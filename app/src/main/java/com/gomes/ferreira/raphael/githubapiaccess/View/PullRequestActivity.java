package com.gomes.ferreira.raphael.githubapiaccess.View;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ToggleButton;

import com.gomes.ferreira.raphael.githubapiaccess.Model.PullRequestRepository;
import com.gomes.ferreira.raphael.githubapiaccess.R;
import com.gomes.ferreira.raphael.githubapiaccess.Service.GitHubServices;
import com.gomes.ferreira.raphael.githubapiaccess.Network.Internet;
import com.gomes.ferreira.raphael.githubapiaccess.View.Adapter.PullRequestAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PullRequestActivity extends AppCompatActivity {

    private static final String API_BASE_URL_PULL_REQUEST = "https://api.github.com/";
    PullRequestAdapter pullAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_request);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();

        String authorName = null;
        String repositoryName = null;

        if (bundle != null){
            if (bundle.getString("authorName") != null){
                if (bundle.getString("authorName") != ""){
                    authorName = bundle.getString("authorName");
                }
            }
            if (bundle.getString("repositoryName") != null){
                if (bundle.getString("repositoryName") != ""){
                    repositoryName = bundle.getString("repositoryName");
                }
            }
        }

        Internet internet = new Internet();
        if (!internet.isInternetAvailable(getApplicationContext())) {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.linearLayoutMainActivity), "Você não está conectado à internet.", Snackbar.LENGTH_LONG);
            snackbar.show();
        }else{
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL_PULL_REQUEST)
                    .addConverterFactory(GsonConverterFactory.create());

            Retrofit retrofit = builder.client(httpClient.build()).build();
            GitHubServices repositories =  retrofit.create(GitHubServices.class);

            // Fetch a list of the Github repositories.
            Call<JsonArray> call = repositories.getPullRequest(authorName, repositoryName);

            // Execute the call asynchronously. Get a positive or negative callback.
            call.enqueue(new Callback<JsonArray>() {
                @Override
                public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                    JsonArray pullRequests =  response.body();
                    List<PullRequestRepository> pulls = new ArrayList<>();
                    Gson gson = new Gson();
                    if (pullRequests.size() > 0){
                        for (int i =0;i<pullRequests.size();i++){
                            PullRequestRepository pull = gson.fromJson(pullRequests.get(i).getAsJsonObject(),PullRequestRepository.class);
                            pulls.add(pull);
                        }

                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewPulls);
                        recyclerView.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL));

                        pullAdapter = new PullRequestAdapter(pulls, getApplicationContext());
                        recyclerView.setAdapter(pullAdapter);
                    }else{
                        Snackbar snackbar = Snackbar.make(findViewById(R.id.linearLayoutPullActivity), "Pull request not found!", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }

                @Override
                public void onFailure(Call<JsonArray> call, Throwable t) {
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.linearLayoutPullActivity), "Pull request not found!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onToggleClicked(View view) {
        if(((ToggleButton) view).isChecked()) {
            // handle toggle on
            pullAdapter.updateRecyclerData("close");
            pullAdapter.notifyDataSetChanged();
        } else {
            // handle toggle off
            pullAdapter.updateRecyclerData("open");
            pullAdapter.notifyDataSetChanged();
        }
    }

}
