package com.leonardo.tableappreservation.menu;

import android.widget.ImageView;

public class Menu {
    private String title;
    private String description;
    private String image;

    public Menu() {
        // Empty constructor
    }

    public Menu(String title, String description, String image){
        this.title = title;
        this.description = description;
        this.image = image;

    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }
}
