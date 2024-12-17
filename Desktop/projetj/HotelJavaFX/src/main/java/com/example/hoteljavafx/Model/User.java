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

}
