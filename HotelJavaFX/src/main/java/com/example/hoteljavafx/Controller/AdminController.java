package com.example.hoteljavafx.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML
    private BorderPane contentPane;
    @FXML
    private VBox vb;

    @FXML
    private void handleDashboardClick() throws IOException {
        //contentPane.setCenter(vb);
        return;
    }

    @FXML
    private void handleReservationsClick() throws IOException {
        loadView("/Views/reservations.fxml");
    }

    @FXML
    private void handleUsersClick() throws IOException {
        loadView("/Views/utilisateurs.fxml");
    }
    @FXML
    private void handleHotelsClick() throws IOException {
        loadView("/Views/Hotels.fxml");
    }

    @FXML
    private void handleStatisticsClick() throws IOException {
        loadView("/Views/statistiques.fxml");
    }

    @FXML
    private void handleSettingsClick() throws IOException {
        loadView("/Views/parametres.fxml");
    }

    @FXML
    private void handleAccountClick() throws IOException {
        loadView("/Views/mon_compte.fxml");
    }

    private void loadView(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        VBox view = loader.load();
        contentPane.setCenter(view);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
