package com.example.itg_task.DTO;

public class Character {
    private String name;
    private String resourceURI;

    public boolean isLoading() {
        return isLoading;
    }

    private boolean isLoading;

    public String getPhoto_img() {
        return photo_img;
    }

    public void setPhoto_img(String photo_img) {
        this.photo_img = photo_img;
    }

    private String photo_img;




    public Character(String name, String resourceURI, String photo_img) {
        this.name = name;
        this.resourceURI = resourceURI;
        this.photo_img = photo_img;
    }

    public Character(String name, String resourceURI, String photo_img, boolean isLoading) {
        this.name = name;
        this.resourceURI = resourceURI;
        this.photo_img = photo_img;
        this.isLoading = isLoading;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResourceURI() {
        return resourceURI;
    }

}
