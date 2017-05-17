package net.mindwalkers.control;


public class RestServer {
    private String name;
    private String address;
    private String hash;

    RestServer(String address, String name){
        this.address = address;
        this.name = name;
        this.hash = "";
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setPassw (String pass) {
        // get hash from pass
    }

    public String getHash () {
        return hash;
    }
}
