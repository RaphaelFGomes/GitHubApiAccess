package com.gomes.ferreira.raphael.githubapiaccess.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Repositories {

    @SerializedName("items")
    private List<Repository> repositoryList = new ArrayList<>();

    public Repositories() {
    }

    public List<Repository> getRepositoryList() {
        return repositoryList;
    }

    public void setRepositoryList(List<Repository> repositoryList) {
        this.repositoryList = repositoryList;
    }
}