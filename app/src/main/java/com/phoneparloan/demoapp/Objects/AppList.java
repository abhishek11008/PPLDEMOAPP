package com.phoneparloan.demoapp.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AppList implements Serializable {

    @SerializedName("success")
    @Expose
    private Boolean success;

    @SerializedName("data")
    @Expose
    private List<AppData> data = null;


    public List<AppData> getData() {
        return data;
    }

    public Boolean getSuccess() {
        return success;
    }


}
