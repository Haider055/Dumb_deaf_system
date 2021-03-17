package com.dumb.dumb_deaf_system.models;

public class model_mytalks {

    String id,file_type,description,user_id,category,date,gif,video,audio,urdu_description,title;

    public model_mytalks(String id, String file_type, String description, String user_id, String category, String quantity, String date, String gif, String video, String audio, String urdu_description, String title) {
        this.id = id;
        this.file_type = file_type;
        this.description = description;
        this.user_id = user_id;
        this.category = category;
        this.date = date;
        this.gif = gif;
        this.video = video;
        this.audio = audio;
        this.urdu_description = urdu_description;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGif() {
        return gif;
    }

    public void setGif(String gif) {
        this.gif = gif;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getUrdu_description() {
        return urdu_description;
    }

    public void setUrdu_description(String urdu_description) {
        this.urdu_description = urdu_description;
    }
}
