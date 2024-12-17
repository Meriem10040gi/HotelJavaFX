package com.example.hoteljavafx.DAO;

import com.example.hoteljavafx.Model.User;
import com.example.hoteljavafx.Utils.*;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAOI{
    GestionDB Pilot = new GestionDB();
    @Override
    public User getUser(int id) throws SQLException {
        User user = new User();
        String sql = "SELECT * FROM User WHERE idUser = ?";
        try  {
            Pilot.connecte("hotelreservation", "root", "");
            PreparedStatement stmt = Pilot.connexion.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(
                        rs.getInt("idUser"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("address"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        null,
                        rs.getString("role"),
                        rs.getDate("dateAjout"),
                        rs.getDate("dateUpdate")
                );
            }
            Pilot.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public int getNbrUsers() throws SQLException, ClassNotFoundException {
        String sql = "SELECT count(*) FROM User";
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
    public String addNewUser(String nom, String prenom, String address, String email, String phone, Role role, String password, String confPassword) throws IOException,IllegalArgumentException, SQLException {
        String sql = "INSERT INTO User (nom, prenom, address, email, phone, password,role,dateAjout,dateUpdate) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";
        try  {
            Pilot.connecte("hotelreservation", "root", "");
            PreparedStatement stmt = Pilot.connexion.prepareStatement(sql);
            String checkEmail = "SELECT COUNT(*) FROM User WHERE email = ?";
            PreparedStatement checkStmt = Pilot.connexion.prepareStatement(checkEmail);
            checkStmt.setString(1, email);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                throw new SQLException("Cet email est déjà utilisé.");
            }
            stmt.setString(1, nom);
            stmt.setString(2, prenom);
            stmt.setString(3, address);
            stmt.setString(4, email);
            stmt.setString(5, phone);
            stmt.setString(6, password);
            if (role== null) {
                stmt.setString(7, "User");
            }
            else{
                stmt.setString(7, role.toString());
            }
            stmt.setDate(8, new java.sql.Date(System.currentTimeMillis()));
            stmt.setDate(9, new java.sql.Date(System.currentTimeMillis()));
            stmt.executeUpdate();
            Pilot.close();
            return "L'ajout est effectuée avec success";
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de l'utilisateur : " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return "Erreur lors de l'ajout ";
    }

    @Override
    public List<User> getAllUsers() throws IOException, SQLException {

        String sql = "SELECT * FROM User";
        List<User> users = new ArrayList<>();
        try {
            Pilot.connecte("hotelreservation", "root", "");
            PreparedStatement stmt = Pilot.connexion.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = new User(
                        rs.getInt("idUser"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("address"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        null,
                        rs.getString("role"),
                        rs.getDate("dateAjout"),
                        rs.getDate("dateUpdate")
                );
                users.add(user);
            }
            Pilot.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void UpdateUser(String nom, String prenom, String address, String email, String phone) throws IOException, SQLException {
        String sql = "UPDATE User SET nom = ?, prenom = ?, address = ?, email = ?, phone = ? WHERE email = ?";

        try  {
            Pilot.connecte("hotelreservation", "root", "");
            PreparedStatement stmt = Pilot.connexion.prepareStatement(sql);
            stmt.setString(1, nom);
            stmt.setString(2, prenom);
            stmt.setString(3, address);
            stmt.setString(4, email);
            stmt.setString(5, phone);
            stmt.setString(6, email);
            stmt.executeUpdate();
            Pilot.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void UpdatePasswordUser(String email, String password, String confPassword) throws IOException, SQLException {
        if (!password.equals(confPassword)) {
            throw new IllegalArgumentException("Les mots de passe ne correspondent pas");
        }

        String sql = "UPDATE User SET password = ? WHERE email = ?";

        try  {
            Pilot.connecte("hotelreservation", "root", "");
            PreparedStatement stmt = Pilot.connexion.prepareStatement(sql);
            stmt.setString(1, password);
            stmt.setString(2, email);
            stmt.executeUpdate();
            Pilot.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void DeleteUser(int idUser) throws IOException, SQLException {
        String sql = "DELETE FROM User WHERE idUser = ?";

        try  {
            Pilot.connecte("hotelreservation", "root", "");
            PreparedStatement stmt = Pilot.connexion.prepareStatement(sql);
            stmt.setInt(1, idUser);
            stmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int login(String email, String password) throws IOException, SQLException {
        String sql = "SELECT * FROM User WHERE email = ? AND password = ?";
        try  {
            Pilot.connecte("hotelreservation", "root", "");
            PreparedStatement stmt = Pilot.connexion.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Session.getInstance().startSession(rs.getInt("idUser"));
                return 1;
            } else {
                throw new IOException("données erronées");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void logout() {
        Session.getInstance().endSession();
    }

    @Override
    public boolean isConnected()  {
        return Session.getInstance().isConnected();
    }
}
