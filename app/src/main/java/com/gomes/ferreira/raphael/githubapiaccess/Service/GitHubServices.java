package com.gomes.ferreira.raphael.githubapiaccess.Service;

import com.gomes.ferreira.raphael.githubapiaccess.Model.Repositories;
import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitHubServices {
    @GET("/search/repositories?q=language:Kotlin&sort=stars")
    Call<Repositories> getRepositories(@Query("page") String page);

    @GET("/repos/{authorName}/{repositoryName}/pulls")
    Call<JsonArray> getPullRequest(@Path("authorName") String authorName, @Path("repositoryName") String repositoryName);
}
