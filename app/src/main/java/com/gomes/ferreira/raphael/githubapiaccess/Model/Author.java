package com.gomes.ferreira.raphael.githubapiaccess.Model;

import com.google.gson.annotations.SerializedName;

public class Author {

    private String login;
    private String avatar_url;

    public Author(){}

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }
}
