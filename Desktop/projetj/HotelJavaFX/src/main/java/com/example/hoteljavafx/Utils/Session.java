package com.example.hoteljavafx.Utils;

public class Session {

    private static Session instance;
    private int idUser;
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

    public void startSession(int userId) {
        this.idUser = userId;
        this.isConnected = true;
    }

    public void endSession() {
        this.idUser = 0;
        this.isConnected = false;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public int getUserId() {
        return idUser;
    }
}
