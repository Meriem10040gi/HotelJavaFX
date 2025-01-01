package com.example.hoteljavafx.Utils;

import com.example.hoteljavafx.Utils.*;
import com.example.hoteljavafx.DAO.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class FrontMETHODS {
    GestionDB gestionDB=new GestionDB();
    public void hotelClicked(MouseEvent event, List<VBox> vboxes, List<HBox> hboxes, List <Label> labels, ImageView v, TextArea t){


        String clickedHotelName="";
        Object source=event.getSource();
        if (source instanceof VBox) {
            VBox clickedHotel = (VBox) source;
            String Hotel=( (Label) ((VBox) ((HBox) clickedHotel.getChildren().get(1)).getChildren().getFirst()).getChildren().getFirst()).getText();
            for(VBox vbox:vboxes){
                if (Hotel.equals(( (Label) ((VBox) ((HBox) vbox.getChildren().get(1)).getChildren().getFirst()).getChildren().getFirst()).getText())) {
                    clickedHotelName = Hotel;
                    break;
                }
            }
        } else if (source instanceof HBox) {
            HBox clickedHotel = (HBox) source;
            String Hotel=((Label) ((AnchorPane) clickedHotel.getChildren().get(1)).getChildren().getFirst()).getText();
            for(HBox hbox:hboxes){
                if (Hotel.equals(((Label) ((AnchorPane) hbox.getChildren().get(1)).getChildren().getFirst()).getText())){
                    clickedHotelName = Hotel;
                    break;
                }
            }
        }
        System.out.println(clickedHotelName);

        try {
            setSideHotel(clickedHotelName,labels,v,t);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void setSideHotel(String clickedHotelName, List <Label> labels, ImageView v, TextArea textArea) throws SQLException, ClassNotFoundException {
        gestionDB.connecte("hotelreservation", "root", "");
        try{
            String req = "Select name, address, description, rating, image from hotel where name=?";
            PreparedStatement ps = gestionDB.connexion.prepareStatement(req);
            ps.setString(1,clickedHotelName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                String name = rs.getString("name");
                String address = rs.getString("address");
                String description = rs.getString("description");
                double rating = rs.getDouble("rating");
                String imagePath = rs.getString("image");
                textArea.setText(description);

                textArea.setEditable(false);
                textArea.setWrapText(true);

                // Mettre à jour les composants JavaFX
                labels.get(0).setText(name);
                labels.get(1).setText(address);
                labels.get(2).setText(String.format("%.1f ★", rating)); // Affichage du rating avec une étoile
                if (imagePath != null && !imagePath.isEmpty()) {v.setImage( new Image(getClass().getResource(imagePath).toExternalForm()));} // Charge l'image (chemin ou URL)
                else {v.setImage( new Image(getClass().getResource("/Images/hotelimages/default.png").toExternalForm()));} // Charge l'image (chemin ou URL)
            }
            else{
                System.out.println("hotel not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void chambreClicked(MouseEvent event, List<VBox> vboxes, VBox v) {


        String clickedChambreName = "";
        Integer clickedChambreId=0;

        Object source = event.getSource();
        if (source instanceof VBox) {
            VBox clickedChambre = (VBox) source;
            String Chambre = ((Label) ((VBox) ((HBox) clickedChambre.getChildren().get(1)).getChildren().getFirst()).getChildren().getFirst()).getText();
            for (VBox vbox : vboxes) {
                if (Chambre.equals(((Label) ((VBox) ((HBox) vbox.getChildren().get(1)).getChildren().getFirst()).getChildren().getFirst()).getText())) {
                    clickedChambreName = Chambre;
                    String id = clickedChambreName.replaceAll("\\D+", "");
                    clickedChambreId = Integer.valueOf(id);

                    break;
                }
            }
        }
        try{
            setSideChambre(clickedChambreId,v);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void setReceipt(VBox v, List<Integer> idRoBooked, LocalDate CheckIn, LocalDate CheckOut) throws SQLException, IOException {
        RoomDAOI roomDao = new RoomDAOImpl();
        HotelDAOI hotelDao = new HotelDAOImpl();
        String hotelName= hotelDao.getHotel(roomDao.getIdHotel(idRoBooked.getFirst())).getNom();
        String roomIds="";
        Double totalPrice= (double) 0;
        if(idRoBooked.size()==1){
            roomIds="Room "+idRoBooked.getFirst();
            totalPrice=(roomDao.getRoom(idRoBooked.getFirst())).getPrix()*ChronoUnit.DAYS.between(CheckIn,CheckOut);
        }
        else{
            roomIds= "Images/Rooms " +idRoBooked.getFirst();
            totalPrice=(roomDao.getRoom(idRoBooked.getFirst())).getPrix();
            for(int i=1;i<idRoBooked.size();i++){
                roomIds=roomIds+", "+idRoBooked.get(i);
                totalPrice=totalPrice+(roomDao.getRoom(idRoBooked.get(i))).getPrix();

            }
            totalPrice=totalPrice*ChronoUnit.DAYS.between(CheckIn,CheckOut);
        }
        ((Label)v.getChildren().getFirst()).setText(roomIds+", "+hotelName);
        ((Label)v.getChildren().get(1)).setText("Check in -> " + CheckIn + " | Check out -> " + CheckOut);
        ((Label)v.getChildren().get(2)).setText("Total : " + totalPrice +"$");

    }
    public void setSideChambre(Integer clickedChambreId, VBox v) throws SQLException, ClassNotFoundException {
        gestionDB.connecte("hotelreservation", "root", "");
        try{
            String req = "Select idRoom, typeRoom, description, prix, image from room where idRoom=?";
            PreparedStatement ps = gestionDB.connexion.prepareStatement(req);
            ps.setInt(1,clickedChambreId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                Integer id = rs.getInt("idRoom");
                String typeRoom = rs.getString("typeRoom");
                String description = rs.getString("description");
                Integer prix = rs.getInt("prix");
                String image=rs.getString("image");

                ((TextArea)v.getChildren().get(3)).setText(description);
                ((TextArea)v.getChildren().get(3)).setEditable(false);
                ((TextArea)v.getChildren().get(3)).setWrapText(true);

                // Mettre à jour les composants JavaFX
                ((Label)((VBox)v.getChildren().get(1)).getChildren().get(0)).setText("room "+id);
                ((Label)((VBox)v.getChildren().get(1)).getChildren().get(1)).setText(typeRoom);
                ((Label)((VBox)v.getChildren().get(1)).getChildren().get(2)).setText(String.format(prix + " $"));
                if (image != null && !image.isEmpty()) {
                    ((ImageView)v.getChildren().get(0)).setImage( new Image(getClass().getResource(image).toExternalForm())); }
                else {
                    ((ImageView)v.getChildren().get(0)).setImage( new Image(getClass().getResource("/Images/hotelimages/default.png").toExternalForm())); }
            }
            else{
                System.out.println("hotel not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void setFooterHotels(List<HBox> hboxesRating, List<HBox> hboxesDateAjout) throws SQLException, ClassNotFoundException {
        gestionDB.connecte("hotelreservation", "root", "");

        try {
            // Requête pour récupérer les trois premiers hôtels
            String req = "SELECT name, address, image, rating FROM hotel ORDER BY rating DESC LIMIT 4";
            PreparedStatement ps = gestionDB.connexion.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            int index = 0;
            while (rs.next()) {
                String name = rs.getString("name");
                String address = rs.getString("address");
                String image = rs.getString("image");
                double rating = rs.getDouble("rating");
                if (image != null && !image.isEmpty()) {
                    ((ImageView) hboxesRating.get(index).getChildren().get(0)).setImage( new Image(getClass().getResource(image).toExternalForm()));}
                else {
                    ((ImageView) hboxesRating.get(index).getChildren().get(0)).setImage( new Image(getClass().getResource("/Images/hotelimages/default.png").toExternalForm()));}
                ((Label) ((AnchorPane) hboxesRating.get(index).getChildren().get(1)).getChildren().get(0)).setText(name);
                ((Label) ((AnchorPane) hboxesRating.get(index).getChildren().get(1)).getChildren().get(1)).setText(address);
                ((Label) ((AnchorPane) hboxesRating.get(index).getChildren().get(1)).getChildren().get(2)).setText(rating + "");
                index++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            // Requête pour récupérer les trois premiers hôtels
            String req = "SELECT name, address, image, rating FROM hotel ORDER BY dateAjout DESC LIMIT 4";
            PreparedStatement ps = gestionDB.connexion.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            int index = 0;
            while (rs.next()) {
                String name = rs.getString("name");
                String address = rs.getString("address");
                String image = rs.getString("image");
                double rating = rs.getDouble("rating");
                if (image != null && !image.isEmpty()) {
                    ((ImageView) hboxesDateAjout.get(index).getChildren().get(0)).setImage( new Image(getClass().getResource(image).toExternalForm())); }
                else{
                    ((ImageView) hboxesDateAjout.get(index).getChildren().get(0)).setImage( new Image(getClass().getResource("/Images/hotelimages/default.png").toExternalForm())); }
                ((Label) ((AnchorPane) hboxesDateAjout.get(index).getChildren().get(1)).getChildren().get(0)).setText(name);
                ((Label) ((AnchorPane) hboxesDateAjout.get(index).getChildren().get(1)).getChildren().get(1)).setText(address);
                ((Label) ((AnchorPane) hboxesDateAjout.get(index).getChildren().get(1)).getChildren().get(2)).setText(rating + "");
                index++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void loadHotels(List<VBox> vboxes) throws SQLException, ClassNotFoundException {
        HotelDAOI hotelDao = new HotelDAOImpl();
        ResultSet rs=hotelDao.RSallHotels(vboxes);
        int index = 0;
        while (rs.next()) {
            String name = rs.getString("name");
            String address = rs.getString("address");
            String image = rs.getString("image");
            if (image != null && !image.isEmpty()) {
                ((ImageView)vboxes.get(index).getChildren().get(0)).setImage( new Image(getClass().getResource(image).toExternalForm())) ;}
            else{
                ((ImageView)vboxes.get(index).getChildren().get(0)).setImage( new Image(getClass().getResource("/Images/hotelimages/default.png").toExternalForm())) ;}
            ((Label)((VBox)((HBox)vboxes.get(index).getChildren().get(1)).getChildren().get(0)).getChildren().get(0)).setText(name);
            ((Label)((VBox)((HBox)vboxes.get(index).getChildren().get(1)).getChildren().get(0)).getChildren().get(1)).setText(address);
            index++;

        }
    }
    public boolean amongMyFavorites(String clickedHotelName) throws SQLException, IOException {
        UserDAOI userDao = new UserDAOImpl();
        HotelDAOI hotelDao = new HotelDAOImpl();
        if (userDao.selectFavoriteHotels(Session.getInstance().getUserId()).isEmpty()) {
            return false;
        }
        if(userDao.selectFavoriteHotels(Session.getInstance().getUserId()).contains(hotelDao.idHotel(clickedHotelName))){
            return true;
        }
        return false;

    }
    public void setStyleHeart(String name, Pane heart) throws SQLException, IOException {
        if(amongMyFavorites(name)){
            heart.getStyleClass().clear();
            heart.getStyleClass().add("button-favorite-selected");
        }
        else{
            heart.getStyleClass().clear();
            heart.getStyleClass().add("button-favorite");
        }
    }

}