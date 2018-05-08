package com.gomes.ferreira.raphael.githubapiaccess;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gomes.ferreira.raphael.githubapiaccess.model.Repositories;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testRetrofit();
    }

    private void testRetrofit() {
        String API_BASE_URL = "https://api.github.com/";

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl(API_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();

        GitHubRepository repositories =  retrofit.create(GitHubRepository.class);

        // Fetch a list of the Github repositories.
        Call<Repositories> call = repositories.getRepositories();

// Execute the call asynchronously. Get a positive or negative callback.
        call.enqueue(new Callback<Repositories>() {
            @Override
            public void onResponse(Call<Repositories> call, Response<Repositories> response) {
                // The network call was a success and we got a response
                // TODO: use the repository list and display it
                Repositories repo =  response.body();
                String result = "";
            }

            @Override
            public void onFailure(Call<Repositories> call, Throwable t) {
                // the network call was a failure
                // TODO: handle error
            }
        });
    }
}
