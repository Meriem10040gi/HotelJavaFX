package com.example.hoteljavafx.Utils;

import com.example.hoteljavafx.DAO.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class searchMethod {
    HotelDAOI hdi = new HotelDAOImpl();
    public Map<Integer, List<Integer>> searchMethodFunction(LocalDate dateDebut, LocalDate dateFin, String city) throws SQLException, IOException, ClassNotFoundException {
        List<Integer> idsHotelsInCitySelected = hdi.idHotelinCity(city);
        ReservationDAOI reservationDao=new ReservationDAOImpl();
        ChambreReservationDAOI reservations = new ChambreReservationDAOImpl();
        List<Integer> idValidRoom=new ArrayList<>();
        Map<Integer, List<Integer>> mapHotelRoDisp = new HashMap<>();
        RoomDAOI roomDao=new RoomDAOImpl();
        for(Integer idhotel:idsHotelsInCitySelected){
            List <Integer> roomsInHotel=roomDao.getRoomsInHotel(idhotel);
            for(Integer idRoom:roomsInHotel){
                List <Integer> reservationIds=reservations.getIdReservationsOfaRoom(idRoom);
                boolean roomIsValid=true;
                for(Integer reservation : reservationIds){
                    DateRange dr=reservationDao.getDateOfaReservation(reservation);
                    boolean isNotChevauchee=isINotntertwined(dr,dateDebut,dateFin);
                    if(!isNotChevauchee){
                        roomIsValid=false;
                        break;
                    }
                }
                if(roomIsValid){
                    idValidRoom.add(idRoom);
                }
            }
            if(!idValidRoom.isEmpty()){
                mapHotelRoDisp.put(idhotel,idValidRoom);
                idValidRoom=null;
            }
        }
        return mapHotelRoDisp;
    }
    public Boolean VerifyChamberDate(LocalDate dateDebut, LocalDate dateFin, int idC) throws SQLException, IOException, ClassNotFoundException {
        ChambreReservationDAOI reservationDao = new ChambreReservationDAOImpl();
        ReservationDAOI reservationDAOI = new ReservationDAOImpl();
        List<Integer> reservationIds = reservationDao.getIdReservationsOfaRoom(idC);
        boolean roomIsValid = true;
        for (Integer reservation : reservationIds) {
            DateRange dr = reservationDAOI.getDateOfaReservation(reservation);
            boolean isNotChevauchee = isINotntertwined(dr, dateDebut, dateFin);
            if (!isNotChevauchee) {
                roomIsValid = false;
                break;
            }
        }
        return roomIsValid;
    }


    public boolean isINotntertwined(DateRange dr, LocalDate checkIn, LocalDate checkOut){
        LocalDate dateDebut = dr.getDateDebut().toLocalDate();
        LocalDate dateFin = dr.getDateFin().toLocalDate();
        return (dateFin.isBefore(checkIn) || dateDebut.isAfter(checkOut));
    }
}
