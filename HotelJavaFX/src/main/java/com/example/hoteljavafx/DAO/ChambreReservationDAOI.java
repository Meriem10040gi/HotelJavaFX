package com.example.hoteljavafx.DAO;

import java.sql.SQLException;
import java.util.List;

public interface ChambreReservationDAOI {
    List<ReservationChambre> getReservations(int id) throws SQLException, ClassNotFoundException;

}
