package com.example.hoteljavafx.DAO;

import com.example.hoteljavafx.Model.Hotel;
import com.example.hoteljavafx.Utils.GestionDB;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HotelDAOImpl implements HotelDAOI{

    GestionDB Pilot = new GestionDB();

    @Override
    public Hotel getHotel(int idHotel) throws IOException, SQLException {
        Hotel hotel = new Hotel();
        String sql = "SELECT * FROM Hotel WHERE idHotel = ?";

        try {
            Pilot.connecte("hotelreservation", "root", "");
            PreparedStatement stmt = Pilot.connexion.prepareStatement(sql);
            stmt.setInt(1, idHotel);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    hotel = new Hotel(
                            rs.getInt("idHotel"),
                            rs.getString("name"),
                            rs.getString("address"),
                            rs.getString("description"),
                            rs.getDouble("rating"),
                            rs.getString("image"),
                            rs.getDate("dateAjout"),
                            rs.getDate("dateUpdate")
                    );
                }
            }
            Pilot.close();
        } catch (SQLException | ClassNotFoundException e ) {
            System.err.println("Erreur en accédant aux données : " + e.getMessage());
            e.printStackTrace();
        }
        return hotel;
    }

    @Override
    public List<Hotel> getAllHotels() throws IOException, SQLException {
        String sql = "SELECT * FROM Hotel";
        List<Hotel> hotels = new ArrayList<>();
        try {
            Pilot.connecte("hotelreservation", "root", "");
            PreparedStatement stmt = Pilot.connexion.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Hotel hotel = new Hotel(
                        rs.getInt("idHotel"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("description"),
                        rs.getDouble("rating"),
                        rs.getString("image"),
                        rs.getDate("dateAjout"),
                        rs.getDate("dateUpdate")
                );
                hotels.add(hotel);
            }
            Pilot.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Erreur en accédant aux données : " + e.getMessage());
            e.printStackTrace();
        }
        return hotels;
    }

    @Override
    public void addNewHotel(String nom, String address, String description, String photo) throws IOException, SQLException {
        String sql = "INSERT INTO Hotel (name, address, description, image, dateAjout, dateUpdate, rating) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try  {
            Pilot.connecte("hotelreservation", "root", "");
            PreparedStatement stmt = Pilot.connexion.prepareStatement(sql);
            stmt.setString(1, nom);
            stmt.setString(2, address);
            stmt.setString(3, description);
            stmt.setString(4, photo);
            stmt.setDate(5, new java.sql.Date(System.currentTimeMillis()));
            stmt.setDate(6, new java.sql.Date(System.currentTimeMillis()));
            stmt.setDouble(7, 3.5);
            stmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Erreur lors de l'ajout de l'hôtel : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void UpdateHotel(int idHotel, String nom, String address, String description, String photo) throws IOException, SQLException {
        String sql = "UPDATE Hotel SET name = ?, address = ?, description = ?, image = ?, dateUpdate = ? WHERE idHotel = ?";

        try  {
            Pilot.connecte("hotelreservation", "root", "");
            PreparedStatement stmt = Pilot.connexion.prepareStatement(sql);
            stmt.setString(1, nom);
            stmt.setString(2, address);
            stmt.setString(3, description);
            stmt.setString(4, photo);
            stmt.setDate(5, new java.sql.Date(System.currentTimeMillis()));
            stmt.setInt(6, idHotel);
            stmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Erreur lors de la mise à jour de l'hôtel : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void DeleteHotel(int idHotel) throws IOException, SQLException {
        String sql = "DELETE FROM Hotel WHERE idHotel = ?";

        try  {
            Pilot.connecte("hotelreservation", "root", "");
            PreparedStatement stmt = Pilot.connexion.prepareStatement(sql);
            stmt.setInt(1, idHotel);
            stmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Erreur lors de la suppression de l'hôtel : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<Hotel> getHotelsByPage(int limit, int offset) throws SQLException, ClassNotFoundException {
        Pilot.connecte("hotelreservation", "root", "");
        List<Hotel> hotels = new ArrayList<>();
        String query = "SELECT * FROM hotel LIMIT ? OFFSET ?";
        try (PreparedStatement statement = Pilot.connexion.prepareStatement(query)) {
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Hotel hotel = new Hotel(
                        rs.getInt("idHotel"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("description"),
                        rs.getDouble("rating"),
                        rs.getString("image"),
                        rs.getDate("dateAjout"),
                        rs.getDate("dateUpdate")
                );
                hotels.add(hotel);
            }
        }
        return hotels;
    }

    @Override
    public int getNbrHotels() throws SQLException, ClassNotFoundException {
        String sql = "SELECT count(*) FROM Hotel";
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

}
