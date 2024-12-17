package com.example.hoteljavafx.DAO;

import com.example.hoteljavafx.Utils.GestionDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChambreReservationDAOImpl implements ChambreReservationDAOI{
    GestionDB Pilot = new GestionDB();
    @Override
    public List<ReservationChambre> getReservations(int id) throws SQLException, ClassNotFoundException {
        List<ReservationChambre> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservationchambre WHERE idChambre = ?";
        try {
            Pilot.connecte("hotelreservation", "root", "");
            PreparedStatement stmt = Pilot.connexion.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ReservationChambre res = new ReservationChambre(
                        rs.getInt("idReservationChambre"),
                        rs.getInt("idChambre"),
                        rs.getInt("idReservation")
                );
                reservations.add(res);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return reservations;
    }
}