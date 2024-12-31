package com.example.hoteljavafx.Utils;


import org.mindrot.jbcrypt.BCrypt;

public class Util {

    /**
     *Fonction pour hacher un mot de passe
     */
    public static String hashPassword(String password) {
        // Génère un salt (sel) pour le hachage
        String salt = BCrypt.gensalt();
        // Hache le mot de passe avec le salt généré
        return BCrypt.hashpw(password, salt);
    }

    /**
     *Fonction pour vérifier si le mot de passe fourni correspond au hachage stocké
     */
    public static boolean verifyUserPassword(String password, String storedHashedPassword) {
        // Vérifie si le mot de passe fourni correspond au hachage stocké
        return BCrypt.checkpw(password, storedHashedPassword);
    }
}
