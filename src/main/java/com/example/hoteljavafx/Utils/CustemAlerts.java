package com.example.hoteljavafx.Utils;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CustemAlerts {

    public static void showCustomAlert(String title, String message, String alertType, Class<?> clazz) {
        Stage alertStage = new Stage();
        alertStage.setTitle(title);

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-width: 1; -fx-border-radius: 5; -fx-background-radius: 5;");

        String color;
        switch (alertType.toLowerCase()) {
            case "success":
                color = "#d4edda";
                break;
            case "error":
                color = "#f8d7da";
                break;
            case "warning":
                color = "#fff3cd";
                break;
            default:
                color = "#cce5ff";
                break;
        }
        vbox.setStyle(vbox.getStyle() + "-fx-background-color: " + color + ";");

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label messageLabel = new Label(message);
        messageLabel.setStyle("-fx-font-size: 14px;");

        Button closeButton = new Button("Fermer");
        closeButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-border-radius: 5; -fx-background-radius: 5;");
        closeButton.setOnAction(e -> alertStage.close());

        vbox.getChildren().addAll(titleLabel, messageLabel, closeButton);
        VBox.setMargin(closeButton, new Insets(10, 0, 0, 0));

        Scene scene = new Scene(vbox, 300, 150);
        scene.getStylesheets().add(clazz.getResource("/Styles/stylesA.css").toExternalForm());
        alertStage.setScene(scene);
        alertStage.show();
    }
}
