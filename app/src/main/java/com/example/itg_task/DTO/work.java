package com.example.itg_task.DTO;

public class work {
    String image ;
    String name ;
    String resourceURI ;

    public String getResourceURI() {
        return resourceURI;
    }



    public work(String name, String resourceURI) {
        this.name = name;
        this.resourceURI = resourceURI;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
