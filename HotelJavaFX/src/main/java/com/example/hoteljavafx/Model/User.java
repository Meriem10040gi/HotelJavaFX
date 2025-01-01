package com.example.hoteljavafx.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    int idUser;
    String nom;
    String prenom;
    String address;
    String email;
    String phone;
    String password;
    String role;
    Date dateAjout;
    Date dateUpdate;
    public User(String nom, String prenom, String address, String email, String phone, String password) {
        this.nom = nom;
        this.prenom = prenom;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }
}
