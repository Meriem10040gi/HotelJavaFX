package com.example.hoteljavafx.Controller;
import com.example.hoteljavafx.DAO.UserDAOImpl;
import com.example.hoteljavafx.Model.User;
import com.example.hoteljavafx.DAO.UserDAOI;
import com.example.hoteljavafx.Utils.Role;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.controlsfx.glyphfont.FontAwesome;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;


public class UserController implements Initializable {

    @FXML
    private VBox userListContainer;
    @FXML
    private  TextField nom ;
    @FXML
    private  TextField prenom ;
    @FXML
    private  TextField email ;
    @FXML
    private  TextField phone ;
    @FXML
    private  TextField pwd ;
    @FXML
    private  TextField address ;
    @FXML
    private ComboBox<Role> roleComboBox;

    private UserDAOI user = new UserDAOImpl(); // Instance du service utilisateur


    @FXML
    private void loadAndDisplayUsers() throws SQLException, IOException {
        // Récupérer la liste des utilisateurs depuis le service
        List<User> users = user.getAllUsers();

        for (User user : users) {
            // Créer dynamiquement une ligne pour chaque utilisateur
            HBox userRow = createUserRow(user);
            // Ajouter cette ligne à la VBox
            userListContainer.getChildren().add(userRow);
        }
    }


    @FXML
    public void initializeUser() {

        // Charger et afficher les utilisateurs
        try {
            loadAndDisplayUsers();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public  void pageadd() throws IOException {
        loadView("addUser.fxml");
    }
    @FXML
    public void setNom(String nomValue) {
        nom.setPromptText(nomValue);
    }

    @FXML
    public String addUser() throws SQLException, IOException {
        UserDAOI userService = new UserDAOImpl();
        String rep= userService.addNewUser(nom.getText(),prenom.getText(),address.getText(),email.getText(),phone.getText(),roleComboBox.getValue(), pwd.getText(),pwd.getText());
        loadView("utilisateurs.fxml");
        return rep;}

    @FXML
    public void editUser() throws SQLException, IOException {
        UserDAOI userService = new UserDAOImpl();
        userService.UpdateUser(nom.getText(),prenom.getText(),address.getText(),email.getText(),phone.getText());
        loadView("utilisateurs.fxml");
    }



    @FXML
    private HBox createUserRow(User user) {
        HBox userRow = new HBox();
        userRow.setStyle("-fx-background-color: #f4f4f4; -fx-padding: 10; -fx-spacing: 10; -fx-alignment: CENTER_LEFT;");

        Label idLabel = new Label(String.valueOf(user.getIdUser()));
        Label nameLabel = new Label(user.getNom());
        Label prenomLabel = new Label(user.getPrenom());
        Label emailLabel = new Label(user.getEmail());
        Label phoneLabel = new Label(user.getPhone());
        Label addressLabel = new Label(user.getAddress());
        Label roleLabel = new Label(user.getRole());

        FontAwesomeIconView editIcon = new FontAwesomeIconView();
        editIcon.setGlyphName("EDIT");
        editIcon.setFill(Color.GREEN);

        Button editButton = new Button();
        editButton.setGraphic(editIcon);
        editButton.setStyle("-fx-background-color: transparent;-fx-cursor: HAND;");
        editButton.setOnAction(event ->
        {
            try {

                // Chargez la nouvelle vue
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/editUser.fxml"));
                loader.setController(this);
                Parent addUserView = loader.load();
                UserDAOI userService=new UserDAOImpl();
                User user1=userService.getUser(user.getIdUser());
                nom.setText(user1.getNom());
                prenom.setText(user1.getPrenom());
                address.setText(user1.getAddress());
                email.setText(user1.getEmail());
                phone.setText(user1.getPhone());
                // Remplacez la vue actuelle ou affichez une nouvelle scène
                Scene scene = new Scene(addUserView);
                Stage stage = (Stage) editButton.getScene().getWindow(); // Obtenez la scène actuelle
                stage.setScene(scene);


                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Erreur lors du chargement de la page d'ajout d'utilisateur.");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        FontAwesomeIconView deleteIcon = new FontAwesomeIconView();
        deleteIcon.setGlyphName("TRASH");
        deleteIcon.setFill(Color.RED);

        Button deleteButton = new Button();
        deleteButton.setGraphic(deleteIcon);
        deleteButton.setStyle("-fx-background-color: transparent;-fx-cursor: HAND;");
        deleteButton.setOnAction(event -> {
            UserDAOI utilisateur = new UserDAOImpl();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText("Êtes-vous sûr de vouloir supprimer cet élément ?");
            alert.setContentText("Cette action est irréversible.");

            // Afficher l'alerte et attendre la réponse de l'utilisateur
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    utilisateur.DeleteUser(user.getIdUser());
                    userListContainer.getChildren().remove(userRow);
                } catch (IOException | SQLException e) {
                    throw new RuntimeException(e);
                }}

        });
        HBox actionBox = new HBox(10);
        actionBox.getChildren().addAll(editButton, deleteButton);

        idLabel.setPrefWidth(90);
        nameLabel.setPrefWidth(150);
        prenomLabel.setPrefWidth(150);
        emailLabel.setPrefWidth(250);
        phoneLabel.setPrefWidth(150);
        addressLabel.setPrefWidth(200);
        roleLabel.setPrefWidth(150);
        actionBox.setPrefWidth(150);
        //ajout css au label
        idLabel.getStyleClass().add("user-label");
        nameLabel.getStyleClass().add("user-label");
        prenomLabel.getStyleClass().add("user-label");
        emailLabel.getStyleClass().add("user-label");
        phoneLabel.getStyleClass().add("user-label");
        addressLabel.getStyleClass().add("user-label");
        roleLabel.getStyleClass().add("user-label");
        actionBox.getStyleClass().add("user-label");
        // Ajouter tous les labels à la HBox
        userRow.getChildren().addAll(idLabel, nameLabel,prenomLabel, emailLabel, phoneLabel, addressLabel, roleLabel,actionBox);

        return userRow;

    }
    @FXML
    public void pageAddUser(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/addUser.fxml")));
            Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene=new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (userListContainer != null) {
            try {
                loadAndDisplayUsers();
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        if(roleComboBox!=null){
            roleComboBox.setItems(FXCollections.observableArrayList(Role.values()));
        }


    }

    @FXML
    private BorderPane contentPane; // Reference to the BorderPane in the Dashboard.fxml
    @FXML
    private VBox vb;

    @FXML
    private void handleDashboardClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Dashboard.fxml"));
        Parent dashboardView = loader.load();
        Scene dashboard = new Scene(dashboardView);
        Stage currentStage= (Stage) contentPane.getScene().getWindow();
        currentStage.setScene(dashboard);
        currentStage.setTitle("Dashboard");
        currentStage.show();
    }

    @FXML
    private void handleReservationsClick() throws IOException {
        loadView("reservations.fxml");
    }

    @FXML
    private void handleUsersClick() throws IOException {
        loadView("utilisateurs.fxml");
    }

    @FXML
    private void handleStatisticsClick() throws IOException {
        loadView("statistiques.fxml");
    }

    @FXML
    private void handleSettingsClick() throws IOException {
        loadView("parametres.fxml");
    }

    @FXML
    private void handleAccountClick() throws IOException {
        loadView("mon_compte.fxml");
    }


    @FXML
    private void loadView(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/" + fxmlFile));
        VBox view = loader.load();
        contentPane.setCenter(view); // Set the loaded view in the center of the BorderPane
    }}





