package net.mindwalkers.control.keyboard_control;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KeyboardWrite {

    @SerializedName("text")
    @Expose
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}