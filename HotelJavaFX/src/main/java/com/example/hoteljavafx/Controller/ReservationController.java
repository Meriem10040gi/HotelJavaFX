package com.example.hoteljavafx.Controller;


import com.example.hoteljavafx.Model.Reservation;
import com.example.hoteljavafx.DAO.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationController {


    @FXML
    private VBox reservationListContainer;


    private final ReservationDAOI reservation = new ReservationDAOImpl();



    private void loadAndDisplayReservation() throws SQLException, IOException, ClassNotFoundException {
        List<Reservation> reservations = reservation.getAllReservation();


        for (Reservation reservation : reservations) {

            HBox reservationRow = createReservationRow(reservation);
            reservationListContainer.getChildren().add(reservationRow);
        }
    }
    @FXML
    public void initialize() throws SQLException, IOException, ClassNotFoundException {
        // Charger et afficher les utilisateurs
        loadAndDisplayReservation();
    }
    private HBox createReservationRow(Reservation reservation) throws SQLException, IOException {
        HBox reservationRow = new HBox();
        UserDAOI user = new UserDAOImpl();
        ReservationDAOI reservationService = new ReservationDAOImpl();
        reservationRow.setStyle("-fx-background-color: #f4f4f4; -fx-padding: 10; -fx-spacing: 20;");

        Label idReservationLabel = new Label(String.valueOf(reservation.getIdReservation()));
        Label emailUserLabel = new Label(user.getUser(reservation.getIdUser()).getEmail());
        List<Integer> roomIds = reservationService.getIdRoom(reservation.getIdReservation());
        String idsString = roomIds.stream()
                .map(String::valueOf) // Convertition id to string
                .collect(Collectors.joining(", "));
        Label labelChambres =new Label();
        labelChambres.setText(idsString);
        RoomDAOI room=new RoomDAOImpl();
        Label prixRoom=new Label();
        List<Double>prix=new ArrayList<>();
        for(int roomId : roomIds) {
            prix.add(room.getRoom(roomId).getPrix());
        }
        String prixroom= prix.stream().map(String::valueOf).collect(Collectors.joining(", "));
        prixRoom.setText(prixroom);
        Label nameHotel=new Label();
        HotelDAOI hotel = new HotelDAOImpl();
        String hot="";
        for(int roomId : roomIds) {

            hot=hotel.getHotel(room.getRoom(roomId).getIdHotel()).getNom();
        }
        nameHotel.setText(hot);
        Label roomtypeLabel=new Label();
        List<String> roomtype=new ArrayList<>();
        for(int roomId:roomIds){
            roomtype.add(room.getRoom(roomId).getTypeRoom().toString());

        }
        String roomType=roomtype.stream().collect(Collectors.joining(", "));
        roomtypeLabel.setText(roomType);
        Label dateDebutLabel = new Label(reservation.getDateDebut().toString());
        Label dateFinlabel = new Label(reservation.getDateFin().toString());
        idReservationLabel.setPrefWidth(130);
        emailUserLabel.setPrefWidth(130);
        labelChambres.setPrefWidth(130);
        roomtypeLabel.setPrefWidth(130);
        prixRoom.setPrefWidth(130);
        nameHotel.setPrefWidth(130);
        dateDebutLabel.setPrefWidth(130);
        dateFinlabel.setPrefWidth(130);
        reservationRow.getChildren().addAll(idReservationLabel, emailUserLabel,labelChambres,roomtypeLabel,prixRoom,nameHotel,dateDebutLabel,dateFinlabel);

        return reservationRow;

    }}


