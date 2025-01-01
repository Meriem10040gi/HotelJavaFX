package com.example.hoteljavafx.DAO;

import com.example.hoteljavafx.Model.Hotel;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface HotelDAOI {

    public Hotel getHotel(int idHotel) throws IOException, SQLException;
    public List<Hotel> getAllHotels() throws IOException, SQLException;
    public void addNewHotel(String nom, String address, String Description, String photo) throws IOException, SQLException;
    public void UpdateHotel(int idHotel,String nom,String address, String Description,String photo) throws IOException, SQLException;
    public void DeleteHotel(int idHotel) throws IOException, SQLException;

    List<Hotel> getHotelsByPage(int limit, int offset) throws SQLException, ClassNotFoundException;

    int getNbrHotels() throws SQLException, ClassNotFoundException;

    ResultSet RSallHotels(List<VBox> vboxes) throws SQLException, ClassNotFoundException;

    List<Integer> idHotelinCity(String city) throws SQLException;

    int idHotel(String hotelName) throws IOException, SQLException;

    int getNumberRooms(int id) throws SQLException, ClassNotFoundException;
}
