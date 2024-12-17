package com.example.hoteljavafx.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Hotel {
    private int idHotel;
    private String nom;
    private String address;
    private String description;
    private double noteRating;
    private String image;
    private Date dateAjout;
    private Date dateUpdate;

    public Hotel(String nom, String address, String description, String image) {
        this.nom = nom;
        this.address = address;
        this.description = description;
        this.noteRating = 3.5;
        this.image = image;
        this.dateAjout = new Date();
        this.dateUpdate = new Date();
    }
}
