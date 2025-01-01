package com.example.hoteljavafx.Controller;

import com.example.hoteljavafx.DAO.HotelDAOI;
import com.example.hoteljavafx.DAO.HotelDAOImpl;
import com.example.hoteljavafx.DAO.UserDAOI;
import com.example.hoteljavafx.DAO.UserDAOImpl;
import com.example.hoteljavafx.Utils.FrontMETHODS;
import com.example.hoteljavafx.Utils.PaginationGest;
import com.example.hoteljavafx.Utils.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class favoriteController implements Initializable {

    @FXML
    private VBox VBoxSide;
    @FXML
    private Label addHotel1;

    @FXML
    private Label addHotel11;

    @FXML
    private Label addHotel111;

    @FXML
    private Label addHotel2;

    @FXML
    private Label addHotel21;

    @FXML
    private Label addHotel211;

    @FXML
    private Label addHotel3;

    @FXML
    private Label addHotel31;

    @FXML
    private Label addHotel311;

    @FXML
    private Label addressHotelClickedSide;

    @FXML
    private BorderPane content;

    @FXML
    private TextArea descriptionHotelClickedSide;

    @FXML
    private Button discoverHotels;

    @FXML
    private Pane heart;

    @FXML
    private VBox hotel1;

    @FXML
    private VBox hotel11;

    @FXML
    private VBox hotel2;

    @FXML
    private VBox hotel21;


    @FXML
    private VBox hotel3;

    @FXML
    private VBox hotel31;


    @FXML
    private ImageView imageHotel1;

    @FXML
    private ImageView imageHotel11;

    @FXML
    private ImageView imageHotel111;

    @FXML
    private ImageView imageHotel2;

    @FXML
    private ImageView imageHotel21;

    @FXML
    private ImageView imageHotel211;

    @FXML
    private ImageView imageHotel3;

    @FXML
    private ImageView imageHotel31;

    @FXML
    private ImageView imageHotel311;

    @FXML
    private ImageView imageHotelClickedSide;

    @FXML
    private Label labelHotel1;

    @FXML
    private Label labelHotel11;

    @FXML
    private Label labelHotel111;

    @FXML
    private Label labelHotel2;

    @FXML
    private Label labelHotel21;

    @FXML
    private Label labelHotel211;

    @FXML
    private Label labelHotel3;

    @FXML
    private Label labelHotel31;

    @FXML
    private Label labelHotel311;

    @FXML
    private Label nameHotelClickedSide;

    @FXML
    private Label noFavoriteHotels;

    @FXML
    private Pagination pagination;

    @FXML
    private Label ratingHotelClickedSide;

    @FXML
    private Label username;

    private static int pageIndx=0;


    FrontMETHODS mth = new FrontMETHODS();
    UserDAOI userDao = new UserDAOImpl();

    @FXML
    public void EV(MouseEvent e) throws SQLException, IOException {
        List<VBox> vboxes = List.of(hotel1, hotel2, hotel3, hotel11, hotel21, hotel31);
        List<Label> labels = List.of(nameHotelClickedSide, addressHotelClickedSide, ratingHotelClickedSide);
        FrontMETHODS fmeth = new FrontMETHODS();
        fmeth.hotelClicked(e, vboxes, null, labels, imageHotelClickedSide, descriptionHotelClickedSide);
        fmeth.setStyleHeart(nameHotelClickedSide.getText(),heart);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            UserDAOI userdao = new UserDAOImpl();
            username.setText(userdao.getUser(Session.getInstance().getUserId()).getNom() + " " + userdao.getUser(Session.getInstance().getUserId()).getPrenom());
            PaginationGest paginationGest = new PaginationGest();
            List<VBox> vBoxes = new ArrayList<>();
            vBoxes = List.of(hotel1, hotel2, hotel3, hotel11, hotel21, hotel31);
            List<Integer> idHotelsfav = userdao.selectFavoriteHotels(Session.getInstance().getUserId());
            paginationGest.setPaginationFavorite(pagination, idHotelsfav);
            if (!idHotelsfav.isEmpty()) {
                discoverHotels.setVisible(false);
                noFavoriteHotels.setVisible(false);
                double nbrPages = idHotelsfav.size() / 6.0;
                int nmbrpages = paginationGest.traiterNombre(nbrPages);
                paginationGest.loadFavoriteHotels(1, nmbrpages, vBoxes, idHotelsfav);
                FrontMETHODS fmeth = new FrontMETHODS();
                List<Label> labels = List.of(nameHotelClickedSide, addressHotelClickedSide, ratingHotelClickedSide);
                String hotel = ((Label) ((VBox) ((HBox) hotel1.getChildren().get(1)).getChildren().getFirst()).getChildren().getFirst()).getText();
                fmeth.setSideHotel(hotel, labels, imageHotelClickedSide, descriptionHotelClickedSide);
                fmeth.setStyleHeart(nameHotelClickedSide.getText(), heart);
                OnClickPagination();
            }
            if (idHotelsfav.isEmpty()) {
                VBoxSide.setVisible(false);
                pagination.setVisible(false);
                discoverHotels.setVisible(true);
                noFavoriteHotels.setVisible(true);
                hotel1.setVisible(false);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    public void viewHotel() throws IOException {
        System.out.println("viewhotel click");
        HomeController.setHotelselection(nameHotelClickedSide.getText());
        loadView("ViewHotel.fxml");

    }
        public void setPagination() throws SQLException, IOException {
        PaginationGest hotelGest = new PaginationGest();
        hotelGest.setPaginationFavorite(pagination, userDao.selectFavoriteHotels(Session.getInstance().getUserId()));
    }

    public void OnClickPagination() {
        pagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> {
            pageIndx = newValue.intValue()+1;
            int pageIndex = newValue.intValue() + 1; // Page actuelle
            int pageCount = pagination.getPageCount(); // Nombre total de pages
            System.out.println("L'index est : " + pageIndex);
            System.out.println("Nombre total de pages : " + pageCount);
            List<VBox> vboxes = List.of(hotel1, hotel2, hotel3, hotel11, hotel21, hotel31);

            try {
                PaginationGest hotelGest = new PaginationGest();
                hotelGest.loadFavoriteHotels(pageIndex, pageCount, vboxes, userDao.selectFavoriteHotels(Session.getInstance().getUserId()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }
    @FXML
    public void ClickDiscMorHot() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/HomePage.fxml"));
        Parent home = (Parent) loader.load();
        Scene scene = new Scene(home);
        Stage currentStage=(Stage) content.getScene().getWindow();
        currentStage.setScene(scene);
        currentStage.setTitle("Hotel Management");
        currentStage.show();

    }
    private void loadView(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/" + fxmlFile));
        HomeController controller = loader.getController();
        BorderPane view = loader.load();
        content.setCenter(view);
    }
    @FXML
    public List<Integer> add_or_remove_FromFavoriteHotels(MouseEvent event) throws IOException, SQLException {
        UserDAOI userDao = new UserDAOImpl();
        HotelDAOI hotelDao = new HotelDAOImpl();
        if (userDao.selectFavoriteHotels(Session.getInstance().getUserId()).contains(hotelDao.idHotel(nameHotelClickedSide.getText()))) {
            userDao.deleteFavoritehotel(Session.getInstance().getUserId(),hotelDao.idHotel(nameHotelClickedSide.getText()));

        }
        else {
            userDao.insertFavoritehotel(Session.getInstance().getUserId(), hotelDao.idHotel(nameHotelClickedSide.getText()));
        }
        initialize(null,null);
        return userDao.selectFavoriteHotels(Session.getInstance().getUserId());

    }




}
