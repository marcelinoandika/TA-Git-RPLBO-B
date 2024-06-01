package org.apkmem.aplikasimembership;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
/**
 * @editor David.Seay-71220909
 */
public class halDaftarController {

    @FXML
    private Button btnDaftar;

    @FXML
    private PasswordField confPass;

    @FXML
    private TextField inpEmail;

    @FXML
    private TextField inpNoHP;

    @FXML
    private PasswordField inpPass;

    @FXML
    private TextField inpUname;

    @FXML
    void btnDaftarClick(ActionEvent event) throws IOException {
        GUI.setRoot("halaman-signin", "",false);
    }

    @FXML
    void onKeyPressEnter(KeyEvent event) throws IOException {
        if( event.getCode() == KeyCode.ENTER ) {
            btnDaftarClick(new ActionEvent());
        }
    }

}
