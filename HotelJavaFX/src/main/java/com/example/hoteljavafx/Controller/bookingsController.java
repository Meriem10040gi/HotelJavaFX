package com.example.hoteljavafx.Controller;

import com.example.hoteljavafx.DAO.ReservationDAOI;
import com.example.hoteljavafx.DAO.ReservationDAOImpl;
import com.example.hoteljavafx.Utils.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class bookingsController implements Initializable {

    @FXML
    private TableView<ListReservation> reservationData;

    @FXML
    private TableColumn<ListReservation, Integer> colnumero;
    @FXML
    private TableColumn<ListReservation, String> colcheckin;
    @FXML
    private TableColumn<ListReservation, String> colcheckout;
    @FXML
    private TableColumn<ListReservation, String> colchambre;
    @FXML
    private TableColumn<ListReservation, String> colhotel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            ShowReservations();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Error initializing reservations: " + e.getMessage());
        }
    }

    void ShowReservations() throws SQLException, ClassNotFoundException {
        ReservationDAOI res = new ReservationDAOImpl();
        ObservableList<ListReservation> reservations = res.getListReservations(Session.getInstance().getUserId());
        colnumero.setCellValueFactory(new PropertyValueFactory<>("id"));
        colcheckin.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
        colcheckout.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));
        colchambre.setCellValueFactory(new PropertyValueFactory<>("idRooms"));
        colhotel.setCellValueFactory(new PropertyValueFactory<>("hotelName"));

        reservationData.setItems(reservations);
    }

}