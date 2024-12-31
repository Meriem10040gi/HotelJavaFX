package com.example.hoteljavafx.Controller;

import com.example.hoteljavafx.DAO.*;
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
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;


public class viewMoreController implements Initializable {
    @FXML
    private BorderPane content;

    @FXML
    private Pagination pagination;
    GestionDB gestionDB = new GestionDB();

    @FXML
    private VBox hotel1;
    @FXML
    ComboBox<Cities> cities;

    @FXML
    private VBox hotel2;

    @FXML
    private VBox hotel3;
    @FXML
    private VBox hotel11;

    @FXML
    private VBox hotel21;

    @FXML
    private VBox hotel31;

    @FXML
    private Label labelHotel1;

    @FXML
    private Label nameHotelClickedSide;
    @FXML
    private Label addressHotelClickedSide;
    @FXML
    private Label ratingHotelClickedSide;
    @FXML
    private ImageView imageHotelClickedSide;
    @FXML
    private TextArea descriptionHotelClickedSide;
    @FXML
    DatePicker checkIn, checkOut;

    @FXML
    public void EV(MouseEvent e){
        List<VBox> vboxes=List.of(hotel1,hotel2,hotel3,hotel11,hotel21,hotel31);
        List<Label> labels=List.of(nameHotelClickedSide,addressHotelClickedSide,ratingHotelClickedSide);
        FrontMETHODS fmeth=new FrontMETHODS();
        fmeth.hotelClicked(e,vboxes,null,labels,imageHotelClickedSide,descriptionHotelClickedSide);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (cities != null) {
            cities.getItems().addAll(Cities.values());}
        PaginationGest hotelGest=new PaginationGest();
        HotelDAOI hotelDao = new HotelDAOImpl();
        double nbHotels = 0;
        try {
            nbHotels = hotelDao.getNbrHotels() / 6.0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        int nbrPages = hotelGest.traiterNombre(nbHotels);
        List <VBox> vboxes=List.of(hotel1,hotel2,hotel3,hotel11,hotel21,hotel31);
        try {
            OnClickPagination();
            setPagination();
            hotelGest.loadViewMorehotelPag(1,nbrPages,vboxes);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException | ClassNotFoundException e) {
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

    public void setPagination() throws SQLException, IOException, ClassNotFoundException {
        PaginationGest hotelGest=new PaginationGest();
        hotelGest.setPaginationHotel(pagination);
    }

    public void OnClickPagination() {
        pagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> {
            int pageIndex = newValue.intValue() + 1; // Page actuelle
            int pageCount = pagination.getPageCount(); // Nombre total de pages
            System.out.println("L'index est : " + pageIndex);
            System.out.println("Nombre total de pages : " + pageCount);
            List <VBox> vboxes=List.of(hotel1,hotel2,hotel3,hotel11,hotel21,hotel31);

            try {
                PaginationGest hotelGest=new PaginationGest();
                hotelGest.loadViewMorehotelPag(pageIndex, pageCount,vboxes);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }
    public void viewHotel() throws IOException {
        System.out.println("viewhotel click");
        if(HomeController.getHotelselection()==null || HomeController.getHotelselection().equals(""))
            HomeController.setHotelselection(nameHotelClickedSide.getText());
        loadView("ViewHotel.fxml");

    }
    private void loadView(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/" + fxmlFile));
        BorderPane view = loader.load();
        content.setCenter(view); // Set the loaded view in the center of the BorderPane
    }

    public void searchHotel(MouseEvent event) throws IOException {
            if (checkIn.getValue() != null && checkIn.getValue().isBefore(checkOut.getValue()) && cities.getValue() != null) {
                HomeController.setCheckInDate(checkIn.getValue()) ;
                HomeController.setCheckOutDate(checkOut.getValue());
                HomeController.setCity(cities.getValue().toString());
                loadView("searchHotel.fxml");
            } else {
                CustemAlerts.showCustomAlert("Error", "Veuillez remplir tous les champs Avec des valeurs valides  !!", "Error", getClass());
                return;
            }

    }


}