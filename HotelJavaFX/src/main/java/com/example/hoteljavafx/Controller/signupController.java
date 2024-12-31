package com.example.hoteljavafx.Controller;

import com.example.hoteljavafx.DAO.UserDAOI;
import com.example.hoteljavafx.DAO.UserDAOImpl;
import com.example.hoteljavafx.Model.User;
import com.example.hoteljavafx.Utils.Role;
import com.example.hoteljavafx.Utils.Util;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class signupController {

    // Liens avec les éléments définis dans le FXML (fx:id)
    @FXML
    private TextField address;

    @FXML
    private TextField email;

    @FXML
    private TextField fname;

    @FXML
    private TextField lname;

    @FXML
    private TextField password;

    @FXML
    private TextField phone;

    @FXML
    private TextField confirmpassword;

    @FXML
    private Button signup;

    // Création d'une instance de UserDao en utilisant l'implémentation UserDaoImpl
   UserDAOI userdao=new UserDAOImpl();

    /**
     * Affiche un message de succès dans une boîte de dialogue d'information.
     * @param message Le message à afficher.
     */
    private void successMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Affiche un message d'erreur dans une boîte de dialogue d'erreur.
     * @param message Le message à afficher.
     */
    private void errorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Vide tous les champs du formulaire d'inscription.
     */
    void clear() {
        fname.setText(null);
        lname.setText(null);
        address.setText(null);
        email.setText(null);
        phone.setText(null);
        confirmpassword.setText(null);
        password.setText(null);
    }

    /**
     *  Méthode pour vérifier la validité de numero de phone
     */
    private boolean isValidPhoneNumber(String phone) {
        // Regex générale pour un numéro de téléphone international
        String phoneRegex = "^\\+?[0-9\\-\\s()]{8,15}$";

        // Vérification du format
        return phone.matches(phoneRegex);
    }


    /**
     *  Méthode pour vérifier la validité d email
     */
    private boolean isValidEmail(String email) {
        // Vérification du format avec une expression régulière
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!email.matches(emailRegex)) {
            return false;
        }

        // Vérifier la longueur minimale et maximale
        if (email.length() < 5 || email.length() > 255) {
            return false;
        }

        // Vérifier que le domaine contient un point après le "@"
        int atIndex = email.indexOf('@');
        int lastDotIndex = email.lastIndexOf('.');
        if (atIndex == -1 || lastDotIndex == -1 || lastDotIndex < atIndex) {
            return false;
        }

        return true;
    }

    /**
     * Vérifie si les deux mots de passe sont identiques.
     *
     * @param password        Le mot de passe principal.
     * @param confirmPassword Le mot de passe de confirmation.
     * @return true si identiques, false sinon.
     */
    private boolean arePasswordsMatching(String password, String confirmPassword) {
        // Vérifier si les mots de passe correspondent
        return password.equals(confirmPassword);
    }


    /**
     *  Méthode pour vérifier la validité du mot de passe
     */
    private boolean isValidPassword(String password) {
        // Vérifier la longueur minimale
        if (password.length() < 8) {
            return false;
        }
        // Vérifier la présence d'une majuscule
        if (!password.matches(".*[A-Z].*")) {
            return false;
        }
        // Vérifier la présence d'une minuscule
        if (!password.matches(".*[a-z].*")) {
            return false;
        }
        // Vérifier la présence d'un chiffre
        if (!password.matches(".*\\d.*")) {
            return false;
        }
        // Vérifier la présence d'un caractère spécial
        if (!password.matches(".*[@#$%&*!^].*")) {
            return false;
        }
        return true;
    }

    /**
     *  Méthode pour ajouter le style "error"
     */
    private void addErrorStyle(TextField field) {
        if (!field.getStyleClass().contains("error")) {
            field.getStyleClass().add("error");
        }
    }

    /**
     *  Méthode pour retirer le style "error"
     */
    private void removeErrorStyle(TextField field) {
        field.getStyleClass().remove("error");
    }

    /**
     *  Méthode pour valider les champs
     */
    private void validateFields() {
        // Liste des champs à tester
        TextField[] fields = { address, email, fname, lname, password, phone , confirmpassword };

        for (TextField field : fields) {
            if (field.getText() == null || field.getText().trim().isEmpty()) {
                // Si le champ est vide, ajoute la classe "error"
                addErrorStyle(field);
            } else {
                // Si le champ n'est pas vide, on vérifie sa validité
                if (field == email && !isValidEmail(field.getText())) {
                    // Si l'email est invalide
                    addErrorStyle(field);
                } else if (field == phone && !isValidPhoneNumber(field.getText())) {
                    // Si le numéro de téléphone est invalide
                    addErrorStyle(field);
                } else if (field == password && !isValidPassword(field.getText())) {
                    // Si le mot de passe est invalide
                    addErrorStyle(field);
                } else if (field == confirmpassword && !arePasswordsMatching(password.getText(),confirmpassword.getText())) {
                    // Si le mot de passe est invalide
                    addErrorStyle(field);
                } else {
                    // Si le champ est valide, on retire la classe "error"
                    removeErrorStyle(field);
                }
            }
        }
    }



    /**
     * Gère l'inscription d'un nouvel utilisateur.
     * Vérifie les champs obligatoires, insère les données dans la base de données,
     * et affiche un message de succès ou d'erreur selon le résultat.
     */
    @FXML
    void signupAccount() throws SQLException, IOException {
        if (fname.getText().isEmpty() || lname.getText().isEmpty() || address.getText().isEmpty() ||
                email.getText().isEmpty() || phone.getText().isEmpty()  || confirmpassword.getText().isEmpty()  ||
                password.getText().isEmpty()) {
            validateFields();
            errorMessage("Please fill in all the fields.");
        } else if (!isValidEmail(email.getText())) {
            validateFields();
            errorMessage("Invalid email format. Please enter a valid email address.");
        } else if (!isValidPhoneNumber(phone.getText())) {
            validateFields();
            errorMessage("Invalid phone number. Please enter a valid international phone number.");
        } else if (!isValidPassword(password.getText())) {
            validateFields();
            errorMessage("- Password must contain at least:\n- One uppercase letter\n- One lowercase letter\n- One number\n- One special character\n- Minimum 8 characters.");
        } else if (!arePasswordsMatching(password.getText(),confirmpassword.getText())) {
            validateFields();
            errorMessage("Error , Passwords do not match.");
        } else {
            validateFields();

            // Création d'un nouvel objet User à partir des champs
            User user = new User(
                    lname.getText(),
                    fname.getText(),
                    address.getText(),
                    email.getText(),
                    phone.getText(),
                    Util.hashPassword(password.getText())// Mot de passe haché
            );

            String res = userdao.addNewUser(user.getNom(),user.getPrenom(),user.getAddress(),user.getEmail(),user.getPhone(),Role.Client,user.getPassword(),user.getPassword());

            if (res.equals("success")) {
                // Si l'insertion a réussi
                successMessage("Account successfully created!");
                clear();
                switchlogin(null);
            } else {
                // Si l'insertion a échoué
                errorMessage("The account could not be created. Please try again.");
            }
        }
    }

    /**
     * Permet de passer de la fenêtre d'inscription à la fenêtre de connexion.
     * @param event L'événement déclenché (ex. clic sur un bouton).
     */
    @FXML
    void switchlogin(ActionEvent event) {
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("/Views/login.fxml"));
            Stage stage = new Stage();
            stage.setTitle("log In");
            stage.setScene(new Scene(root));
            stage.show();
            signup.getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
