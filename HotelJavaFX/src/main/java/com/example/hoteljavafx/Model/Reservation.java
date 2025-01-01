package com.example.hoteljavafx.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    int idReservation;
    int idUser;
    Date dateDebut;
    Date dateFin;
    Date dateAjout;
    Date dateUpdate;
    List<Room> rooms;
}
