package com.example.hoteljavafx.Controller;

import com.example.hoteljavafx.DAO.RoomDAOI;
import com.example.hoteljavafx.DAO.RoomDAOImpl;
import com.example.hoteljavafx.Model.Room;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Data;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Data
public class RoomController {

    @FXML
    private VBox vboxRooms;
    private int hotelId;
    @FXML
    private BorderPane contentPane;
    @FXML
    private VBox vb;

    private final RoomDAOI roomService = new RoomDAOImpl();


    public void initialize() throws SQLException, IOException {
        loadRoomsOfHotel();
    }

    public void loadRoomsOfHotel() {
        System.out.println("l'hotel : "+hotelId);
        vboxRooms.getChildren().clear();
        try {
            List<Room> rooms = roomService.getAllRooms(hotelId);
            for (Room room : rooms) {
                HBox roomRow = createRoomRow(room);
                vboxRooms.getChildren().add(roomRow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement des chambres pour l'hôtel ID : " + hotelId);
        }
    }

    private HBox createRoomRow(Room room) {
        HBox hbox = new HBox();
        Label lblId = new Label(String.valueOf(room.getIdRoom()));
        HBox.setMargin(lblId, new Insets(0, 0, 0, 20));
        lblId.setPrefWidth(90);
        Image image = new Image(getClass().getResource(room.getImage()).toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(35);
        imageView.setFitWidth(50);
        imageView.setPreserveRatio(false);
        HBox.setMargin(imageView, new Insets(0, 75, 0, 0));
        Label lblNom = new Label(String.valueOf(room.getNumero()));
        lblNom.setPrefWidth(125);
        Label lblAddress = new Label(room.getTypeRoom().toString());
        lblAddress.setPrefWidth(125);
        Label lblNote = new Label(String.format("%.1f", room.getPrix()));
        lblNote.setPrefWidth(125);
        Label lblDateA = new Label(room.getDateAjout().toString());
        lblDateA.setPrefWidth(125);
        Label lblDateU = new Label(room.getDateUpdate().toString());
        lblDateU.setPrefWidth(170);
        Button actionButton = new Button("Reservations");
        actionButton.getStyleClass().add("buttons2");
        actionButton.setPrefWidth(100);
        actionButton.setOnAction(e -> {handleReservationClick(room.getIdRoom());});
        hbox.getChildren().addAll(lblId, imageView, lblNom, lblAddress, lblNote, lblDateA, lblDateU,actionButton);
        hbox.setStyle("-fx-background-color: #f5f5f5; -fx-padding: 10;");
        return hbox;
    }

    private void handleReservationClick(int idRoom) {
    }


    @FXML
    private void handleDashboardClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Dashboard.fxml"));
        Parent dashboardView = loader.load();
        Scene dashboard = new Scene(dashboardView);
        Stage currentStage = (Stage) vboxRooms.getScene().getWindow();
        currentStage.setScene(dashboard);
        currentStage.setTitle("Dashboard");
        currentStage.show();
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

}


