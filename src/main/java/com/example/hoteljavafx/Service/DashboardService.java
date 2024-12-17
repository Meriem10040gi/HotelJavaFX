package com.example.hoteljavafx.Service;

import com.example.hoteljavafx.DAO.*;
import com.example.hoteljavafx.Model.Hotel;
import com.example.hoteljavafx.Model.Reservation;
import com.example.hoteljavafx.Model.Room;
import com.example.hoteljavafx.Utils.TypeRoom;
import lombok.Data;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
@Data

public class DashboardService {

    private final ReservationDAOI reservationDAO = new ReservationDAOImpl();
    private final HotelDAOI hotelDAO = new HotelDAOImpl();
    private final RoomDAOI roomDAO = new RoomDAOImpl();
    private final UserDAOI userDAO = new UserDAOImpl();
    int nbrHotels;
    int nbrReservations;
    int nbrRooms;
    int nbrUsers;
    int nbrFamille;
    int nbrDouble;
    int nbrSingle;
    int nbrLuxe;

    public void getAll() throws SQLException, ClassNotFoundException {
        nbrHotels=hotelDAO.getNbrHotels();
        nbrRooms=roomDAO.getNbrRooms();
        nbrReservations=reservationDAO.getNbrReservations();
        nbrUsers=userDAO.getNbrUsers();
        nbrFamille=0;
        nbrDouble=0;
        nbrSingle=0;
        nbrLuxe=0;
        List<Room> rooms = roomDAO.getAllRooms();
        for(Room r : rooms){
            if(r.getTypeRoom()== TypeRoom.DE_LUXE) nbrLuxe++;
            if(r.getTypeRoom()== TypeRoom.DOUBLE) nbrDouble++;
            if(r.getTypeRoom()== TypeRoom.SINGLE) nbrSingle++;
            if(r.getTypeRoom()== TypeRoom.FAMILLE) nbrFamille++;
        }
    }

    public Map<Integer, Long> getReservationsForCurrentYear() throws SQLException, ClassNotFoundException {
        List<Reservation> allReservations = reservationDAO.getAllReservation();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        List<Reservation> currentYearReservations = allReservations.stream()
                .filter(reservation -> {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(reservation.getDateDebut());
                    return cal.get(Calendar.YEAR) == currentYear;
                })
                .collect(Collectors.toList());
        Map<Integer, Long> reservationsPerMonth = currentYearReservations.stream()
                .collect(Collectors.groupingBy(reservation -> {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(reservation.getDateDebut());
                    return cal.get(Calendar.MONTH) + 1;
                }, Collectors.counting()));
        Map<Integer, Long> result = new HashMap<>();
        for (int i = 1; i <= 12; i++) {
            result.put(i, reservationsPerMonth.getOrDefault(i, 0L));
        }

        return result;
    }

    public double[] calculateRatingPercentages() throws SQLException, IOException {
        List<Hotel> hotels = hotelDAO.getAllHotels();
        int totalHotels = hotels.size();
        int count5to4 = 0;
        int count4to3 = 0;
        int count3to2 = 0;
        int count2to1 = 0;
        int count1to0 = 0;
        for (Hotel hotel : hotels) {
            double rating = hotel.getNoteRating();
            if (rating >= 4 && rating <= 5) {
                count5to4++;
            } else if (rating >= 3 && rating < 4) {
                count4to3++;
            } else if (rating >= 2 && rating < 3) {
                count3to2++;
            } else if (rating >= 1 && rating < 2) {
                count2to1++;
            } else if (rating >= 0 && rating < 1) {
                count1to0++;
            }
        }
        double[] percentages = new double[5];
        if (totalHotels > 0) {
            percentages[0] = (double) count5to4 / totalHotels * 100;
            percentages[1] = (double) count4to3 / totalHotels * 100;
            percentages[2] = (double) count3to2 / totalHotels * 100;
            percentages[3] = (double) count2to1 / totalHotels * 100;
            percentages[4] = (double) count1to0 / totalHotels * 100;
        }

        return percentages;
    }
}
