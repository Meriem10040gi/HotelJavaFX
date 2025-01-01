package com.example.hoteljavafx.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class PayementSuccessController {

    @FXML
    private BorderPane content;
    @FXML
    void ConsultONCLICK(MouseEvent event) throws IOException {
        loadView("bookings.fxml");
    }
    @FXML
    private void loadView(String fxmlFile) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/" + fxmlFile));
        BorderPane view = loader.load();
        content.setCenter(view);
    }

}
