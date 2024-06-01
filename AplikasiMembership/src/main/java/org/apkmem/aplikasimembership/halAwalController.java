package org.apkmem.aplikasimembership;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;
/**
 * @editor David.Seay-71220909
 */
public class halAwalController {

    @FXML
    protected void onbtnDaftarClick(ActionEvent event) throws IOException {
        GUI.setRoot("halaman-daftar", "Halaman Daftar",true);
    }

    @FXML
    protected void onbtnMasukClick(ActionEvent event) throws IOException {
        GUI.setRoot("halaman-signin", "Halaman Login",true);
    }

}

