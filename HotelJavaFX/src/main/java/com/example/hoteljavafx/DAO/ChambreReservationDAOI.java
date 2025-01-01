package com.example.hoteljavafx.DAO;

import com.example.hoteljavafx.Model.ReservationChambre;

import java.sql.SQLException;
import java.util.List;

public interface ChambreReservationDAOI {
    List<ReservationChambre> getReservations(int id) throws SQLException, ClassNotFoundException;

    List<Integer> getIdReservationsOfaRoom(int idR) throws SQLException, ClassNotFoundException;
}
