package com.dumb.dumb_deaf_system.models;

public class model_Talksby_Cat {

    String id,file_type,description,user_id,category,quantity,date,gif,video,audio,urdu_description,title;

    public model_Talksby_Cat(String id, String file_type, String description, String title, String user_id, String category, String quantity, String date, String gif, String video, String audio,String urdu_description) {
        this.id = id;
        this.file_type = file_type;
        this.description = description;
        this.urdu_description = urdu_description;
        this.user_id = user_id;
        this.category = category;
        this.quantity = quantity;
        this.date = date;
        this.gif = gif;
        this.video = video;
        this.title = title;
        this.audio = audio;
    }
    public model_Talksby_Cat() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrdu_description() {
        return urdu_description;
    }

    public void setUrdu_description(String urdu_description) {
        this.urdu_description = urdu_description;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
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
}