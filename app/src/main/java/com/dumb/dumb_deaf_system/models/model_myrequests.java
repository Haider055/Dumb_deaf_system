package com.dumb.dumb_deaf_system.models;

public class model_myrequests {
    String id,viewer,title,description,request_status,date;

    public model_myrequests(String id, String viewer, String title, String description, String request_status, String date) {
        this.id = id;
        this.viewer = viewer;
        this.title = title;
        this.description = description;
        this.request_status = request_status;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getViewer() {
        return viewer;
    }

    public void setViewer(String viewer) {
        this.viewer = viewer;
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

    public String getRequest_status() {
        return request_status;
    }

    public void setRequest_status(String request_status) {
        this.request_status = request_status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
