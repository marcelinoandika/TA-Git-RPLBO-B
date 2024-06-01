package org.apkmem.aplikasimembership;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.apkmem.aplikasimembership.util.SessionManager;

import java.io.IOException;
/**
 * @editor David.Seay-71220909
 */
public class profileController {

    @FXML
    private Button btnDaftarMember;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnMenuUtama;

    @FXML
    private Button btnNotif;

    @FXML
    private Button btnProfil;

    @FXML
    private Button btnSimpan;

    @FXML
    private Hyperlink linkAbout;

    @FXML
    private RadioButton radioF;

    @FXML
    private RadioButton radioM;

    @FXML
    private TextField txtNamaUser;

    @FXML
    private TextField txtNoTelp;

    @FXML
    private PasswordField txtPass;

    @FXML
    private TextField txtUser;

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
    void linkAboutClick(ActionEvent event) throws IOException {
        GUI.setRoot("halaman-about", "About",true);
    }

    @FXML
    void onBtnNotif(ActionEvent event) throws IOException {
        GUI.setRoot("notifikasi", "Notifikasi",true);
    }

    @FXML
    void onBtnProfil(ActionEvent event) throws IOException {
        GUI.setRoot("profil-fix", "Profile",true);
    }

    @FXML
    void onBtnSimpan(ActionEvent event) {

    }

    @FXML
    void onRadioF(ActionEvent event) {

    }

    @FXML
    void onRadioM(ActionEvent event) {

    }

}
