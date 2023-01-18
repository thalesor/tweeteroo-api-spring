package com.tweeteroo.api.models;

public class Tweet {
    private String username;
    private String avatar;
    private String tweet;

    public Tweet(String username, String avatar, String tweet) {
        this.username = username;
        this.avatar = avatar;
        this.tweet = tweet;
    }

    public String getUserName() {
        return this.username;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public String getTweet() {
        return this.tweet;
    }

}
