package com.example.myproject.model;

import com.google.firebase.Timestamp;

public class Journal {
    private String title;
    private String description;
    private String imageUrl;
    private String user_id;
    private Timestamp timeAdded;
    private String userName;

    public Journal() { }

    public Journal(String title, String description, String imageUrl, String user_id, Timestamp timeAdded, String userName) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.user_id = user_id;
        this.timeAdded = timeAdded;
        this.userName = userName;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String user_id) {
        this.user_id = user_id;
    }

    public Timestamp getTimeAdded() {
        return timeAdded;
    }

    public void setTimeAdded(Timestamp timeAdded) {
        this.timeAdded = timeAdded;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
