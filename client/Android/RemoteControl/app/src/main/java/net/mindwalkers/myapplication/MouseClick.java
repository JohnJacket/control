package net.mindwalkers.myapplication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MouseClick {

    @SerializedName("button")
    @Expose
    private Integer button;
    @SerializedName("repeat")
    @Expose
    private Integer repeat;
    @SerializedName("delay")
    @Expose
    private Integer delay;

    public Integer getButton() {
        return button;
    }

    public void setButton(Integer button) {
        this.button = button;
    }

    public Integer getRepeat() {
        return repeat;
    }

    public void setRepeat(Integer repeat) {
        this.repeat = repeat;
    }

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

}