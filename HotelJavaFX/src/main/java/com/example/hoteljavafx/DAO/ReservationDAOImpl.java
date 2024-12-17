package com.example.hoteljavafx.DAO;

import com.example.hoteljavafx.Model.Reservation;
import com.example.hoteljavafx.Utils.GestionDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReservationDAOImpl implements ReservationDAOI{
    GestionDB Pilot = new GestionDB();
    @Override
    public Reservation getReservation(int idReservation) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Reservation WHERE idReservation = ?";
        Reservation reservation = null;
        try {
            Pilot.connecte("hotelreservation", "root", "");
            PreparedStatement stmt = Pilot.connexion.prepareStatement(sql);
            stmt.setInt(1, idReservation);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                reservation = new Reservation(
                        rs.getInt("idReservation"),
                        rs.getInt("idUser"),
                        rs.getDate("dateDebut"),
                        rs.getDate("dateFin"),
                        rs.getInt("numberGuests"),
                        rs.getInt("numberAdults"),
                        rs.getDate("dateAjout"),
                        rs.getDate("dateUpdate")
                );
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw e;
        } finally {
            Pilot.close();
        }

        return reservation;
    }

    @Override
    public List<Reservation> getAllReservation() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Reservation ";
        List<Reservation> reservations = new ArrayList<>();
        try {
            Pilot.connecte("hotelreservation", "root", "");
            PreparedStatement stmt = Pilot.connexion.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Reservation reservation = new Reservation(
                        rs.getInt("idReservation"),
                        rs.getInt("idUser"),
                        rs.getDate("dateDebut"),
                        rs.getDate("dateFin"),
                        rs.getInt("numberGuests"),
                        rs.getInt("numberAdults"),
                        rs.getDate("dateAjout"),
                        rs.getDate("dateUpdate")
                );
                reservations.add(reservation);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw e;
        } finally {
            Pilot.close();
        }
        return reservations;
    }
    @Override
    public int getNbrReservations() throws SQLException, ClassNotFoundException {
        String sql = "SELECT count(*) FROM Reservation";
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
    public void addNewReservation(int idRoom, int idUser, int nbrGuests, int nbrAdults, Date debut, Date fin) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Reservation (idRoom, idUser, numberGuests, numberAdults, dateDebut, dateFin, dateAjout, dateUpdate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            Pilot.connecte("hotelreservation", "root", "");
            PreparedStatement stmt = Pilot.connexion.prepareStatement(sql);
            stmt.setInt(1, idRoom);
            stmt.setInt(2, idUser);
            stmt.setInt(3, nbrGuests);
            stmt.setInt(4, nbrAdults);
            stmt.setDate(5, new java.sql.Date(debut.getTime()));
            stmt.setDate(6, new java.sql.Date(fin.getTime()));
            stmt.setDate(7, new java.sql.Date(System.currentTimeMillis()));
            stmt.setDate(8, new java.sql.Date(System.currentTimeMillis()));
            stmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw e;
        } finally {
            Pilot.close();
        }
    }


    @Override
    public List<Reservation> getAllReservationsRoom(int idRoom) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Reservation WHERE idRoom = ?";
        List<Reservation> reservations = new ArrayList<>();

        try {
            Pilot.connecte("hotelreservation", "root", "");
            PreparedStatement stmt = Pilot.connexion.prepareStatement(sql);
            stmt.setInt(1, idRoom);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Reservation reservation = new Reservation(
                        rs.getInt("idReservation"),
                        rs.getInt("idUser"),
                        //rs.getInt("idRoom"),
                        rs.getDate("dateDebut"),
                        rs.getDate("dateFin"),
                        rs.getInt("numberGuests"),
                        rs.getInt("numberAdults"),
                        rs.getDate("dateAjout"),
                        rs.getDate("dateUpdate")
                        // rs.getString("confirmationCode"),
                        // rs.getBoolean("statue")
                );
                reservations.add(reservation);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw e;
        } finally {
            Pilot.close();
        }

        return reservations;
    }

    @Override
    public List<Reservation> getAllReservationsUser(int idUser) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Reservation WHERE idUser = ?";
        List<Reservation> reservations = new ArrayList<>();

        try {
            Pilot.connecte("hotelreservation", "root", "");
            PreparedStatement stmt = Pilot.connexion.prepareStatement(sql);
            stmt.setInt(1, idUser);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Reservation reservation = new Reservation(
                        rs.getInt("idReservation"),
                        rs.getInt("idUser"),
                        //rs.getInt("idRoom"),
                        rs.getDate("dateDebut"),
                        rs.getDate("dateFin"),
                        rs.getInt("numberGuests"),
                        rs.getInt("numberAdults"),
                        rs.getDate("dateAjout"),
                        rs.getDate("dateUpdate")
                        //rs.getString("confirmationCode"),
                        //rs.getBoolean("statue")
                );
                reservations.add(reservation);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw e;
        } finally {
            Pilot.close();
        }

        return reservations;
    }

    @Override
    public void UpdateReservation(int idReservation, int nbrGuests, int nbrAdults, Date debut, Date fin) throws SQLException, ClassNotFoundException {
        String sql1 = "SELECT idRoom FROM Reservation WHERE idReservation = ?";
        String sql2 = "UPDATE Reservation SET numberGuests = ?, numberAdults = ?, dateDebut = ?, dateFin = ?, dateUpdate = ? WHERE idReservation = ?";

        try {
            Pilot.connecte("hotelreservation", "root", "");

            PreparedStatement stmt = Pilot.connexion.prepareStatement(sql1);
            stmt.setInt(1, idReservation);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int idroom = rs.getInt(1);
            RoomDAOI rm = new RoomDAOImpl();
            if (rm.isDisponible(idroom, debut, fin)) {
                stmt = Pilot.connexion.prepareStatement(sql2);
                stmt.setInt(1, nbrGuests);
                stmt.setInt(2, nbrAdults);
                stmt.setDate(3, new java.sql.Date(debut.getTime()));
                stmt.setDate(4, new java.sql.Date(fin.getTime()));
                stmt.setDate(5, new java.sql.Date(System.currentTimeMillis()));
                stmt.setInt(6, idReservation);
                stmt.executeUpdate();
            } else {
                throw new SQLException("La date choisi n'est pas convenable , réessayer !!");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw e;
        } finally {
            Pilot.close();
        }
    }

    @Override
    public void DeleteReservation(int idReservation) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Reservation WHERE idReservation = ?";

        try {
            Pilot.connecte("hotelreservation", "root", "");
            PreparedStatement stmt = Pilot.connexion.prepareStatement(sql);
            stmt.setInt(1, idReservation);
            stmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw e;
        } finally {
            Pilot.close();
        }
    }


    @Override
    public List<Integer> getIdRoom(int idReservation) throws SQLException {
        String sql = "SELECT idChambre FROM reservationchambre WHERE idReservation = ?";
        List<Integer> idRoom = new ArrayList<>();
        try {
            Pilot.connecte("hotelreservation", "root", "");
            PreparedStatement stmt = Pilot.connexion.prepareStatement(sql);
            stmt.setInt(1, idReservation);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                idRoom.add(rs.getInt(1));
            }
        } catch (RuntimeException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return idRoom;
    }


}
