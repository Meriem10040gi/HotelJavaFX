package com.example.hoteljavafx.Controller;

import com.example.hoteljavafx.DAO.UserDAOI;
import com.example.hoteljavafx.DAO.UserDAOImpl;
import com.example.hoteljavafx.Utils.Role;
import com.example.hoteljavafx.Utils.Session;
import com.example.hoteljavafx.Utils.Util;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class loginController   {

    // Liens avec les éléments définis dans le FXML (fx:id)
    @FXML
    private BorderPane content;
    @FXML
    private Button loginbtn;

    @FXML
    private TextField email;

    @FXML
    private TextField password;

    // Objet Alert pour afficher les messages
    private Alert alert;

    // Création d'une instance de UserDao en utilisant l'implémentation UserDaoImpl
    UserDAOI userdao=new UserDAOImpl();

    /**
     * Méthode pour afficher un message d'erreur.
     *
     * @param message Le message d'erreur à afficher.
     */
    private void errorMessage(String message) {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Ajoute le style "error" aux champs concernés si absent.
     */
    private void addErrorStyle() {
        TextField[] fields = { password, email };
        for (TextField field : fields) {
            if (!field.getStyleClass().contains("error")) {
                field.getStyleClass().add("error");
            }
        }
    }

    /**
     * Supprime le style "error" des champs concernés.
     */
    private void removeErrorStyle() {
        TextField[] fields = { password, email };
        for (TextField field : fields) {
            field.getStyleClass().remove("error");
        }
    }

    /**
     * Méthode pour gérer l'action de connexion (login).
     *
     * @param event L'événement déclenché lors du clic sur le bouton "Log in".
     */
    @FXML
    public void loginAccount(ActionEvent event) {
        // Vérifier que les champs ne sont pas vides
        if (email.getText().isEmpty() || password.getText().isEmpty()) {
            addErrorStyle();
            errorMessage("Please fill in all the fields.");
        } else {
            removeErrorStyle();

            // Appeler la méthode `getHashedPassword` pour obtenir le mot de passe de l'utilisateur
            String[] storedHashedPassword =userdao.getHashedPassword (email.getText());
            if (storedHashedPassword != null) {
                // Vérifier si le mot de passe fourni correspond au hachage stocké
                if (Util.verifyUserPassword(password.getText(),storedHashedPassword[1])) {
                    // Identifiants corrects
                    Session.getInstance().startSession(Integer.parseInt(storedHashedPassword[0]),storedHashedPassword[2]);
                    try {
                        if(Session.getInstance().getRole().equals("Client")){
                            Parent root = FXMLLoader.load(getClass().getResource("/Views/HomePage.fxml"));
                            Stage stage = new Stage();
                            stage.setTitle("User Interface");
                            stage.setScene(new Scene(root));
                            stage.show();}
                        else{
                            Parent root = FXMLLoader.load(getClass().getResource("/Views/Dashboard.fxml"));
                            Stage stage = new Stage();
                            stage.setTitle("User Interface");
                            stage.setScene(new Scene(root));
                            stage.show();}
                        loginbtn.getScene().getWindow().hide();
                    } catch (IOException e) {
                        e.printStackTrace();
                        errorMessage("An error occurred while loading the user interface.");
                    }
                } else {
                    addErrorStyle();
                    // Mot de passe incorrect
                    errorMessage("Incorrect email or password.");
                }
            } else {
                addErrorStyle();
                // Aucun utilisateur trouvé
                errorMessage("Incorrect email or password.");
            }
        }
    }

    /**
     * Ouvre la fenêtre "Sign Up" et ferme la fenêtre de connexion actuelle.
     *
     * @param event L'événement déclenché lors du clic.
     */
    @FXML
    void switchsignup(ActionEvent event) {
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("/Views/signup.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Sign Up");
            stage.setScene(new Scene(root));
            stage.show();
            loginbtn.getScene().getWindow().hide();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
