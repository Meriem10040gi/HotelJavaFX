package com.example.hoteljavafx.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.hoteljavafx.Utils.TypeRoom;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Room {

    private int idRoom;
    private int idHotel;
    private int numero;
    private TypeRoom typeRoom;
    private String description;
    private double prix;
    private boolean disponibilite = true;
    private String image;
    private Date dateAjout;
    private Date dateUpdate;

}