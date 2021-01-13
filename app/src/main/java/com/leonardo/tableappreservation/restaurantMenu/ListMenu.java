package com.leonardo.tableappreservation.restaurantMenu;

public class ListMenu {
    private String title;
    private String description;
    private String image;

    public ListMenu() {
        // Empty constructor
    }

    public ListMenu(String title, String description, String image){
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
