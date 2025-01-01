package com.example.hoteljavafx.Controller;

import com.example.hoteljavafx.DAO.UserDAOI;
import com.example.hoteljavafx.DAO.UserDAOImpl;
import com.example.hoteljavafx.Utils.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class searchHotelController implements Initializable {

    @FXML
    private Label addressHotelClickedSide;

    @FXML
    private DatePicker checkIn;

    @FXML
    private DatePicker checkOut;

    @FXML
    private BorderPane content;

    @FXML
    private TextArea descriptionHotelClickedSide;

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
    private ImageView imageHotelClickedSide;

    @FXML
    private Label labelHotel1;

    @FXML
    private Label nameHotelClickedSide;

    @FXML
    private Pagination pagination;

    @FXML
    private Label ratingHotelClickedSide;

    @FXML
    private ComboBox<Cities> cities;
    @FXML
    private Label username;

    private static String hotelselection;

    public static String getHotelselection(){
        return hotelselection;
    }
    public static void setHotelselection(String s){
        hotelselection=s;
    }

    @Override
    public void initialize (URL location, ResourceBundle resources) {
        UserDAOI userdao = new UserDAOImpl();
        try {
            username.setText(userdao.getUser(Session.getInstance().getUserId()).getNom() + " " + userdao.getUser(Session.getInstance().getUserId()).getPrenom());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        cities.getItems().addAll(Cities.values());
        PaginationGest paginationGest = new PaginationGest();
        searchMethod search = new searchMethod();
        List<VBox> vBoxes= List.of(hotel1,hotel2,hotel3,hotel11,hotel21,hotel31);
        try {
            OnClickPagination();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Map<Integer, List<Integer>> toto = null;
        double nbrPages=0;
        try {
            toto = search.searchMethodFunction(HomeController.getCheckInDate(), HomeController.getCheckOutDate(), HomeController.getCity());
            nbrPages=toto.size()/6.0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            paginationGest.setPaginationSearch(pagination, toto);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int nmbrPages=paginationGest.traiterNombre(nbrPages);
        try {
            paginationGest.loadSearchHotelPag(1,nmbrPages,vBoxes,toto);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        FrontMETHODS meth=new FrontMETHODS();
        List<Label> labels=List.of(nameHotelClickedSide,addressHotelClickedSide,ratingHotelClickedSide);
        try {
            meth.setSideHotel(labelHotel1.getText(),labels,imageHotelClickedSide,descriptionHotelClickedSide);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    public void OnClickPagination() throws Exception {
        pagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> {
            int pageIndex = newValue.intValue() + 1; // Page actuelle
            int pageCount = pagination.getPageCount();
            List <VBox> vboxes=List.of(hotel1,hotel2,hotel3,hotel11,hotel21,hotel31);
            searchMethod search=new searchMethod();
            Map<Integer,List<Integer>> map=new HashMap<>();
            try {
                map = search.searchMethodFunction(HomeController.getCheckInDate(), HomeController.getCheckOutDate(),HomeController.getCity());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            try {

                PaginationGest pagGest=new PaginationGest();
                pagGest.loadSearchHotelPag(pageIndex, pageCount,vboxes,map);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FXML
    void EV(MouseEvent event) {
        List<VBox> vboxes=List.of(hotel1,hotel2,hotel3,hotel11,hotel21,hotel31);
        List<Label> labels=List.of(nameHotelClickedSide,addressHotelClickedSide,ratingHotelClickedSide);
        FrontMETHODS fmeth=new FrontMETHODS();
        fmeth.hotelClicked(event,vboxes,null,labels,imageHotelClickedSide,descriptionHotelClickedSide);
    }

    public void viewHotel(MouseEvent event) throws IOException {
        hotelselection=nameHotelClickedSide.getText();
        HomeController.setHotelselection(hotelselection);
        loadView("ViewHotel.fxml");
    }
    private void loadView(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/" + fxmlFile));
        BorderPane view = loader.load();
        content.setCenter(view); // Set the loaded view in the center of the BorderPane
    }
    public void searchHotel(MouseEvent event) throws IOException {
        if(checkIn.getValue().isBefore(checkOut.getValue()) && cities.getValue()!=null){
            HomeController.setCheckInDate(checkIn.getValue());
            HomeController.setCheckOutDate(checkOut.getValue());
            HomeController.setCity(cities.getValue().toString());
            loadView("searchHotel.fxml");
        }


    }

}