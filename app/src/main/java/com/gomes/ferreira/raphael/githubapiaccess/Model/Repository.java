package com.gomes.ferreira.raphael.githubapiaccess.Model;

import com.google.gson.annotations.SerializedName;

public class Repository {

    private String name;
    private String description;
    @SerializedName("owner")
    private Author author;
    private int stargazers_count;
    private int forks_count;

    public Repository(){
        this.setAuthor(new Author());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public int getStargazers_count() {
        return stargazers_count;
    }

    public void setStargazers_count(int stargazers_count) {
        this.stargazers_count = stargazers_count;
    }

    public int getForks_count() {
        return forks_count;
    }

    public void setForks_count(int forks_count) {
        this.forks_count = forks_count;
    }
}
