package org.apkmem.aplikasimembership;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.apkmem.aplikasimembership.data.Users;
import org.apkmem.aplikasimembership.util.DBConnector;
import org.apkmem.aplikasimembership.util.SessionManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * @editor David.Seay-71220909
 */
public class halSigninController {

    @FXML
    private Button btnKeluar;

    @FXML
    private Button btnLogin;

    @FXML
    private PasswordField txtpassword;

    @FXML
    private TextField txtusername;

    @FXML
    void onbtnKeluar(ActionEvent event) throws IOException {
        GUI.setRoot("halaman-awal", "APLIKASI PENGELOAAN MEMBERSHIP", true);
    }

    @FXML
    protected void onKeyPressEvent(KeyEvent event) throws IOException, SQLException {
        if (event.getCode() == KeyCode.ENTER) {
            btnLoginClick(new ActionEvent());
        }
    }

    @FXML
    protected void btnLoginClick(ActionEvent event) throws IOException, SQLException {
        Alert alert;
        if (txtusername.getText().isBlank() && txtpassword.getText().isBlank()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("Login failed!! Please insert your username and password");
            alert.showAndWait();
            txtusername.requestFocus();
        } else {
            validateLogin();
        }
    }

    private void validateLogin() throws SQLException {
        Connection connNEW = DBConnector.getInstance().getConnection();

        String verif = "SELECT * FROM users WHERE username = '" + txtusername.getText() + "' AND password = '" + txtpassword.getText() + "'";
        Statement ps = connNEW.createStatement();
        ResultSet rs = ps.executeQuery(verif);
            try {
                while (rs.next()) {
                    int userId = rs.getInt("id_user");
                    String username = rs.getString("username");
                    String email = rs.getString("email");
                    String phoneNumber = rs.getString("no_telp");
                    String password = rs.getString("password");
                    Users loggedInUser = new Users(userId, username, email, phoneNumber, password);
                    Alert alert;
                    if (loggedInUser != null) {
                        if(txtusername.getText().equals("admin") && txtpassword.getText().equals("admin")) {
                            alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setHeaderText("Information");
                            alert.setContentText("Login success!!");
                            alert.showAndWait();
                            GUI.setRoot("menu-admin", "Menu Utama", true);
                        }
                        else {
                            alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setHeaderText("Information");
                            alert.setContentText("Login success!!");
                            SessionManager.getInstance().setUserInfo(userId, username, email, phoneNumber);
                            SessionManager.getInstance().login();
                            alert.showAndWait();
                            GUI.setRoot("menu-utama", "Menu Utama", true);
                        }
                    } else {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("Error");
                        alert.setContentText("Invalid Login!! Please check again.");
                        alert.showAndWait();
                        txtusername.requestFocus();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

