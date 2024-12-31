//cette classe est pour inserer les donees dans le tableau dans bookings
package com.example.hoteljavafx.Utils;

public class ListReservation {
    private int id;
    private String hotelName;
    private String idRooms;
    private String checkInDate;
    private String checkOutDate;
    private int numberAdults;
    private int numberGuests;

    public ListReservation(String checkOutDate, String checkInDate, String idRooms, String hotelName, int id) {
        this.checkOutDate = checkOutDate;
        this.checkInDate = checkInDate;
        this.idRooms = idRooms;
        this.hotelName = hotelName;
        this.id = id;
    }

    public ListReservation() {
    }


    public int getNumberAdults() {
        return numberAdults;
    }

    public void setNumberAdults(int numberAdults) {
        this.numberAdults = numberAdults;
    }

    public int getNumberGuests() {
        return numberGuests;
    }

    public void setNumberGuests(int numberGuests) {
        this.numberGuests = numberGuests;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getIdRooms() {
        return idRooms;
    }

    public void setIdRooms(String idRoom) {
        this.idRooms = idRoom;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
}
