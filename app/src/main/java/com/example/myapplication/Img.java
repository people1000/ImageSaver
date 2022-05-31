package com.example.myapplication;

public class Img {
    private long id;
    private String url;

    public Img(long id, String url){
        this.id = id;
        this.url = url;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
