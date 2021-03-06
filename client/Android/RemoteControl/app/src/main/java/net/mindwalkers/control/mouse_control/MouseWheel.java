package net.mindwalkers.control.mouse_control;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MouseWheel {

    @SerializedName("amount")
    @Expose
    private Integer amount;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}