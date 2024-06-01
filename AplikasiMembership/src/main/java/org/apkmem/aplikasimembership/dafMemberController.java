package org.apkmem.aplikasimembership;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apkmem.aplikasimembership.data.Membership;
import org.apkmem.aplikasimembership.util.DBConnector;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * @editor David.Seay-71220909
 */
public class dafMemberController implements Initializable {

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
    private TableColumn<Membership, String> txtDesc;

    @FXML
    private TextField txtCari;

    @FXML
    private Button btnDaftarMember;

    @FXML
    private Button btnMenuUtama;

    private FilteredList<Membership> membershipsFilteredList;
    Membership selectedMembership;
    private Connection connection;
    private final String DB_URL = "jdbc:sqlite:memberDB.sqlite";


    @FXML
    protected void btnDaftarMemberClick(ActionEvent event) throws IOException {
        GUI.setRoot("daftar-membership", "Daftar Membership",true);
    }

    @FXML
    protected void btnMenuUtamaClick(ActionEvent event) throws IOException {
        GUI.setRoot("menu-utama", "Menu Utama",true);
    }

    @FXML
    protected void btnLogoutClick(ActionEvent event) throws IOException {
        Alert alert;
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("LOGOUT");
        alert.setContentText("Are you sure want to LOGOUT?");
        if(alert.showAndWait().get() == ButtonType.OK) {
            GUI.setRoot("halaman-signin", "Halaman Login",true);
        }
    }

    @FXML
    protected void linkAboutClick(ActionEvent event) throws IOException {
        GUI.setRoot("halaman-about", "About",true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        membershipsFilteredList = new FilteredList<>(FXCollections.observableList(FXCollections.observableArrayList()));
        table.setItems(membershipsFilteredList);
        txtCari.textProperty().addListener(
                (observableValue, oldValue, newValue) -> membershipsFilteredList.setPredicate(createPredicate(newValue))
        );
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        namaAplikasi.setCellValueFactory(new PropertyValueFactory<>("nama_apk"));
        jenisMembership.setCellValueFactory(new PropertyValueFactory<>("jenis_membership"));
        tglMulai.setCellValueFactory(new PropertyValueFactory<>("tgl_mulai"));
        tglBerakhir.setCellValueFactory(new PropertyValueFactory<>("tgl_berhenti"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        txtDesc.setCellValueFactory(new PropertyValueFactory<>("deskripsi"));
        connection = DBConnector.getInstance().getConnection();
        createTable();
        getAllData();
    }

    public void createTable() {
        String mhsTableSql = "CREATE TABLE IF NOT EXISTS memberships ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nama_apk TEXT NOT NULL,"
                + "jenis_membership TEXT NOT NULL,"
                + "tgl_mulai DATE NOT NULL,"
                + "tgl_berhenti TEXT NOT NULL,"
                + "status TEXT NOT NULL,"
                + "id_user INT(10) NOT NULL,"
                + "deskripsi TEXT NOT NULL"
                + ")";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(mhsTableSql);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle table creation error
        }
    }

    private ObservableList<Membership> getObservableList() {
        return (ObservableList<Membership>) membershipsFilteredList.getSource();
    }

    private Predicate<Membership> createPredicate(String searchText) {
        return membership -> {
            if (searchText == null || searchText.isEmpty()) return true;
            return searchFindsMembership(membership, searchText);
        };
    }

    private boolean searchFindsMembership(Membership membership, String searchText) {
        return (membership.getNama_apk().toLowerCase().contains(searchText.toLowerCase())) ||
                (membership.getJenis_membership().toLowerCase().contains(searchText.toLowerCase())) ||
                (membership.getStatus().toLowerCase().contains(searchText.toLowerCase()));
    }

    private void getAllData() {
        String query = "SELECT * FROM memberships";
        getObservableList().clear();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String namaAplikasi = rs.getString("nama_apk");
                String jenisMem = rs.getString("jenis_membership");
                String tglMulai = rs.getString("tgl_mulai");
                String tglBerakhir = rs.getString("tgl_berhenti");
                String status = rs.getString("status");
                String desc = rs.getString("deskripsi");
                Membership membership = new Membership(
                        id, namaAplikasi, jenisMem,
                        tglMulai, tglBerakhir, status,desc);
                getObservableList().add(membership);
            }
        } catch (SQLException e) {
            // Handle database query error
            e.printStackTrace();
        }
    }

    private boolean isMembershipsUpdated() {
        if (selectedMembership == null) {
            return false;
        }
        if (!selectedMembership.getNama_apk().equalsIgnoreCase(namaAplikasi.getText()) ||
                !selectedMembership.getJenis_membership().equalsIgnoreCase(jenisMembership.getText()) ||
                !selectedMembership.getStatus().equalsIgnoreCase(status.getText())) {
            return true;
        } else {
            return false;
        }
    }
}

