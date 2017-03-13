package net.mindwalkers.myapplication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MouseDown {

    @SerializedName("button")
    @Expose
    private Integer button;

    public Integer getButton() {
        return button;
    }

    public void setButton(Integer button) {
        this.button = button;
    }

}