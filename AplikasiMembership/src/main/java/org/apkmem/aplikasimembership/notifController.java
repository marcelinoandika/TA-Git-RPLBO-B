package org.apkmem.aplikasimembership;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import org.apkmem.aplikasimembership.util.SessionManager;

import java.io.IOException;
/**
 * @editor David.Seay-71220909
 */
public class notifController {

    @FXML
    private Button btnDaftarMember;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnMenuUtama;

    @FXML
    private Hyperlink linkAbout;

    @FXML
    private Button btnProfil;

    @FXML
    private Button btnNotif;

    @FXML
    void btnDaftarMemberClick(ActionEvent event) throws IOException {
        GUI.setRoot("daftar-membership", "Daftar Membership", true);
    }

    @FXML
    void btnLogoutClick(ActionEvent event) throws IOException {
        Alert alert;
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("LOGOUT");
        alert.setContentText("Are you sure want to LOGOUT?");
        if(alert.showAndWait().get() == ButtonType.OK) {
            SessionManager.getInstance().logout();
            GUI.setRoot("halaman-signin", "Halaman Login",true);
        }
    }

    @FXML
    void btnMenuUtamaClick(ActionEvent event) throws IOException {
        GUI.setRoot("menu-utama", "Menu Utama",true);
    }

    @FXML
    void onBtnProfil(ActionEvent event) throws IOException {
        GUI.setRoot("profil-fix", "Profile",true);
    }

    @FXML
    void onBtnNotif(ActionEvent event) throws IOException {
        GUI.setRoot("notifikasi", "Notifikasi",true);
    }

    @FXML
    void linkAboutClick(ActionEvent event) throws IOException {
        GUI.setRoot("halaman-about", "About",true);
    }

}

