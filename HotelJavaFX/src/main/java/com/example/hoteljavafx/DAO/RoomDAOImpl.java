package com.example.hoteljavafx.DAO;

import com.example.hoteljavafx.Model.Room;
import com.example.hoteljavafx.Utils.GestionDB;
import com.example.hoteljavafx.Utils.TypeRoom;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public List<Room> getListRoom(List<Integer> idRooms) throws SQLException {
        List<Room> rooms = new ArrayList<>();
        for(Integer idRoom : idRooms){
            rooms.add(getRoom(idRoom));
        }
        return rooms;
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

    @Override
    public int getNbrRooms() throws SQLException, ClassNotFoundException {
        String sql = "SELECT count(*) FROM Room";
        try {
            Pilot.connecte("hotelreservation", "root", "");
            PreparedStatement stmt = Pilot.connexion.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return 0;

    }
    @Override
    public List<Room> getAllRooms() throws SQLException {
        String sql = "SELECT * FROM Room ";
        List<Room> rooms = new ArrayList<>();

        try {
            Pilot.connecte("hotelreservation", "root", "");
            PreparedStatement stmt = Pilot.connexion.prepareStatement(sql);
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
    public int getIdHotel(int idRoom){
        String req = "SELECT idHotel FROM room WHERE idRoom=?";
        try{
            Pilot.connecte("hotelreservation","root", "");
            PreparedStatement ps = Pilot.connexion.prepareStatement(req);
            ps.setInt(1,idRoom);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public List<Integer> getRoomsInHotel(int idhotel)throws IOException, SQLException{
        List<Integer> idRoomsInHotel = new ArrayList<>();
        try {
            Pilot.connecte("hotelreservation", "root", "");
            String req = "SELECT idRoom FROM room WHERE idHotel=?";
            PreparedStatement ps = Pilot.connexion.prepareStatement(req);
            ps.setInt(1, idhotel);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                idRoomsInHotel.add(rs.getInt("idRoom"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return idRoomsInHotel;
    }
    @Override
    public int nbrRooms(int idHotel) throws IOException, SQLException{
        String req = "SELECT COUNT(*) FROM room where idHotel=?";
        try{
            Pilot.connecte("hotelreservation","root", "");
            PreparedStatement ps = Pilot.connexion.prepareStatement(req);
            ps.setInt(1,idHotel);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) { // Lire le premier enregistrement
                return rs.getInt(1); // Obtenir la valeur de la première colonne
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

}
