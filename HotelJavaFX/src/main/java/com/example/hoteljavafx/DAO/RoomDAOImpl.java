package com.example.hoteljavafx.DAO;

import com.example.hoteljavafx.Model.Room;
import com.example.hoteljavafx.Utils.GestionDB;
import com.example.hoteljavafx.Utils.TypeRoom;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RoomDAOImpl implements RoomDAOI {

    private GestionDB Pilot = new GestionDB();

    @Override
    public void addNewRoom(int numero, String photo, String roomType, Double roomPrice, String description, int idHotel) throws SQLException {
        String sql = "INSERT INTO Room (idHotel, typeRoom, description, prix, disponibilite, image, dateAjout,numero) VALUES (?, ?, ?, ?, ?, ?, ?,?)";
        try {
            Pilot.connecte("hotelreservation", "root", "");
            PreparedStatement stmt = Pilot.connexion.prepareStatement(sql);
            stmt.setInt(1, idHotel);
            stmt.setInt(8, numero);
            stmt.setString(2, roomType);
            stmt.setString(3, description);
            stmt.setDouble(4, roomPrice);
            stmt.setBoolean(5, true);
            stmt.setString(6, photo);
            stmt.setDate(7, new java.sql.Date(System.currentTimeMillis()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            Pilot.close();
        }
    }

    @Override
    public Room getRoom(int idRoom) throws  SQLException {
        String sql = "SELECT * FROM Room WHERE idRoom = ?";
        Room room = new Room();
        try {
            Pilot.connecte("hotelreservation", "root", "");
            PreparedStatement stmt = Pilot.connexion.prepareStatement(sql);
            stmt.setInt(1, idRoom);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                room = new Room(
                        rs.getInt("idRoom"),
                        rs.getInt("idHotel"),
                        rs.getInt("numero"),
                        TypeRoom.valueOf(rs.getString("typeRoom")),
                        rs.getString("description"),
                        rs.getDouble("prix"),
                        rs.getBoolean("disponibilite"),
                        rs.getString("image"),
                        rs.getDate("dateAjout"),
                        rs.getDate("dateUpdate")
                );
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            Pilot.close();
        }
        return room;
    }

    @Override
    public boolean isDisponible(int idRoom, Date debut, Date fin) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Reservation WHERE idRoom = ? AND ((dateDebut BETWEEN ? AND ? OR dateFin BETWEEN ? AND ?) OR (dateDebut <= ? AND dateFin >= ?))  ";
        boolean r=false;
        try {
            Pilot.connecte("hotelreservation", "root", "");
            PreparedStatement stmt = Pilot.connexion.prepareStatement(sql);
            stmt.setInt(1, idRoom);
            stmt.setDate(2, new java.sql.Date(debut.getTime()));
            stmt.setDate(3, new java.sql.Date(fin.getTime()));
            stmt.setDate(4, new java.sql.Date(debut.getTime()));
            stmt.setDate(5, new java.sql.Date(fin.getTime()));
            stmt.setDate(6, new java.sql.Date(debut.getTime()));
            stmt.setDate(7, new java.sql.Date(fin.getTime()));
            ResultSet rs = stmt.executeQuery();
            r = !rs.next();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            Pilot.close();
        }
        return r;
    }

    @Override
    public List<Room> getAllRooms(int idHotel, Date debut, Date fin) throws SQLException {
        String sql = "SELECT * FROM Room WHERE idHotel = ?";
        List<Room> rooms = new ArrayList<>();

        try {
            Pilot.connecte("hotelreservation", "root", "");
            PreparedStatement stmt = Pilot.connexion.prepareStatement(sql);
            stmt.setInt(1, idHotel);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Room room = new Room(
                        rs.getInt("idRoom"),
                        rs.getInt("idHotel"),
                        rs.getInt("numero"),
                        TypeRoom.valueOf(rs.getString("typeRoom")),
                        rs.getString("description"),
                        rs.getDouble("prix"),
                        rs.getBoolean("disponibilite"),
                        rs.getString("image"),
                        rs.getDate("dateAjout"),
                        rs.getDate("dateUpdate")
                );
                if(this.isDisponible(room.getIdRoom(),debut,fin)){
                    rooms.add(room);}
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            Pilot.close();
        }
        return rooms;
    }
    @Override
    public List<Room> getAllRooms(int idHotel) throws SQLException {
        String sql = "SELECT * FROM Room WHERE idHotel = ?";
        List<Room> rooms = new ArrayList<>();

        try {
            Pilot.connecte("hotelreservation", "root", "");
            PreparedStatement stmt = Pilot.connexion.prepareStatement(sql);
            stmt.setInt(1, idHotel);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Room room = new Room(
                        rs.getInt("idRoom"),
                        rs.getInt("idHotel"),
                        rs.getInt("numero"),
                        TypeRoom.valueOf(rs.getString("typeRoom")),
                        rs.getString("description"),
                        rs.getDouble("prix"),
                        rs.getBoolean("disponibilite"),
                        rs.getString("image"),
                        rs.getDate("dateAjout"),
                        rs.getDate("dateUpdate")
                );
                rooms.add(room);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            Pilot.close();
        }
        return rooms;
    }

    @Override
    public void UpdateRoom(String photo, String roomType, Double roomPrice, String description, int idRoom) throws IOException, SQLException {
        String sql = "UPDATE Room SET typeRoom = ?, description = ?, prix = ?, disponibilite = ?, image = ?, dateUpdate = ? WHERE idRoom = ?";

        try {
            Pilot.connecte("hotelreservation", "root", "");
            PreparedStatement stmt = Pilot.connexion.prepareStatement(sql);
            stmt.setString(1, roomType);
            stmt.setString(2, description);
            stmt.setDouble(3, roomPrice);
            stmt.setBoolean(4, true);
            stmt.setString(6, photo);
            stmt.setDate(6, new java.sql.Date(System.currentTimeMillis()));
            stmt.setInt(7, idRoom);
            stmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            Pilot.close();
        }
    }

    @Override
    public void DeleteRoom(int idRoom) throws SQLException {
        String sql = "DELETE FROM Room WHERE idRoom = ?";

        try {
            Pilot.connecte("hotelreservation", "root", "");
            PreparedStatement stmt = Pilot.connexion.prepareStatement(sql);
            stmt.setInt(1, idRoom);
            stmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            Pilot.close();
        }
    }
}
