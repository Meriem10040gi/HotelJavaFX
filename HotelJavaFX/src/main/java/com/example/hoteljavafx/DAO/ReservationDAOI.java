package com.example.hoteljavafx.DAO;

import com.example.hoteljavafx.Model.Reservation;
import com.example.hoteljavafx.Utils.DateRange;
import com.example.hoteljavafx.Utils.ListReservation;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface ReservationDAOI {

    Reservation getReservation(int idReservation) throws SQLException, ClassNotFoundException;
    List<Reservation> getAllReservation() throws SQLException, ClassNotFoundException;

    int getNbrReservations() throws SQLException, ClassNotFoundException;

    List<Reservation> getAllReservationsRoom(int idRoom) throws SQLException, ClassNotFoundException;

    List<Reservation> getAllReservationsUser(int idUser) throws SQLException, ClassNotFoundException;

    void DeleteReservation(int idReservation) throws SQLException, ClassNotFoundException;

    List<Integer> getIdRoom(int idReservation) throws SQLException;

    void createReservation(Reservation reservation);

    int getLastReservationId(int idUser) throws SQLException;

    DateRange getDateOfaReservation(int idReser) throws SQLException, ClassNotFoundException;

    ObservableList<ListReservation> getListReservations(int id) throws SQLException, ClassNotFoundException;
}
