package com.dumb.dumb_deaf_system.models;

public class modelcategories {

    String category,image,id;

    public modelcategories(String category, String image, String id) {
        this.category = category;
        this.image = image;
        this.id = id;
    }
    public modelcategories() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
