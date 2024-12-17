package com.example.hoteljavafx.DAO;

import com.example.hoteljavafx.Model.Reservation;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface ReservationDAOI {
    void addNewReservation(int idRoom, int idUser, int nbrGuests, int nbrAdults, Date debut, Date fin) throws SQLException, ClassNotFoundException;


    Reservation getReservation(int idReservation) throws SQLException, ClassNotFoundException;
    List<Reservation> getAllReservation() throws SQLException, ClassNotFoundException;

    int getNbrReservations() throws SQLException, ClassNotFoundException;

    List<Reservation> getAllReservationsRoom(int idRoom) throws SQLException, ClassNotFoundException;

    List<Reservation> getAllReservationsUser(int idUser) throws SQLException, ClassNotFoundException;

    void UpdateReservation(int idReservation, int nbrGuests, int nbrAdults, Date debut, Date fin) throws SQLException, ClassNotFoundException;

    void DeleteReservation(int idReservation) throws SQLException, ClassNotFoundException;

    List<Integer> getIdRoom(int idReservation) throws SQLException;
}
