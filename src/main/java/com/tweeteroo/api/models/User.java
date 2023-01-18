package com.tweeteroo.api.models;

public class User {
    private String username;
    private String avatar;

    public User(String username, String avatar) {
        this.username = username;
        this.avatar = avatar;
    }

    public String getUserName() {
        return this.username;
    }

    public String getAvatar() {
        return this.avatar;
    }

}
