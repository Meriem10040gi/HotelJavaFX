package com.example.hoteljavafx.DAO;

import com.example.hoteljavafx.Model.User;
import com.example.hoteljavafx.Utils.Role;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface UserDAOI {
    User getUser(int id) throws SQLException;

    int getNbrUsers() throws SQLException, ClassNotFoundException;

    String[] getHashedPassword(String email);

    String addNewUser(String nom, String prenom, String address, String email, String phone, Role role, String password, String confPassword) throws IOException,IllegalArgumentException, SQLException;

    List<User> getAllUsers() throws IOException, SQLException;

    void UpdateUser(String nom, String prenom, String address, String email, String phone,int id) throws IOException, SQLException;

    void UpdatePasswordUser(String email, String password, String confPassword) throws IOException, SQLException;

    void DeleteUser(int idUser) throws IOException, SQLException;

    boolean isConnected();

    List<Integer> selectFavoriteHotels(int idUser);

    void insertFavoritehotel(int idUser, int idHotel);

    void deleteFavoritehotel(int idUser, int idHotel);
}
