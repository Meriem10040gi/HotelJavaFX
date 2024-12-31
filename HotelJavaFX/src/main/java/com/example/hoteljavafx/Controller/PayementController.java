package com.example.hoteljavafx.Controller;

import com.example.hoteljavafx.Model.*;
import com.example.hoteljavafx.DAO.*;
import com.example.hoteljavafx.Utils.*;
import javafx.application.Platform;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class PayementController implements Initializable {

    @FXML
    private WebView WV;
    @FXML
    VBox VBOX1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FrontMETHODS fmethod = new FrontMETHODS();
        try {
            fmethod.setReceipt(VBOX1, viewHotelController.getIdRoomsReserved(), viewHotelController.getCheckInSt(),viewHotelController.getCheckOutSt());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Start the payment server in a separate thread
        startPaymentServer();

        WebEngine webEngine = WV.getEngine();
        webEngine.setJavaScriptEnabled(true);

        // Listen for the WebEngine's load state
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldState, newState) -> {
            if (newState == State.SUCCEEDED) {
                System.out.println("Payment page loaded successfully.");
            }
        });

        // Load the payment HTML page
        loadPaymentPage();
    }

    /**
     * Starts the Stripe Payment Server in a separate thread.
     */
    private void startPaymentServer() {
        new Thread(() -> StripePaymentServer.startPaymentServer()).start();
    }

    /**
     * Loads the Stripe Payment HTML page into the WebView.
     */
    private void loadPaymentPage() {
        String workingDir = System.getProperty("user.dir");
        String relativePath = "src/main/resources/js/Stripe.html";
        String absolutePath = Paths.get(workingDir, relativePath).toString();
        WV.getEngine().load("file:///" + absolutePath);
    }

    public static void processPaymentResult(String requestBody) {
        Platform.runLater(() -> {
            try {
                JSONObject resultData = new JSONObject(requestBody);
                boolean success = resultData.getBoolean("success");
                String message = resultData.optString("message", "No additional details.");

                if (success) {
                    showAlert("Payment Success", "Payment was successful!\n" + message);
                    Date dateD=Date.from(viewHotelController.getCheckInSt().atStartOfDay(ZoneId.systemDefault()).toInstant());
                    Date dateF=Date.from(viewHotelController.getCheckOutSt().atStartOfDay(ZoneId.systemDefault()).toInstant());
                    RoomDAOI rdi=new RoomDAOImpl();
                    List<Room> lrooms=rdi.getListRoom(viewHotelController.getIdRoomsReserved());
                    Reservation r= new Reservation(0,Session.getInstance().getUserId(),dateD,dateF,new Date(),new Date(),lrooms);
                    ReservationDAOI rdi1=new ReservationDAOImpl();
                    rdi1.createReservation(r);
                } else {
                    showAlert("Payment Failed", "Payment failed. Please try again.\n" + message);
                }
            } catch (Exception e) {
                showAlert("Error", "Failed to process payment result: " + e.getMessage());
            }
        });
    }

    private static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
