package com.example.beansdeseasesdetector.Model;

public class Users {
    private String Jina, Email, Password,Token, userUid;

    public Users() {
    }

    public Users(String jina, String email, String password, String phoneNo, String token, String userUid, String userProfile, String userLocation) {
        Jina = jina;
        Email = email;
        Password = password;
        Token = token;
        this.userUid = userUid;
    }

    public String getJina() {
        return Jina;
    }

    public void setJina(String jina) {
        Jina = jina;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }


    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

}
