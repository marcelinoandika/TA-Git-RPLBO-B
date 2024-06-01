package org.apkmem.aplikasimembership;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import org.apkmem.aplikasimembership.util.SessionManager;

import java.io.IOException;
/**
 * @editor David.Seay-71220909
 */
public class menuUtamaController {

    @FXML
    private Button btnDaftarMember;

    @FXML
    private Button btnDisney;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnML;

    @FXML
    private Button btnMenuUtama;

    @FXML
    private Button btnNetflix;

    @FXML
    private Button btnNotif;

    @FXML
    private Button btnPlus;

    @FXML
    private Button btnSpotify;

    @FXML
    private Button btnViu;

    @FXML
    private Button btnYoutube;

    @FXML
    private Button btnProfil;

    @FXML
    protected void btnDaftarMemberClick(ActionEvent event) throws IOException {
        editMemberController.getInstance().getSelectedAppName();
        GUI.setRoot("daftar-membership", "Daftar Membership", true);
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
            SessionManager.getInstance().logout();
            GUI.setRoot("halaman-signin", "Halaman Login",true);
        }
    }

    @FXML
    void onBtnProfil(ActionEvent event) throws IOException {
        GUI.setRoot("profil-fix", "Profile",true);
    }

    @FXML
    void btnDisneyClick(ActionEvent event) throws IOException {
        editMemberController.getInstance().setSelectedAppName("DISNEY");
        GUI.setRoot("edit-data-user", "Edit Informasi",true);
    }

    @FXML
    void btnMLClick(ActionEvent event) throws IOException {
        editMemberController.getInstance().setSelectedAppName("MOBILE LEGENDS");
        GUI.setRoot("edit-data-user", "Edit Informasi",true);
    }

    @FXML
    void btnNetflixCLick(ActionEvent event) throws IOException {
        editMemberController.getInstance().setSelectedAppName("NETFLIX");
        GUI.setRoot("edit-data-user", "Edit Informasi",true);
    }

    @FXML
    void btnSpotifyClick(ActionEvent event) throws IOException {
        editMemberController.getInstance().setSelectedAppName("SPOTIFY");
        GUI.setRoot("edit-data-user", "Edit Informasi",true);
    }

    @FXML
    void btnViuClick(ActionEvent event) throws IOException{
        editMemberController.getInstance().setSelectedAppName("VIU");
        GUI.setRoot("edit-data-user", "Edit Informasi",true);
    }

    @FXML
    void btnYoutubeClick(ActionEvent event) throws IOException{
        editMemberController.getInstance().setSelectedAppName("YOUTUBE");
        GUI.setRoot("edit-data-user", "Edit Informasi",true);
    }

    @FXML
    void onBtnNotifClick(ActionEvent event) throws IOException {
        GUI.setRoot("notifikasi", "Notifikasi",true);
    }

}

