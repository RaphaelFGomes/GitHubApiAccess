package com.gomes.ferreira.raphael.githubapiaccess;

import com.gomes.ferreira.raphael.githubapiaccess.model.Repositories;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GitHubRepository {
    @GET("/search/repositories?q=language:Kotlin&sort=stars&page=1")
    Call<Repositories> getRepositories();
}
