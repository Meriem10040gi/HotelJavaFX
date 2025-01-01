package com.example.hoteljavafx.Controller;

import com.example.hoteljavafx.DAO.HotelDAOI;
import com.example.hoteljavafx.DAO.HotelDAOImpl;
import com.example.hoteljavafx.DAO.UserDAOI;
import com.example.hoteljavafx.DAO.UserDAOImpl;
import com.example.hoteljavafx.Utils.CustemAlerts;
import com.example.hoteljavafx.Utils.Session;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import com.example.hoteljavafx.Utils.Cities;
import com.example.hoteljavafx.Utils.FrontMETHODS;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.*;

public class HomeController implements Initializable {
    @FXML
    private HBox hb;
    @FXML
    private HBox hboxHotel1;
    @FXML
    private HBox hboxHotel2;
    @FXML
    private HBox hboxHotel3;
    @FXML
    private HBox hboxHotel4;
    @FXML
    private HBox hboxHotel11;
    @FXML
    private HBox hboxHotel21;
    @FXML
    private HBox hboxHotel31;
    @FXML
    private HBox hboxHotel41;
    @FXML
    private Label addressHotelClickedSide;
    @FXML
    private TextArea descriptionHotelClickedSide;
    @FXML
    private ImageView imageHotelClickedSide;
    @FXML
    private Label nameHotelClickedSide;
    @FXML
    private Label ratingHotelClickedSide;
    @FXML
    private VBox hotel1;
    @FXML
    private VBox hotel2;
    @FXML
    private VBox hotel3;
    @FXML
    private Label labelHotel1;
    @FXML
    private Button recentlyAdded;
    @FXML
    private Button topRated1;
    @FXML
    private BorderPane content;
    @FXML
    private VBox VboxRecentlyAdded;
    @FXML
    private VBox VboxTopRated;
    @FXML
    private Button loginbtn;
    @FXML
    ComboBox<Cities> cities;
    @FXML
    DatePicker checkIn, checkOut;
    @FXML
    private Pane heart;
    FrontMETHODS mth = new FrontMETHODS();
    public static BorderPane ct ;

    private static List<String> favoriteHotels = new ArrayList<>();
    static public List<String> getFavoriteHotels(){
        return favoriteHotels;
    }

    private static LocalDate checkInDate, checkOutDate;
    public static void setCheckInDate(LocalDate d) {
        checkInDate = d;
    }
    public static void setCheckOutDate(LocalDate d) {
        checkOutDate = d;
    }
    public static LocalDate getCheckInDate() {
        return checkInDate;
    }
    public static LocalDate getCheckOutDate() {
        return checkOutDate;
    }
    private static String city;
    public static void setCity(String c){
        city = c;
    }
    public static String getCity(){
        return city;
    }

    private static String hotelselection;

    public static String getHotelselection(){
        return hotelselection;
    }
    public static void setHotelselection(String s){
        hotelselection=s;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(loginbtn!=null){
            if(Session.getInstance().isConnected()){
                loginbtn.setText("Sign out");
            }
            else{
                loginbtn.setText("Sign in");
            }}
        try {
            mth.setStyleHeart(nameHotelClickedSide.getText(),heart);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(content!=null) ct = content;
        if (cities != null) {
            cities.getItems().addAll(Cities.values());
            try {
                VboxRecentlyAdded.setVisible(true);
                VboxTopRated.setVisible(false);
                loadHotels();
                setFooterHotels();
            } catch (SQLException | ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
            topRated1.setOnMouseClicked(event -> {
                VboxRecentlyAdded.setVisible(false);
                VboxTopRated.setVisible(true);
            });
            recentlyAdded.setOnMouseClicked(event -> {
                VboxTopRated.setVisible(false);
                VboxRecentlyAdded.setVisible(true);

            });
        }
    }
    @FXML
    public void EV(MouseEvent e) throws SQLException, IOException {
        List<VBox> vboxes=List.of(hotel1,hotel2,hotel3);
        List<HBox> hboxes=List.of(hboxHotel1,hboxHotel2,hboxHotel3,hboxHotel4,hboxHotel11,hboxHotel21,hboxHotel31,hboxHotel41);
        List<Label> labels=List.of(nameHotelClickedSide,addressHotelClickedSide,ratingHotelClickedSide);
        FrontMETHODS fmeth=new FrontMETHODS();
        fmeth.hotelClicked(e,vboxes,hboxes,labels,imageHotelClickedSide,descriptionHotelClickedSide);
        mth.setStyleHeart(nameHotelClickedSide.getText(),heart);
    }
    public void loadHotels() throws SQLException, ClassNotFoundException, IOException {
        List<VBox> vboxes=List.of(hotel1,hotel2,hotel3);
        FrontMETHODS meth=new FrontMETHODS();
        meth.loadHotels(vboxes);
        List<Label> labels=List.of(nameHotelClickedSide,addressHotelClickedSide,ratingHotelClickedSide);
        meth.setSideHotel(labelHotel1.getText(),labels,imageHotelClickedSide,descriptionHotelClickedSide);
        meth.setStyleHeart(nameHotelClickedSide.getText(),heart);
    }
    public void setFooterHotels() throws SQLException, ClassNotFoundException {
        List<HBox> hboxesRating=List.of(hboxHotel1,hboxHotel2,hboxHotel3,hboxHotel4);
        List<HBox> hboxesDateAjout=List.of(hboxHotel11,hboxHotel21,hboxHotel31,hboxHotel41);
        FrontMETHODS fmeth=new FrontMETHODS();
        fmeth.setFooterHotels(hboxesRating,hboxesDateAjout);
    }

    private void loadView(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/" + fxmlFile));
        BorderPane view = loader.load();
        content.setCenter(view);
    }

    public void viewMoreOnClick() throws IOException {
        if(Session.getInstance().isConnected()){
            loadView("viewMore.fxml");}
        else{
            Login();
        }
    }

    public void viewHotel(MouseEvent event) throws IOException {
        if(Session.getInstance().isConnected()){
            hotelselection=nameHotelClickedSide.getText();
            loadView("ViewHotel.fxml");}
        else{
            Login();
        }
    }
    public void searchHotel(MouseEvent event) throws IOException {
        if(Session.getInstance().isConnected()) {
            if (checkIn.getValue() != null && checkIn.getValue().isBefore(checkOut.getValue()) && cities.getValue() != null) {
                checkInDate = checkIn.getValue();
                checkOutDate = checkOut.getValue();
                city = cities.getValue().toString();
                loadView("searchHotel.fxml");
            } else {
                CustemAlerts.showCustomAlert("Error", "Veuillez remplir tous les champs Avec des valeurs valides  !!", "Error", getClass());
                return;
            }
        }
        else{
            Login();
        }

    }

    public void ViewFavorite(ActionEvent actionEvent) throws IOException {
        if(Session.getInstance().isConnected()){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/favorites.fxml"));
            BorderPane view = loader.load();
            content.setCenter(view);}
        else{
            Login();
        }
    }

    public void ViewBookings(ActionEvent actionEvent) throws IOException {
        if(Session.getInstance().isConnected()){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/bookings.fxml"));
            BorderPane view = loader.load();
            content.setCenter(view);
        }
        else{
            Login();
        }
    }

    public void ViewHome(ActionEvent actionEvent) {
        content.setCenter(hb);
    }

    public void Login( ) throws IOException {
        if(!Session.getInstance().isConnected()){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/login.fxml"));
            StackPane view = loader.load();
            content.setCenter(view);
        }
        else{
            Session.getInstance().endSession();
            ViewHome(null);
            initialize(null,null);
        }
    }
    public List<Integer> add_or_remove_FromFavoriteHotels(MouseEvent event) throws IOException, SQLException {
        if(Session.getInstance().isConnected()){
            UserDAOI userDao = new UserDAOImpl();
            HotelDAOI hotelDao = new HotelDAOImpl();
            System.out.println("favorite hotels of "+ Session.getInstance().getUserId());

            if (userDao.selectFavoriteHotels(Session.getInstance().getUserId()).contains(hotelDao.idHotel(nameHotelClickedSide.getText()))) {
                userDao.deleteFavoritehotel(Session.getInstance().getUserId(), hotelDao.idHotel(nameHotelClickedSide.getText()));

            }
            else {
                userDao.insertFavoritehotel(Session.getInstance().getUserId(), hotelDao.idHotel(nameHotelClickedSide.getText()));
            }
            mth.setStyleHeart(nameHotelClickedSide.getText(),heart);

            return userDao.selectFavoriteHotels(Session.getInstance().getUserId());
        }
        else{
            Login();
        }
        return null;
    }
}

