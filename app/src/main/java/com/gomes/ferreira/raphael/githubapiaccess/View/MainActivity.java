package com.gomes.ferreira.raphael.githubapiaccess.View;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.gomes.ferreira.raphael.githubapiaccess.Database.DBAdapter;
import com.gomes.ferreira.raphael.githubapiaccess.Model.Repositories;
import com.gomes.ferreira.raphael.githubapiaccess.Model.Repository;
import com.gomes.ferreira.raphael.githubapiaccess.R;
import com.gomes.ferreira.raphael.githubapiaccess.Service.GitHubServices;
import com.gomes.ferreira.raphael.githubapiaccess.Network.Internet;
import com.gomes.ferreira.raphael.githubapiaccess.View.Adapter.RepositoryAdapter;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private int page = 1;
    private RepositoryAdapter repositoryAdapter;
    private boolean isLoading = false;
    private int totalItems = 0;
    private DBAdapter bd = null;
    private Internet internet = null;
    private static final String API_BASE_URL = "https://api.github.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bd = new DBAdapter(this);
        bd.open();
        internet = new Internet();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewRepositories);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = ((LinearLayoutManager) recyclerView.getLayoutManager());
                int pos = layoutManager.findLastCompletelyVisibleItemPosition();
                if ((pos + 1) == totalItems && !isLoading) {
                    isLoading = true;
                    loadData();
                }
            }
        });
        loadData();
    }

    public void loadData() {
        List<Repository> repositoryList = null;
            if (!internet.isInternetAvailable(getApplicationContext())) {
                repositoryList = bd.getAllRepositoriesByPage(page);
                if (repositoryList.size() <= 0) {
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.linearLayoutMainActivity), "You need connect to Internet to download all repositories!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                else {
                    if (page == 1) {
                        repositoryAdapter = new RepositoryAdapter(repositoryList, getApplicationContext());
                        recyclerView.setAdapter(repositoryAdapter);
                    } else {
                        repositoryAdapter.addListMoreItems(repositoryList);
                    }
                    totalItems += repositoryList.size();
                    page++;
                    isLoading = false;
                }
            } else {
                    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
                    Retrofit.Builder builder = new Retrofit.Builder()
                            .baseUrl(API_BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create());

                    Retrofit retrofit = builder.client(httpClient.build()).build();
                    GitHubServices repositories =  retrofit.create(GitHubServices.class);

                    // Fetch a list of the Github repositories.
                    Call<Repositories> call = repositories.getRepositories(Integer.toString(page));

                    // Execute the call asynchronously. Get a positive or negative callback.
                    call.enqueue(new Callback<Repositories>() {
                        @Override
                        public void onResponse(Call<Repositories> call, Response<Repositories> response) {
                            // The network call was a success and we got a response
                            // TODO: use the repository list and display it
                            Repositories repo =  response.body();
                            if (repo.getRepositoryList().size() > 0) {
                                bd.clearDatabase();
                                bd.insertRepositories(repo.getRepositoryList(), page);

                                if (page == 1) {
                                    repositoryAdapter = new RepositoryAdapter(repo.getRepositoryList(), getApplicationContext());
                                    recyclerView.setAdapter(repositoryAdapter);
                                } else {
                                    repositoryAdapter.addListMoreItems(repo.getRepositoryList());
                                }
                                totalItems += repo.getRepositoryList().size();
                                page++;
                                isLoading = false;
                            } else {
                                Snackbar snackbar = Snackbar.make(findViewById(R.id.linearLayoutMainActivity), "Is not possible to show the repositories!", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Repositories> call, Throwable t) {
                            Snackbar snackbar = Snackbar.make(findViewById(R.id.linearLayoutMainActivity), "Is not possible to show the repositories!", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    });
            }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        return true;
    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (repositoryAdapter != null) repositoryAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }
}
