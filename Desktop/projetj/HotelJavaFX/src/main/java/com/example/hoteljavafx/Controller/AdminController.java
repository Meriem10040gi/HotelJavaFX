package com.example.hoteljavafx.Controller;

import com.example.hoteljavafx.Service.DashboardService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Month;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.Scene;

public class AdminController {
    @FXML
    public Label nbrReservations;
    @FXML
    public Label nbrChambres;
    @FXML
    public Label nbrHotels;
    @FXML
    public Label nbrUsers;
    @FXML
    public Label simpleRoom;
    @FXML
    public Label familialeRoom;
    @FXML
    public Label doubleRoom;
    @FXML
    public Label luxeRoom;
    @FXML
    private BorderPane contentPane;
    @FXML
    private VBox vb;
    @FXML
    private LineChart<String, Number> reservationChart;
    @FXML
    private PieChart hotelChart;

    DashboardService dashboardService = new DashboardService();

    @FXML
    private void handleDashboardClick() throws IOException {
        contentPane.setCenter(vb);
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

    public void initialize() {
        try {
            dashboardService.getAll();
            nbrChambres.setText(String.valueOf(dashboardService.getNbrRooms()));
            nbrHotels.setText(String.valueOf(dashboardService.getNbrHotels()));
            nbrUsers.setText(String.valueOf(dashboardService.getNbrUsers()));
            nbrReservations.setText(String.valueOf(dashboardService.getNbrReservations()));
            simpleRoom.setText(String.valueOf(dashboardService.getNbrSingle()));
            doubleRoom.setText(String.valueOf(dashboardService.getNbrDouble()));
            luxeRoom.setText(String.valueOf(dashboardService.getNbrLuxe()));
            familialeRoom.setText(String.valueOf(dashboardService.getNbrFamille()));
            Map<Integer, Long> reservationsPerMonth = dashboardService.getReservationsForCurrentYear();
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Reservations per Month");
            for (int month = 1; month <= 12; month++) {
                series.getData().add(new XYChart.Data<>(getMonthName(month), reservationsPerMonth.get(month)));
            }
            reservationChart.getData().add(series);
            double[] percentages = dashboardService.calculateRatingPercentages();
            PieChart.Data slice1 = new PieChart.Data("5-4", percentages[0]);
            PieChart.Data slice2 = new PieChart.Data("4-3", percentages[1]);
            PieChart.Data slice3 = new PieChart.Data("3-2", percentages[2]);
            PieChart.Data slice4 = new PieChart.Data("2-1", percentages[3]);
            PieChart.Data slice5 = new PieChart.Data("1-0", percentages[4]);
            hotelChart.getData().addAll(slice1, slice2, slice3, slice4, slice5);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getMonthName(int month) {
        switch (month) {
            case 1: return "January";
            case 2: return "February";
            case 3: return "March";
            case 4: return "April";
            case 5: return "May";
            case 6: return "June";
            case 7: return "July";
            case 8: return "August";
            case 9: return "September";
            case 10: return "October";
            case 11: return "November";
            case 12: return "December";
            default: return "";
        }
    }
}
