package net.mindwalkers.control;


public class RestServer {
    public String name;
    public String address;
    private String hash;

    RestServer(String address, String name){
        this.address = address;
        this.name = name;
    }
    public void SetPassw (String password) {
        // get hash from password
    }
    public String GetHash () {
        return hash;
    }
}
