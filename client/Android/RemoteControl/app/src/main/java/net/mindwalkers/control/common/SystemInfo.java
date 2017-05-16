package net.mindwalkers.control.common;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SystemInfo {

    @SerializedName("Machine")
    @Expose
    private String machine;
    @SerializedName("OS")
    @Expose
    private String oS;
    @SerializedName("Platform")
    @Expose
    private String platform;
    @SerializedName("Release")
    @Expose
    private String release;
    @SerializedName("Uname")
    @Expose
    private List<String> uname = null;

    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public String getOS() {
        return oS;
    }

    public void setOS(String oS) {
        this.oS = oS;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public List<String> getUname() {
        return uname;
    }

    public void setUname(List<String> uname) {
        this.uname = uname;
    }

}