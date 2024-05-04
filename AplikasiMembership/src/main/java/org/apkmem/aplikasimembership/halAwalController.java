package org.apkmem.aplikasimembership;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class halAwalController {

    @FXML
    private Button btnDaftar;

    @FXML
    private Button btnMasuk;

    @FXML
    protected void onbtnDaftarClick(ActionEvent event) throws IOException {
        GUI.setRoot("halaman-daftar", "Halaman Daftar",true);
    }

    @FXML
    protected void onbtnMasukClick(ActionEvent event) throws IOException {
        GUI.setRoot("halaman-signin", "Halaman Login",true);
    }

}

