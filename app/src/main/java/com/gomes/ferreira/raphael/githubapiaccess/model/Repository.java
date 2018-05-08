package com.gomes.ferreira.raphael.githubapiaccess.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Repository implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("full_name")
    private String fullName;

    @SerializedName("description")
    private String description;

    @SerializedName("owner")
    private Author author;

    @SerializedName("stargazers_count")
    private int numberOfStarts;

    @SerializedName("forks")
    private int numberOfForks;

    public Repository() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public int getNumberOfStarts() {
        return numberOfStarts;
    }

    public void setNumberOfStarts(int numberOfStarts) {
        this.numberOfStarts = numberOfStarts;
    }

    public int getNumberOfForks() {
        return numberOfForks;
    }

    public void setNumberOfForks(int numberOfForks) {
        this.numberOfForks = numberOfForks;
    }
}
