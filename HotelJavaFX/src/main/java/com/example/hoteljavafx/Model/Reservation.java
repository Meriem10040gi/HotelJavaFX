package com.example.hoteljavafx.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    int idReservation;
    int idUser;
    Date dateDebut;
    Date dateFin;
    int numberGuests;
    int numberAdults;
    Date dateAjout;
    Date dateUpdate;
}
