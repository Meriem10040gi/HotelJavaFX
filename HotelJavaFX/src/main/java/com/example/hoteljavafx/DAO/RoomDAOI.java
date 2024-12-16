package com.example.hoteljavafx.DAO;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import com.example.hoteljavafx.Model.Room;

public interface RoomDAOI {

    public void addNewRoom(int Numero, String photo, String roomType, Double roomPrice, String Description, int idHotel) throws  SQLException;
    public Room getRoom(int idRoom) throws SQLException;
    public boolean isDisponible(int id, Date debut, Date fin) throws SQLException;
    public List<Room> getAllRooms(int idHotel, Date debut, Date fin) throws SQLException;
    public List<Room> getAllRooms(int idHotel) throws SQLException;
    public void UpdateRoom(String photo, String roomType, Double roomPrice, String Description, int idHotel) throws IOException, SQLException;
    public void DeleteRoom(int idRoom) throws  SQLException;
}
