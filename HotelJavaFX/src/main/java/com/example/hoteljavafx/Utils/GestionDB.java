package com.example.hoteljavafx.Utils;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Data
@NoArgsConstructor
public class GestionDB {

    public Connection connexion;

    public void connecte(String BD, String User, String Password) throws SQLException, ClassNotFoundException {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("la classe not found",e);
        }
        String url = "jdbc:mysql://localhost:3306/"+BD;
        try {
            this.connexion = DriverManager.getConnection(url, User, Password);
        } catch (SQLException e) {
            System.err.println("Erreur de connexion à la base de données : " + e.getMessage() );
            throw e;
        }
    }
    public void close() {
        try {
            if (connexion != null) {
                connexion.close();
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
        }
    }
}
