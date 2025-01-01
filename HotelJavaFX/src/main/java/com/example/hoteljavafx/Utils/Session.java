package com.example.hoteljavafx.Utils;

public class Session {

    private static Session instance;
    private int idUser;
    private String role;
    private boolean isConnected;

    private Session() {
        this.isConnected = false;
    }

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public void startSession(int userId,String role) {
        this.idUser = userId;
        this.role=role;
        this.isConnected = true;
    }

    public void endSession() {
        this.idUser = 0;
        this.role=null;
        this.isConnected = false;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public int getUserId() {
        return idUser;
    }
    public String getRole(){return role;}
}
