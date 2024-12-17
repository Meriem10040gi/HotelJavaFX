package com.example.hoteljavafx.Service;

import com.example.hoteljavafx.DAO.ChambreReservationDAOI;
import com.example.hoteljavafx.DAO.ChambreReservationDAOImpl;
import com.example.hoteljavafx.DAO.ReservationDAOI;
import com.example.hoteljavafx.DAO.ReservationDAOImpl;
import com.example.hoteljavafx.Model.Reservation;
import com.example.hoteljavafx.Model.ReservationChambre;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservationChambreService {

    static ChambreReservationDAOI CReservationS = new ChambreReservationDAOImpl();
    static ReservationDAOI reservation = new ReservationDAOImpl();
    public List<Reservation> getReservations(int id) throws SQLException, ClassNotFoundException {
          List<ReservationChambre> chambrereservations = CReservationS.getReservations(id);
          List<Reservation> reservations = new ArrayList<>();
          for (ReservationChambre r : chambrereservations){
              Reservation res = reservation.getReservation(r.getIdReservation());
              reservations.add(res);
          }
          return reservations;
    }
}
