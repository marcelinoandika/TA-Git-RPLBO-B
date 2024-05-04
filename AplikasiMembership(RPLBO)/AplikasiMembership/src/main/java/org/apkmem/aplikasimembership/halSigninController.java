package org.apkmem.aplikasimembership;

import Connector.DBConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class halSigninController {

    @FXML
    private Button btnLogin;

    @FXML
    private PasswordField txtpassword;

    @FXML
    private TextField txtusername;

    @FXML
    protected void onKeyPressEvent(KeyEvent event) throws IOException, SQLException {
        if( event.getCode() == KeyCode.ENTER ) {
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
        Connection connNEW = DBConnector.getConnect();

        String verif = "SELECT count(1) FROM users WHERE username = '"+txtusername.getText()+"' AND password = '"+txtpassword.getText()+"'";
        Statement ps = connNEW.createStatement();
        ResultSet rs = ps.executeQuery(verif);

        try {
            while (rs.next()) {
                Alert alert;
                if(rs.getInt(1) == 1) {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Information");
                    alert.setContentText("Login success!!");
                    alert.showAndWait();
                    GUI.setRoot("menu-utama", "Menu Utama",true);
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
