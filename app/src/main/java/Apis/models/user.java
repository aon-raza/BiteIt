package Apis.models;

public class user {
    private String username;
    private String password;
    private String firebase_tocken;

    public user(String username, String password, String firebase_tocken) {
        this.username = username;
        this.password = password;
        this.firebase_tocken = firebase_tocken;
    }
}
