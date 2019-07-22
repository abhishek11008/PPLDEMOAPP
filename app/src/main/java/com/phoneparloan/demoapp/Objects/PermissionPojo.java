package com.phoneparloan.demoapp.Objects;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class PermissionPojo implements Serializable {

    Drawable id;
    String title,description;


    public PermissionPojo(Drawable id, String title, String description) {
        this.id = id;
        this.title = String.valueOf(title);
        this.description = String.valueOf(description);
    }


    public Drawable getId() {
        return id;
    }

    public void setId(Drawable id) {
        this.id = id;
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
}
