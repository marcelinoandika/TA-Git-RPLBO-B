package org.apkmem.aplikasimembership;

import Connector.DBConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class dafMemberController implements Initializable {

    @FXML
    private Button btnCari;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnHapus;

    @FXML
    private Button btnTambah;

    @FXML
    private TableColumn<Membership, Integer> id;

    @FXML
    private TableColumn<Membership, String> jenisMembership;

    @FXML
    private TableColumn<Membership, String> namaAplikasi;

    @FXML
    private TableColumn<Membership, String> status;

    @FXML
    private TableView<Membership> table;

    @FXML
    private TableColumn<Membership, String> tglBerakhir;

    @FXML
    private TableColumn<Membership, String> tglMulai;

    @FXML
    private TextField txtCari;

    @FXML
    private Button btnDaftarMember;

    @FXML
    private Button btnMenuUtama;

    Connection connNEW = null;
    String query = null;
    PreparedStatement ps = null;
    ResultSet rs = null;


    @FXML
    protected void btnDaftarMemberClick(ActionEvent event) throws IOException {
        GUI.setRoot("daftar-membership", "Daftar Membership",true);
    }

    @FXML
    protected void btnMenuUtamaClick(ActionEvent event) throws IOException {
        GUI.setRoot("menu-utama", "Menu Utama",true);
    }


    public void getUserMembership() throws SQLException {
        try {
            connNEW = DBConnector.getConnect();
            query = "SELECT * FROM memberships";
            ps = connNEW.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                int No = rs.getInt("id");
                String namaAplikasi = rs.getString("nama_apk");
                String jenisMem = rs.getString("jenis_membership");
                String tglMulai = rs.getString("tgl_mulai");
                String tglBerakhir = rs.getString("tgl_berhenti");
                String status = rs.getString("status");

                Membership membership = new Membership(
                        No, namaAplikasi, jenisMem,
                        tglMulai, tglBerakhir, status);
                table.getItems().add(membership);
            }
        } catch (SQLException e) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getUserMembership();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        namaAplikasi.setCellValueFactory(new PropertyValueFactory<>("nama_apk"));
        jenisMembership.setCellValueFactory(new PropertyValueFactory<>("jenis_membership"));
        tglMulai.setCellValueFactory(new PropertyValueFactory<>("tgl_mulai"));
        tglBerakhir.setCellValueFactory(new PropertyValueFactory<>("tgl_berhenti"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
}

