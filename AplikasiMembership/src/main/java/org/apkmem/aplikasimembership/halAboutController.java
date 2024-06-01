package org.apkmem.aplikasimembership;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;

import java.io.IOException;
/**
 * @editor David.Seay-71220909
 */
public class halAboutController {

    @FXML
    private Button btnDaftarMember;

    @FXML
    private Button btnMenuUtama;

    @FXML
    private Hyperlink linkAbout;

    @FXML
    private Button btnLogout;


    @FXML
    protected void btnDaftarMemberClick(ActionEvent event) throws IOException {
        GUI.setRoot("daftar-membership", "Daftar Membership",true);
    }

    @FXML
    protected void btnMenuUtamaClick(ActionEvent event) throws IOException {
        GUI.setRoot("menu-utama", "Menu Utama",true);
    }

    @FXML
    protected void linkAboutClick(ActionEvent event) throws IOException {
        GUI.setRoot("halaman-about", "About",true);
    }

    @FXML
    protected void btnLogoutClick(ActionEvent event) throws IOException {
        Alert alert;
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("LOGOUT");
        alert.setContentText("Are you sure want to LOGOUT?");
        if(alert.showAndWait().get() == ButtonType.OK) {
            GUI.setRoot("halaman-signin", "Halaman Login",true);
        }
    }

}

