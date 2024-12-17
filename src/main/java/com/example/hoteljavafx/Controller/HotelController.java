package com.example.hoteljavafx.Controller;

import com.example.hoteljavafx.DAO.HotelDAOImpl;
import com.example.hoteljavafx.Model.Hotel;
import com.example.hoteljavafx.DAO.HotelDAOI;
import com.example.hoteljavafx.Utils.CustemAlerts;
import com.example.hoteljavafx.Utils.ImageManager;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class HotelController {
    @FXML
    private VBox vboxHotels;
    @FXML
    private BorderPane contentPane;
    @FXML
    private Button addhotel;
    @FXML
    private TextField name;
    @FXML
    private TextField address;
    @FXML
    private TextField image;
    @FXML
    private TextArea description;
    @FXML
    private TextField nameE;
    @FXML
    private TextField addressE;
    @FXML
    private TextArea descriptionE;
    private static int id;

    public void initialize() throws SQLException, IOException {
        if(vboxHotels!=null){
            vboxHotels.getChildren().clear();
            HotelDAOI hotelService = new HotelDAOImpl();
            for (Hotel hotel : hotelService.getAllHotels()) {
                HBox hbox = createHotelRow(hotel);
                vboxHotels.getChildren().add(hbox);}
        }
    }

    private HBox createHotelRow(Hotel hotel) {
        HBox hbox = new HBox();
        Label lblId = new Label(String.valueOf(hotel.getIdHotel()));
        HBox.setMargin(lblId, new Insets(8, 0, 0, 20));
        lblId.setPrefWidth(90);
        Image image;
        if(hotel.getImage()!=null && getClass().getResource(hotel.getImage())!=null)
            image = new Image(getClass().getResource(hotel.getImage()).toExternalForm());
        else
            image = new Image(getClass().getResource("/Images/hotelimages/default-hotel.png").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(35);
        imageView.setFitWidth(50);
        imageView.setPreserveRatio(false);
        HBox.setMargin(imageView, new Insets(0, 68, 0, 0));
        Label lblNom = new Label(hotel.getNom());
        lblNom.setPrefWidth(125);
        HBox.setMargin(lblNom, new Insets(8, 0, 0, 0));
        Label lblAddress = new Label(hotel.getAddress());
        lblAddress.setPrefWidth(155);
        HBox.setMargin(lblAddress, new Insets(8, 0, 0, 0));
        Label lblNote = new Label(String.format("%.1f", hotel.getNoteRating()));
        lblNote.setPrefWidth(90);
        HBox.setMargin(lblNote, new Insets(8, 0, 0, 10));
        Label lblDateA = new Label(hotel.getDateAjout().toString());
        lblDateA.setPrefWidth(125);
        HBox.setMargin(lblDateA, new Insets(8, 0, 0, 0));
        Label lblDateU = new Label(hotel.getDateUpdate().toString());
        lblDateU.setPrefWidth(105);
        HBox.setMargin(lblDateU, new Insets(8, 0, 0, 0));
        FontAwesomeIconView deleteIcon = new FontAwesomeIconView();
        deleteIcon.setGlyphName("TRASH");
        deleteIcon.setFill(Color.RED);
        Button deleteButton = new Button();
        deleteButton.setGraphic(deleteIcon);
        deleteButton.setStyle("-fx-background-color: transparent;-fx-cursor: HAND;");
        deleteButton.setOnAction(e -> {
            try {
                deleteHotel(hotel.getIdHotel());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        HBox.setMargin(deleteButton, new Insets(10, 0, 0, 15));
        FontAwesomeIconView editIcon = new FontAwesomeIconView();
        editIcon.setGlyphName("EDIT");
        editIcon.setFill(Color.GREEN);
        Button editButton = new Button();
        editButton.setGraphic(editIcon);
        editButton.setStyle("-fx-background-color: transparent;-fx-cursor: HAND;");
        editButton.setOnAction(e -> {
            try {
                updateHotel(hotel.getIdHotel());
            } catch (IOException | SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        HBox actionBox = new HBox(10);
        actionBox.getChildren().addAll(editButton, deleteButton);
        actionBox.setPrefWidth(150);
        HBox.setMargin(editButton, new Insets(10, 0, 0, 0));
        Button actionButton = new Button("Chambres");
        actionButton.getStyleClass().add("buttons3");
        actionButton.setPrefWidth(80);
        HBox.setMargin(actionButton, new Insets(8, 0, 0, 40));
        actionButton.setOnAction(e -> {
            try {
                handleRoomClick(hotel.getIdHotel());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        lblId.getStyleClass().add("user-label");
        lblNom.getStyleClass().add("user-label");
        lblAddress.getStyleClass().add("user-label");
        lblNote.getStyleClass().add("user-label");
        lblDateA.getStyleClass().add("user-label");
        lblDateU.getStyleClass().add("user-label");
        hbox.getChildren().addAll(lblId, imageView, lblNom, lblAddress, lblNote, lblDateA, lblDateU,deleteButton,editButton,actionButton);
        hbox.setStyle("-fx-background-color: #f5f5f5; -fx-padding: 10;");
        return hbox;
    }

    private void updateHotel(int idHotel) throws IOException, SQLException {
        id = idHotel;
        HotelDAOI hotelService = new HotelDAOImpl();
        Hotel hotel = hotelService.getHotel(idHotel);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/EditHotel.fxml"));
        loader.setController(this);
        Parent hotelView = loader.load();
        nameE.setText(hotel.getNom());
        addressE.setText(hotel.getAddress());
        descriptionE.setText(hotel.getDescription());
        Scene hotelScene = new Scene(hotelView);
        Stage currentStage = (Stage) vboxHotels.getScene().getWindow();
        currentStage.setScene(hotelScene);
        currentStage.setTitle("Modifier l'Hôtel");
        currentStage.show();

    }
    private void deleteHotel(int idHotel) throws SQLException, IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Supprimer l'hôtel");
        alert.setContentText("Êtes-vous sûr de vouloir supprimer cet hôtel ?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            HotelDAOI hotelService = new HotelDAOImpl();
            hotelService.DeleteHotel(idHotel);
            CustemAlerts.showCustomAlert("Succès", "L'hôtel a été supprimé avec succès !", "success",getClass());
            initialize();
        }
    }
    @FXML
    private void editHotel(){
        HotelDAOI hotel = new HotelDAOImpl();
        try {
            String Name = nameE.getText();
            String Address = addressE.getText();
            String Description = descriptionE.getText();
            String path = image.getText();
            if(Name.isEmpty() || Address.isEmpty() || Description.isEmpty()){
                CustemAlerts.showCustomAlert("Error", "Veuillez remplir tous les champs !!", "Error",getClass());
                return;
            }
            if(path.isEmpty()){
                hotel.UpdateHotel(id,Name,Address,Description,null);
            }
            else{
                 String Image = ImageManager.copyImageToResources(path,"hotelimages");
                 hotel.UpdateHotel(id,Name,Address,Description,Image);}
            loadView("/Views/Hotels.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void handleRoomClick(int idHotel) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Rooms.fxml"));
        RoomController roomController = new RoomController();
        roomController.setHotelId(idHotel);
        loader.setController(roomController);
        Parent roomView = loader.load();
        Scene roomScene = new Scene(roomView);
        Stage currentStage = (Stage) vboxHotels.getScene().getWindow();
        currentStage.setScene(roomScene);
        currentStage.setTitle("Dashboard");
        currentStage.show();
    }
    @FXML
    private void AddHotel() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/AddHotel.fxml"));
        Parent hotelView = loader.load();
        Scene hotelScene = new Scene(hotelView);
        Stage currentStage = (Stage) vboxHotels.getScene().getWindow();
        currentStage.setScene(hotelScene);
        currentStage.setTitle("Dashboard");
        currentStage.show();
    }
    public void saveHotel() {
        HotelDAOI hotel = new HotelDAOImpl();
        try {
            String Name = name.getText();
            String Address = address.getText();
            String Description = description.getText();
            String path = image.getText();
            if(Name.isEmpty() || Address.isEmpty() || Description.isEmpty()){
                CustemAlerts.showCustomAlert("Error", "Veuillez remplir tous les champs !!", "Error",getClass());
                return;
            }
            if(path.isEmpty()){
                hotel.addNewHotel(Name,Address,Description,null);
            }
            else{
            String Image = ImageManager.copyImageToResources(path,"hotelimages");
            hotel.addNewHotel(Name,Address,Description,Image);}
            loadView("/Views/Hotels.fxml");
            }
           catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }}

    public void browseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            image.setText(selectedFile.getAbsolutePath());
        }
    }


    @FXML
    private void handleSettingsClick() throws IOException {
        loadView("/Views/parametres.fxml");
    }

    @FXML
    private void handleAccountClick() throws IOException {
        loadView("/Views/mon_compte.fxml");
    }
    @FXML
    private void handleDashboardClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Dashboard.fxml"));
        Parent dashboardView = loader.load();
        Scene dashboard = new Scene(dashboardView);
        Stage currentStage= (Stage) contentPane.getScene().getWindow();
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
    private void loadView(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        VBox view = loader.load();
        contentPane.setCenter(view);
    }




}




