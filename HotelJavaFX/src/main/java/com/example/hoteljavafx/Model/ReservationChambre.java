package com.example.hoteljavafx.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationChambre {
    int idReservationChambre;
    int idChambre;
    int idReservation;

}
