package com.example.hoteljavafx.DAO;

import com.example.hoteljavafx.Model.Reservation;
import com.example.hoteljavafx.Model.Room;
import com.example.hoteljavafx.Utils.DateRange;
import com.example.hoteljavafx.Utils.GestionDB;
import com.example.hoteljavafx.Utils.ListReservation;
import com.example.hoteljavafx.Utils.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
                        rs.getDate("dateAjout"),
                        rs.getDate("dateUpdate"),
                        null
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
                        rs.getDate("dateAjout"),
                        rs.getDate("dateUpdate"),
                        null
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
                        rs.getDate("dateDebut"),
                        rs.getDate("dateFin"),
                        rs.getDate("dateAjout"),
                        rs.getDate("dateUpdate"),
                        null
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
                        rs.getDate("dateDebut"),
                        rs.getDate("dateFin"),
                        rs.getDate("dateAjout"),
                        rs.getDate("dateUpdate"),
                        null
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

    @Override
    public void createReservation(Reservation reservation) {
        String sql="insert into reservation(idUser,dateDebut,dateFin,dateAjout,dateUpdate) values(?,?,?,?,?)";
        try {
            Pilot.connecte("hotelreservation","root", "");
            PreparedStatement ps= Pilot.connexion.prepareStatement(sql);

            ps.setInt(1,reservation.getIdUser());
            ps.setDate(2,new java.sql.Date(reservation.getDateDebut().getTime()));
            ps.setDate(3,new java.sql.Date(reservation.getDateFin().getTime()));
            ps.setDate(4,new java.sql.Date(reservation.getDateAjout().getTime()));
            ps.setDate(5,new java.sql.Date(reservation.getDateUpdate().getTime()));
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        sql="insert into reservationchambre(idChambre,idReservation) values(?,?)";
        for(Room e: reservation.getRooms()){
            try{
                PreparedStatement ps=Pilot.connexion.prepareStatement(sql);
                ps.setInt(1,e.getIdRoom());
                ps.setInt(2,getLastReservationId(Session.getInstance().getUserId()));
                ps.executeUpdate();
                System.out.println("t\n");
            } catch (SQLException e1) {
                throw new RuntimeException(e1);
            }
        }



    }
    @Override
    public int getLastReservationId(int idUser) throws SQLException {
        String sql="select max(idReservation) from reservation where idUser=?";
        try{
            Pilot.connecte("hotelreservation","root", "");
            PreparedStatement ps=Pilot.connexion.prepareStatement(sql);
            ps.setInt(1,idUser);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
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
    public DateRange getDateOfaReservation(int idReser) throws SQLException, ClassNotFoundException {
        Pilot.connecte("hotelreservation", "root", "");
        String req = "SELECT dateDebut, dateFin FROM reservation WHERE idReservation = ?";
        DateRange dr = null;

        try (PreparedStatement ps = Pilot.connexion.prepareStatement(req)) {
            ps.setInt(1, idReser);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    dr = new DateRange(rs.getDate("dateDebut"), rs.getDate("dateFin"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching reservation dates", e);
        }

        return dr;
    }
    @Override
    public ObservableList<ListReservation> getListReservations(int id) throws SQLException, ClassNotFoundException {
        ObservableList<ListReservation> listReservations = FXCollections.observableArrayList();
        Pilot.connecte("hotelreservation", "root", "");

        String req = "SELECT * FROM reservation WHERE idUser=?";
        try (PreparedStatement ps = Pilot.connexion.prepareStatement(req)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ListReservation lr = new ListReservation();
                    lr.setId(rs.getInt("idReservation"));
                    lr.setCheckInDate(rs.getDate("dateDebut").toString());
                    lr.setCheckOutDate(rs.getDate("dateFin").toString());
                    String req2 = "SELECT idChambre FROM reservationchambre WHERE idReservation=?";
                    try (PreparedStatement ps2 = Pilot.connexion.prepareStatement(req2)) {
                        ps2.setInt(1, rs.getInt("idReservation"));
                        try (ResultSet rs2 = ps2.executeQuery()) {
                            StringBuilder listChambres = new StringBuilder();
                            int idC = 0;
                            while (rs2.next()) {
                                if (listChambres.length() > 0) {
                                    listChambres.append(",");
                                }
                                idC = rs2.getInt("idChambre");
                                listChambres.append(idC);
                            }
                            lr.setIdRooms(listChambres.toString());
                            if (idC > 0) {
                                String req3 = "SELECT h.name FROM room r JOIN hotel h ON r.idHotel = h.idHotel WHERE r.idRoom=?";
                                try (PreparedStatement ps3 = Pilot.connexion.prepareStatement(req3)) {
                                    ps3.setInt(1, idC);
                                    try (ResultSet rs3 = ps3.executeQuery()) {
                                        if (rs3.next()) {
                                            lr.setHotelName(rs3.getString("name"));
                                        }
                                    }
                                }
                            }
                        }
                    }
                    listReservations.add(lr);
                }
            }
        }
        return listReservations;
    }

}
