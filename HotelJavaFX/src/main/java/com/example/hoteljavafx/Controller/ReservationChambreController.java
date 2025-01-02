package com.example.hoteljavafx.Controller;
import com.example.hoteljavafx.DAO.ReservationDAOI;
import com.example.hoteljavafx.DAO.ReservationDAOImpl;
import com.example.hoteljavafx.DAO.UserDAOI;
import com.example.hoteljavafx.DAO.UserDAOImpl;
import com.example.hoteljavafx.Model.Reservation;
import com.example.hoteljavafx.Model.User;
import com.example.hoteljavafx.Service.ReservationChambreService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Data;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Data
public class ReservationChambreController {
	@FXML
    private VBox vboxReservations;
    @FXML
    private BorderPane contentPane;

	private int roomId;

    private final ReservationChambreService reservationService = new ReservationChambreService();
    private final UserDAOI userDAOI = new UserDAOImpl();

    public void initialize() throws SQLException, IOException {
        loadReservationsOfReservation();
    }

    public void loadReservationsOfReservation() {
        vboxReservations.getChildren().clear();
        try {
            List<Reservation> reservations = reservationService.getReservations(roomId);
            for (Reservation reservation : reservations) {
                HBox reservationRow = createReservationRow(reservation);
                vboxReservations.getChildren().add(reservationRow);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement des reservation pour la chmbre ID : " + roomId);
        }
    }

    private HBox createReservationRow(Reservation reservation) throws SQLException {
        User user = userDAOI.getUser(reservation.getIdUser());
        HBox hbox = new HBox();

        Label lblId = new Label(String.valueOf(reservation.getIdReservation()));
        HBox.setMargin(lblId, new Insets(0, 0, 0, 20));
        lblId.setPrefWidth(120);

        Label lblEmail = new Label(String.valueOf(user.getEmail()));
        lblEmail.setPrefWidth(300);


        Label lblDateD = new Label(reservation.getDateDebut().toString());
        lblDateD.setPrefWidth(300);

        Label lblDateF = new Label(reservation.getDateFin().toString());
        lblDateF.setPrefWidth(300);

        hbox.getChildren().addAll(lblId, lblEmail, lblDateD, lblDateF);
        hbox.setStyle("-fx-background-color: #f5f5f5; -fx-padding: 10;");
        return hbox;
    }

    private void handleReservationClick(int idReservation) {
    }


    @FXML
    private void handleDashboardClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Dashboard.fxml"));
        Parent dashboardView = loader.load();
        Scene dashboard = new Scene(dashboardView);
        Stage currentStage = (Stage) vboxReservations.getScene().getWindow();
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
    private void handleAccountClick() throws IOException {
        loadView("/Views/mon_compte.fxml");
    }
    private void loadView(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        VBox view = loader.load();
        contentPane.setCenter(view);
    }
}
