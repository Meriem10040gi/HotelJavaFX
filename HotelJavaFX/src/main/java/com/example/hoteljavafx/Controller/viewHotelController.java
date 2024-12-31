package com.example.hoteljavafx.Controller;

import com.example.hoteljavafx.DAO.*;
import com.example.hoteljavafx.Utils.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class viewHotelController implements Initializable {
    private List<Integer> ChambersReserved=new ArrayList<>();
    @FXML
    BorderPane content;
    @FXML
    DatePicker checkIn,checkOut;
    @FXML
    private Label HotelName;
    @FXML
    private Pagination pagination;
    @FXML
    private VBox Chambre1;

    @FXML
    private VBox Chambre2;

    @FXML
    private VBox Chambre3;
    @FXML
    private VBox Chambre4;

    @FXML
    private VBox Chambre5;
    @FXML
    Label nameHotelClickedSide, addressHotelClickedSide, ratingHotelClickedSide;
    @FXML
    TextArea descriptionHotelClickedSide;
    @FXML
    Button reset;
    @FXML
    Button BookNow;
    @FXML
    private VBox Chambre6;
    @FXML
    private VBox ClickedChambreSide;

    private static LocalDate checkInSt;
    private static LocalDate checkOutSt;
    private static List<Integer> idRoomsReserved;
    public static void setCheckInSt(LocalDate checkIn) {
        checkInSt=checkIn;
    }
    public static void setCheckOutSt(LocalDate checkOut) {
        checkOutSt=checkOut;
    }
    public static LocalDate getCheckInSt() {return checkInSt;}
    public static LocalDate getCheckOutSt() {return checkOutSt;}
    public static List<Integer> getIdRoomsReserved() {
        return idRoomsReserved;
    }
    public static void setIdRoomsReserved(List<Integer> idRReserved) {
        idRoomsReserved=idRReserved;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        HotelName.setText(HomeController.getHotelselection());
        reset.setVisible(false);
        BookNow.setVisible(false);
        PaginationGest roomGest=new PaginationGest();
        HotelDAOI hotelDao = new HotelDAOImpl();
        int idHotel= 0;
        try {
            idHotel = hotelDao.idHotel(HotelName.getText());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        RoomDAOI roomlDao = new RoomDAOImpl();
        double nbrRoom = 0;
        try {
            nbrRoom = roomlDao.nbrRooms(idHotel) / 6.0;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int nbrRoominPage = roomGest.traiterNombre(nbrRoom);
        double nbHotels = 0;
        try {
            nbHotels = hotelDao.getNbrHotels() / 6.0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        int nbrHotelinPage = roomGest.traiterNombre(nbHotels);
        List <VBox> vboxes=List.of(Chambre1,Chambre2,Chambre3,Chambre4,Chambre5,Chambre6);
        try {
            OnClickPagination();
            setPagination();
            roomGest.loadViewMoreroomPag(1,nbrHotelinPage,vboxes,HotelName.getText());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        FrontMETHODS fmeth=new FrontMETHODS();
        String chambreName=((Label)((VBox)((HBox)Chambre1.getChildren().get(1)).getChildren().get(0)).getChildren().get(0)).getText();
        Integer defchambreid = Integer.parseInt(chambreName.replaceAll("\\D+", ""));
        try {
            fmeth.setSideChambre(defchambreid,ClickedChambreSide);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
    public void EV(MouseEvent event){
        List<VBox> vboxes=List.of(Chambre1,Chambre2,Chambre3,Chambre4,Chambre5,Chambre6);
        FrontMETHODS fmeth=new FrontMETHODS();
        fmeth.chambreClicked(event,vboxes,ClickedChambreSide);

    }

    public void setPagination() throws SQLException, IOException {
        PaginationGest hotelGest=new PaginationGest();
        hotelGest.setPaginationRoom(pagination,HotelName.getText());
    }

    public void OnClickPagination() {
        pagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> {
            int pageIndex = newValue.intValue() + 1; // Page actuelle
            int pageCount = pagination.getPageCount(); // Nombre total de pages
            System.out.println("L'index est : " + pageIndex);
            System.out.println("Nombre total de pages : " + pageCount);
            List<VBox> vboxes=List.of(Chambre1,Chambre2,Chambre3,Chambre4,Chambre5,Chambre5);

            try {
                PaginationGest hotelGest=new PaginationGest();
                hotelGest.loadViewMoreroomPag(pageIndex, pageCount,vboxes,HotelName.getText());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }
    private void loadView(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/" + fxmlFile));
        BorderPane view = loader.load();
        content.setCenter(view); // Set the loaded view in the center of the BorderPane
    }
    public void AddToReservation(MouseEvent event) throws IOException, SQLException, ClassNotFoundException {
        String idchext=nameHotelClickedSide.getText();
        idchext = idchext.replaceAll("\\s+", " ").trim();
        Pattern pattern = Pattern.compile("room\\s+(\\d+)");
        Integer idChambre=0;

        Matcher matcher = pattern.matcher(idchext);
        if (matcher.find()) {
            idChambre = Integer.parseInt(matcher.group(1));
            System.out.println("Extracted idChambre: " + idChambre);
        } else {
            System.out.println("No match found in the input: " + idchext);
        }
        searchMethod search=new searchMethod();
        if(checkIn.getValue()!=null && checkOut.getValue()!=null && checkIn.getValue().isBefore(checkOut.getValue()) && search.VerifyChamberDate(checkIn.getValue(),checkOut.getValue(),idChambre)){
            if(ChambersReserved.contains(idChambre)){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("room already added");
                alert.setHeaderText(null);
                alert.setContentText("the room is already in the cart");
                alert.showAndWait();
            }
            else {
                ChambersReserved.add(idChambre);
                checkIn.setDisable(true);
                checkOut.setDisable(true);
                BookNow.setVisible(true);
                reset.setVisible(true);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("room added");
                alert.setHeaderText(null);
                alert.setContentText("room " + idChambre + " successfully added to your reservation");
                alert.showAndWait();
            }
        }
        else if (checkIn.getValue()==null || checkOut.getValue()==null || checkOut.getValue().isBefore(checkIn.getValue())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("check in date before check out or empty");
            alert.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("room is not available");
            alert.setHeaderText(null);
            alert.setContentText("room is not available in the period you provided");
            alert.showAndWait();
        }


    }
    @FXML
    void ResetRes(MouseEvent event) {
        ChambersReserved.clear();
        checkIn.setDisable(false);
        checkOut.setDisable(false);
        BookNow.setVisible(false);
        reset.setVisible(false);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Reservation is cleared");
        alert.setHeaderText(null);
        alert.setContentText("Reservation is cleared");
        alert.showAndWait();
    }
    @FXML
    void Book(MouseEvent event) throws IOException {
        idRoomsReserved=ChambersReserved;
        checkInSt=checkIn.getValue();
        checkOutSt=checkOut.getValue();
        loadView("PayementChambre.fxml");


    }


}
