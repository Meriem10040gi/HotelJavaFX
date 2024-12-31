package com.example.hoteljavafx.Utils;

import com.example.hoteljavafx.DAO.*;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class PaginationGest {

    GestionDB gestionDB = new GestionDB();

    public void setPaginationHotel(Pagination pagination) throws SQLException,ClassNotFoundException {
        HotelDAOI hotelDao = new HotelDAOImpl();
        double nbHotels = hotelDao.getNbrHotels() / 6.0;
        int nbrHotelinPage = traiterNombre(nbHotels);
        pagination.setPageCount(nbrHotelinPage);
    }
    public int traiterNombre(double nombre) {
        if (nombre == Math.floor(nombre)) {
            return (int) nombre;
        } else {
            return (int) Math.ceil(nombre);
        }
    }
    public void setPaginationSearch(Pagination pagination, Map<Integer,List<Integer>> maphotels_rooms) throws SQLException, IOException {
        double nbHotels = maphotels_rooms.size() / 6.0;
        int nbrHotelinPage = traiterNombre(nbHotels);
        pagination.setPageCount(nbrHotelinPage);
    }
    public void loadSearchHotelPag(int currentIndex, int totalIndex, List<VBox> vboxes, Map<Integer,List<Integer>> maphotels_rooms) throws SQLException, IOException, ClassNotFoundException {
        gestionDB.connecte("hotelreservation", "root", "");
        // Requête pour récupérer les trois premiers hôtels
        String req = "SELECT name, address, image FROM hotel where idHotel IN(?) LIMIT ? OFFSET ?";
        int nbreHotel = 0;
        int indiceDeslignes = 0;
        if (currentIndex < totalIndex) {
            nbreHotel = 6;
            indiceDeslignes = (currentIndex - 1) * nbreHotel;
        } else if (currentIndex == totalIndex) {
            if(maphotels_rooms.size()%6==0){
                nbreHotel=6;
            }
            else {
                nbreHotel = maphotels_rooms.size() % 6;
            }
            indiceDeslignes = maphotels_rooms.size() - nbreHotel;
        }
        int i=0;
        String SearchedHotels="";
        for(Map.Entry<Integer,List<Integer>> MapE:maphotels_rooms.entrySet() ){
            if(i==0){
                SearchedHotels=SearchedHotels+MapE.getKey();
                i=1;
            }
            else{
                SearchedHotels=SearchedHotels+", "+MapE.getKey();
            }

        }
        PreparedStatement ps = gestionDB.connexion.prepareStatement(req);

        ps.setString(1, SearchedHotels);
        ps.setInt(2, nbreHotel);
        ps.setInt(3, indiceDeslignes);
        ResultSet rs = ps.executeQuery();
        int indice = 0;
        while (rs.next()) {
            System.out.println("rs"+rs);
            String name = rs.getString("name");
            String address = rs.getString("address");
            String image = rs.getString("image");
            if (indice < nbreHotel) {
                ((Label)((VBox)((HBox)vboxes.get(indice).getChildren().get(1)).getChildren().get(0)).getChildren().get(0)).setText(name);
                ((Label)((VBox)((HBox)vboxes.get(indice).getChildren().get(1)).getChildren().get(0)).getChildren().get(1)).setText(address);
                if (image != null && !image.isEmpty()) {
                    ((ImageView)vboxes.get(indice).getChildren().get(0)).setImage( new Image(getClass().getResource(image).toExternalForm()));
                }
                indice++;
            }
        }

        for ( i = nbreHotel; i < 6; i++) {
            ((Label)((VBox)((HBox)vboxes.get(i).getChildren().get(1)).getChildren().get(0)).getChildren().get(0)).setText("");
            ((Label)((VBox)((HBox)vboxes.get(i).getChildren().get(1)).getChildren().get(0)).getChildren().get(1)).setText("");
            ((ImageView)vboxes.get(i).getChildren().get(0)).setImage(null);
        }
    }


    public void loadViewMorehotelPag(int currentIndex, int totalIndex, List<VBox> vboxes) throws SQLException, IOException, ClassNotFoundException {
        gestionDB.connecte("hotelreservation", "root", "");
        // Requête pour récupérer les trois premiers hôtels
        String req = "SELECT name, address, image FROM hotel LIMIT ? OFFSET ?";
        int nbreHotel = 0;
        int indiceDeslignes = 0;
        if (currentIndex < totalIndex) {
            nbreHotel = 6;
            indiceDeslignes = (currentIndex - 1) * nbreHotel;
        } else if (currentIndex == totalIndex) {
            HotelDAOI hotelDao = new HotelDAOImpl();
            if(hotelDao.getNbrHotels()%6==0){
                nbreHotel=6;
            }
            else {
                nbreHotel = hotelDao.getNbrHotels() % 6;
            }
            System.out.println(nbreHotel);
            indiceDeslignes = hotelDao.getNbrHotels() - nbreHotel;
            System.out.println(indiceDeslignes);
        }
        PreparedStatement ps = gestionDB.connexion.prepareStatement(req);
        ps.setInt(1, nbreHotel);
        ps.setInt(2, indiceDeslignes);
        ResultSet rs = ps.executeQuery();
        // Parcourir les résultats
        int indice = 0;
        while (rs.next()) {
            String name = rs.getString("name");
            String address = rs.getString("address");
            String image = rs.getString("image");
            if (indice < nbreHotel) {
                ((Label)((VBox)((HBox)vboxes.get(indice).getChildren().get(1)).getChildren().get(0)).getChildren().get(0)).setText(name);
                ((Label)((VBox)((HBox)vboxes.get(indice).getChildren().get(1)).getChildren().get(0)).getChildren().get(1)).setText(address);
                if (image != null && !image.isEmpty()) {
                    ((ImageView)vboxes.get(indice).getChildren().get(0)).setImage( new Image(getClass().getResource(image).toExternalForm()));
                }
                indice++;
            }
        }
        // dans dernière page faire disparatire les anciens hotels pour afficher ce qui reste dans DB
        for (int i = nbreHotel; i < 6; i++) {
            System.out.println(("hi"));
            ((Label)((VBox)((HBox)vboxes.get(i).getChildren().get(1)).getChildren().get(0)).getChildren().get(0)).setText("");
            ((Label)((VBox)((HBox)vboxes.get(i).getChildren().get(1)).getChildren().get(0)).getChildren().get(1)).setText("");
            ((ImageView)vboxes.get(i).getChildren().get(0)).setImage(null);
        }
    }


    public void setPaginationRoom(Pagination pagination,String hotelName) throws SQLException, IOException {
        HotelDAOI hotelDao = new HotelDAOImpl();
        int idHotel=hotelDao.idHotel(hotelName);
        RoomDAOI roomlDao = new RoomDAOImpl();
        double nbrRoom =roomlDao.nbrRooms(idHotel) / 6.0;
        int nbrRoominPage = traiterNombre(nbrRoom);
        System.out.println(nbrRoominPage);
        pagination.setPageCount(nbrRoominPage);
    }



    // afficher les hotels en suivant la pagination
    public void loadViewMoreroomPag(int currentIndex, int totalIndex, List<VBox> vboxes,String hotelName ) throws SQLException, IOException, ClassNotFoundException {
        gestionDB.connecte("hotelreservation", "root", "");
        HotelDAOI hotelDao = new HotelDAOImpl();
        int idHotel=hotelDao.idHotel(hotelName);
        // Requête pour récupérer les trois premiers hôtels
        String req = "SELECT TypeRoom,idRoom, image from room where idHotel=? LIMIT ? OFFSET ? ";
        int nbreHotel = 0;
        int indiceDeslignes = 0;
        if (currentIndex < totalIndex) {
            nbreHotel = 6;
            indiceDeslignes = (currentIndex - 1) * nbreHotel;
        } else if (currentIndex == totalIndex) {
            RoomDAOI roomlDao = new RoomDAOImpl();
            if(roomlDao.nbrRooms(idHotel)%6==0){
                nbreHotel=6;
            }
            else {
                nbreHotel = roomlDao.nbrRooms(idHotel) % 6;
            }
            System.out.println(nbreHotel);
            indiceDeslignes = roomlDao.nbrRooms(idHotel) - nbreHotel;
            System.out.println(idHotel);
        }
        PreparedStatement ps = gestionDB.connexion.prepareStatement(req);
        ps.setInt(2, nbreHotel);
        ps.setInt(3, indiceDeslignes);
        ps.setInt(1, idHotel);
        ResultSet rs = ps.executeQuery();
        // Parcourir les résultats
        int indice = 0;
        while (rs.next()) {
            String  typeRoom= rs.getString("TypeRoom");
            int idChambre  = rs.getInt("idRoom");
            String image = rs.getString("image");
            if (indice < nbreHotel) {
                ((Label)((VBox)((HBox)vboxes.get(indice).getChildren().get(1)).getChildren().get(0)).getChildren().get(0)).setText("room "+idChambre);
                ((Label)((VBox)((HBox)vboxes.get(indice).getChildren().get(1)).getChildren().get(0)).getChildren().get(1)).setText(typeRoom);
                if (image != null && !image.isEmpty()) {
                    ((ImageView)vboxes.get(indice).getChildren().get(0)).setImage( new Image(getClass().getResource(image).toExternalForm()));
                }
                indice++;
            }
        }
        // dans dernière page faire disparatire les anciens hotels pour afficher ce qui reste dans DB
        for (int i = nbreHotel; i < 6; i++) {
            ((Label)((VBox)((HBox)vboxes.get(i).getChildren().get(1)).getChildren().get(0)).getChildren().get(0)).setText("");
            ((Label)((VBox)((HBox)vboxes.get(i).getChildren().get(1)).getChildren().get(0)).getChildren().get(1)).setText("");
            ((ImageView)vboxes.get(i).getChildren().get(0)).setImage(null);
        }
    }











}
