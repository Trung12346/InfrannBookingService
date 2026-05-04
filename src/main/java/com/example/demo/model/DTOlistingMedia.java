package com.example.demo.model;

public class DTOlistingMedia {
    private listings listings;
    private media media;

    public listings getListings(){
        return listings;
    }
    public media getMedia(){
        return media;
    }

    public void setListings(listings listings) {
        this.listings = listings;
    }
    public void setMedia(media media){
        this.media=media;
    }


}
