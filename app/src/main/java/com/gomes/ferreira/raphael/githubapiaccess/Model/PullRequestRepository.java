package com.gomes.ferreira.raphael.githubapiaccess.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class PullRequestRepository {

    private String title;
    @SerializedName("body")
    private String description;
    private String html_url;
    private String state;
    @SerializedName("created_at")
    private Date date;
    @SerializedName("user")
    private Author author;

    public PullRequestRepository(){
        this.setAuthor(new Author());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
