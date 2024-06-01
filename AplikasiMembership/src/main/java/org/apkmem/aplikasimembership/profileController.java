package org.apkmem.aplikasimembership;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.apkmem.aplikasimembership.data.Membership;
import org.apkmem.aplikasimembership.data.Users;
import org.apkmem.aplikasimembership.util.DBConnector;
import org.apkmem.aplikasimembership.util.SessionManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.function.Predicate;

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
    private TextField txtNamaUser;

    @FXML
    private TextField txtNoTelp;

    @FXML
    private TextField txtEmail;
    private Connection connection = DBConnector.getInstance().getConnection();
    private FilteredList<Users> userFilteredList;


    @FXML
    public void initialize() {
        SessionManager sessionManager = SessionManager.getInstance();
        if (sessionManager.isLoggedIn()) {
            txtNamaUser.setText(sessionManager.getUsername());
            txtEmail.setText(sessionManager.getEmail());
            txtNoTelp.setText(sessionManager.getNo_telp());
        }
    }
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
        SessionManager sessionManager = SessionManager.getInstance();
        Users currentUser = new Users(
                txtNamaUser.getText(),
                txtEmail.getText(),
                txtNoTelp.getText()
        );
        if (updateUsers(currentUser)) {
            new Alert(Alert.AlertType.INFORMATION, "Profil berhasil diupdate!!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Profil gagal diupdate!!").show();
        }
    }
    private boolean updateUsers(Users updatedUser) {
        String query = "UPDATE users SET username = ?, email = ?, no_telp = ? WHERE id_user = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, updatedUser.getUsername());
            preparedStatement.setString(2, updatedUser.getEmail());
            preparedStatement.setString(3, updatedUser.getTelephone());
            preparedStatement.setInt(4, SessionManager.getInstance().getId());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                SessionManager sessionManager = SessionManager.getInstance();
                sessionManager.setUserInfo(
                        updatedUser.getId(),
                        updatedUser.getUsername(),
                        updatedUser.getEmail(),
                        updatedUser.getTelephone()
                );
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    private ObservableList<Users> getObservableList() {
        return (ObservableList<Users>) userFilteredList.getSource();
    }
}
