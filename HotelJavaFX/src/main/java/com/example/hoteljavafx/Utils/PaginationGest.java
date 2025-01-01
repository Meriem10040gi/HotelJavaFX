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
import java.util.ArrayList;
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


    public void setPaginationFavorite(Pagination pagination,List<Integer> idHotels) throws SQLException, IOException {
        double nbHotelfavorite = idHotels.size() / 6.0;
        int nbrHotelinPage = traiterNombre(nbHotelfavorite);
        System.out.println("nbr pagination : "+nbrHotelinPage);
        pagination.setPageCount(nbrHotelinPage);
    }

    public void loadFavoriteHotels(int pageActuelle, int totalPages, List<VBox> vboxes, List<Integer> idHotels) throws SQLException, IOException, ClassNotFoundException {
        // Connexion à la base de données
        gestionDB.connecte("hotelreservation", "root", "");

        // Déclaration des variables
        List<Integer> nomsSelectionnes = new ArrayList<>();
        int nombreHotels = 0;
        int indiceDebut = 0;

        // Gestion des indices de pagination
        if (pageActuelle < totalPages) {
            nombreHotels = 6; // Nombre d'hôtels par page
            indiceDebut = (pageActuelle - 1) * nombreHotels;
        } else if (pageActuelle == totalPages) {
            // Si c'est la dernière page, ajuster le nombre d'hôtels restants
            if (idHotels.size() % 6 == 0) {
                nombreHotels = 6;
            } else {
                nombreHotels = idHotels.size() % 6;
            }
            indiceDebut = idHotels.size() - nombreHotels;
        }

        // Sélectionner une sous-liste d'hôtels pour la page actuelle
        nomsSelectionnes = idHotels.subList(indiceDebut, indiceDebut + nombreHotels);

        // Construction de la requête SQL avec des placeholders
        String placeholders = String.join(",", nomsSelectionnes.stream().map(id -> "?").toArray(String[]::new));
        String requete = "SELECT name, address, image FROM hotel WHERE idHotel IN (" + placeholders + ")";
        PreparedStatement ps = gestionDB.connexion.prepareStatement(requete);

        // Assignation des paramètres dans la requête
        for (int i = 0; i < nomsSelectionnes.size(); i++) {
            ps.setInt(i + 1, nomsSelectionnes.get(i));
        }

        // Exécution de la requête
        ResultSet rs = ps.executeQuery();

        // Parcourir les résultats et mettre à jour l'interface utilisateur (VBox)
        int indice = 0;
        while (rs.next()) {
            String nomHotel = rs.getString("name");
            String adresse = rs.getString("address");
            String image = rs.getString("image");

            if (indice < nombreHotels) {
                // Mise à jour des labels et de l'image
                ((Label) ((VBox) ((HBox) vboxes.get(indice).getChildren().get(1))
                        .getChildren().get(0)).getChildren().get(0)).setText(nomHotel);
                ((Label) ((VBox) ((HBox) vboxes.get(indice).getChildren().get(1))
                        .getChildren().get(0)).getChildren().get(1)).setText(adresse);

                if (image != null && !image.isEmpty()) {
                    ((ImageView) vboxes.get(indice).getChildren().get(0)).setImage(new Image(getClass().getResource(image).toExternalForm()));
                }
                indice++;
            }
        }

        // Effacer les anciens éléments pour les indices restants sur la dernière page
        for (int i = nombreHotels; i < 6; i++) {
            ((Label) ((VBox) ((HBox) vboxes.get(i).getChildren().get(1))
                    .getChildren().get(0)).getChildren().get(0)).setText("");
            ((Label) ((VBox) ((HBox) vboxes.get(i).getChildren().get(1))
                    .getChildren().get(0)).getChildren().get(1)).setText("");
            ((ImageView) vboxes.get(i).getChildren().get(0)).setImage(null);
        }
    }


}
