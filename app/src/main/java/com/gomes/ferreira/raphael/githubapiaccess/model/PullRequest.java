package com.gomes.ferreira.raphael.githubapiaccess.model;

public class PullRequest {

    private String name;
    private String title;
    private long date;
    private String body;
    private Author Author;
    private String htmlUrl;

    public PullRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public com.gomes.ferreira.raphael.githubapiaccess.model.Author getAuthor() {
        return Author;
    }

    public void setAuthor(com.gomes.ferreira.raphael.githubapiaccess.model.Author author) {
        Author = author;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }
}
