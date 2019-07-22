package com.phoneparloan.demoapp.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AppData implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("app_name")
    @Expose
    private String app_name;

    @SerializedName("package_id")
    @Expose
    private String package_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getPackage_id() {
        return package_id;
    }

    public void setPackage_id(String package_id) {
        this.package_id = package_id;
    }
}
