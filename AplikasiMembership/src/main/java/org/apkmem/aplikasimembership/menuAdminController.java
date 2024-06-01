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
import org.apkmem.aplikasimembership.data.Users;
import org.apkmem.aplikasimembership.util.DBConnector;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.function.Predicate;
/**
 * @editor David.Seay-71220909
 */
public class menuAdminController implements Initializable {

    @FXML
    private TableColumn<Membership, String> Deskripsi;

    @FXML
    private TableColumn<Membership, String> Status;

    @FXML
    private TextField TxtTglBerakhir;

    @FXML
    private Button btnDaftarMember;

    @FXML
    private Button btnHapus;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnMenuUtama;

    @FXML
    private Button btnSimpan;

    @FXML
    private Button btnTambah;

    @FXML
    private ChoiceBox<String> cbJenisPaket;

    @FXML
    private TableColumn<Membership, Integer> id;

    @FXML
    private TableColumn<Membership, String> jenisMember;

    @FXML
    private Hyperlink linkAbout;

    @FXML
    private TableColumn<Membership, String> namaAplikasi;

    @FXML
    private TextField serachBar;

    @FXML
    private TextField txtStatus;

    @FXML
    private TableView<Membership> table;

    @FXML
    private TableColumn<Membership, String> tglBerakhir;

    @FXML
    private TableColumn<Membership, String> tglMulai;

    @FXML
    private TextField txtIdUser;

    @FXML
    private TextArea txtDeskripsi;

    @FXML
    private TextField txtNamaApk;

    @FXML
    private TextField txtTglMulai;

    @FXML
    private Button btnNotif;

    @FXML
    private Button btnProfil;

    private FilteredList<Membership> membershipsFilteredList;
    Membership selectedMembership;
    private Connection connection;
    private final String DB_URL = "jdbc:sqlite:memberDB.sqlite";

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
    void onBtnHapus(ActionEvent event) {
        if (selectedMembership != null && deleteMembership(selectedMembership)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "<Membership> Dihapus!");
            alert.show();
            bersihkan();
        }
    }

    @FXML
    void onBtnSimpan(ActionEvent event) {
        if (isMembershipsUpdated()) {
            if (updateCatatan(selectedMembership, new Membership(selectedMembership.getId(), txtNamaApk.getText(), cbJenisPaket.getSelectionModel().getSelectedItem(), txtTglMulai.getText(), TxtTglBerakhir.getText(), txtStatus.getText(), txtDeskripsi.getText()))) {
                new Alert(Alert.AlertType.INFORMATION, "Membership Dirubah!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Membership gagal Dirubah!").show();
            }
        }
        bersihkan();
    }

    @FXML
    void onBtnTambah(ActionEvent event) {
        if (addMembership(new Membership(txtNamaApk.getText(), cbJenisPaket.getSelectionModel().getSelectedItem(), txtTglMulai.getText(), TxtTglBerakhir.getText(), txtStatus.getText(), Integer.parseInt(txtIdUser.getText()), txtDeskripsi.getText()))) {
            new Alert(Alert.AlertType.INFORMATION, "Catatan Ditambahkan!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Catatan gagal Ditambahkan!").show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        membershipsFilteredList = new FilteredList<>(FXCollections.observableList(FXCollections.observableArrayList()));
        table.setItems(membershipsFilteredList);
        serachBar.textProperty().addListener(
                (observableValue, oldValue, newValue) -> membershipsFilteredList.setPredicate(createPredicate(newValue))
        );
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        namaAplikasi.setCellValueFactory(new PropertyValueFactory<>("nama_apk"));
        jenisMember.setCellValueFactory(new PropertyValueFactory<>("jenis_membership"));
        tglMulai.setCellValueFactory(new PropertyValueFactory<>("tgl_mulai"));
        tglBerakhir.setCellValueFactory(new PropertyValueFactory<>("tgl_berhenti"));
        Status.setCellValueFactory(new PropertyValueFactory<>("status"));
        Deskripsi.setCellValueFactory(new PropertyValueFactory<>("deskripsi"));
        connection = DBConnector.getInstance().getConnection();
        createTable();
        getAllData();
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Membership>() {
            @Override
            public void changed(ObservableValue<? extends Membership> observableValue, Membership course, Membership t1) {
                if (observableValue.getValue() != null) {
                    selectedMembership = observableValue.getValue();
                    txtNamaApk.setText(observableValue.getValue().getNama_apk());
                    cbJenisPaket.setValue(observableValue.getValue().getJenis_membership());
                    txtTglMulai.setText(observableValue.getValue().getTgl_mulai());
                    TxtTglBerakhir.setText(observableValue.getValue().getTgl_berhenti());
                    txtDeskripsi.setText(observableValue.getValue().getDeskripsi());

                }
            }
        });

        //setup combo box
        cbJenisPaket.getItems().add(Membership.JENIS_PAKET_BRONZE);
        cbJenisPaket.getItems().add(Membership.JENIS_PAKET_SILVER);
        cbJenisPaket.getItems().add(Membership.JENIS_PAKET_GOLD);
        bersihkan();
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

    private void bersihkan() {
        txtNamaApk.clear();
        txtTglMulai.clear();
        TxtTglBerakhir.clear();
        txtDeskripsi.clear();
        serachBar.clear();
        txtNamaApk.requestFocus();
        txtTglMulai.requestFocus();
        TxtTglBerakhir.requestFocus();
        txtDeskripsi.requestFocus();
        serachBar.requestFocus();
        cbJenisPaket.getSelectionModel().clearSelection();
        cbJenisPaket.getSelectionModel().select(0);
        table.getSelectionModel().clearSelection();
        selectedMembership = null;
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
        if (!selectedMembership.getNama_apk().equalsIgnoreCase(txtNamaApk.getText()) ||
                !selectedMembership.getJenis_membership().equalsIgnoreCase(cbJenisPaket.getSelectionModel().getSelectedItem()) ||
                !selectedMembership.getTgl_mulai().equalsIgnoreCase(txtTglMulai.getText()) ||
                !selectedMembership.getTgl_berhenti().equalsIgnoreCase(TxtTglBerakhir.getText()) ||
                !selectedMembership.getDeskripsi().equalsIgnoreCase(txtDeskripsi.getText())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteMembership(Membership membership) {
        String query = "DELETE FROM memberships WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, membership.getId());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                getObservableList().remove(membership);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean addMembership(Membership membership) {
        String queryGetNextId = "SELECT seq FROM SQLITE_SEQUENCE WHERE name = 'memberships' LIMIT 1";
        String queryInsert = "INSERT INTO memberships (nama_apk, jenis_membership, tgl_mulai, tgl_berhenti, status, id_user, deskripsi) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            connection.setAutoCommit(false); // Start transaction
            try (PreparedStatement getNextIdStatement = connection.prepareStatement(queryGetNextId);
                 PreparedStatement insertStatement = connection.prepareStatement(queryInsert)) {
                // Execute query to get the next ID
                ResultSet resultSet = getNextIdStatement.executeQuery();
                int nextId = 1; // Default value if no rows are returned
                if (resultSet.next()) {
                    nextId = resultSet.getInt("seq") + 1;
                }
                // Set parameters for insert query
                insertStatement.setString(1, membership.getNama_apk());
                insertStatement.setString(2, membership.getJenis_membership());
                insertStatement.setString(3, membership.getTgl_mulai());
                insertStatement.setString(4, membership.getTgl_berhenti());
                insertStatement.setString(5, membership.getStatus());
                insertStatement.setInt(6, membership.getId_user());
                insertStatement.setString(7, membership.getDeskripsi());

                // Execute insert query
                int rowsAffected = insertStatement.executeUpdate();

                if (rowsAffected > 0) {
                    membership.setId(nextId);
                    getObservableList().add(membership);
                    connection.commit(); // Commit transaction
                    return true;
                }
            } catch (SQLException e) {
                connection.rollback(); // Rollback transaction
                e.printStackTrace();
                // Handle database query error
            } finally {
                connection.setAutoCommit(true); // Reset auto-commit mode
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean updateCatatan(Membership oldMembership, Membership newMembership) {
        String query = "UPDATE memberships SET nama_apk = ?, jenis_membership = ?,  tgl_mulai = ?, tgl_berhenti = ?, status = ?, deskripsi = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newMembership.getNama_apk());
            preparedStatement.setString(2, newMembership.getJenis_membership());
            preparedStatement.setString(3, newMembership.getTgl_mulai());
            preparedStatement.setString(4, newMembership.getTgl_berhenti());
            preparedStatement.setString(5, newMembership.getStatus());
            preparedStatement.setString(6, newMembership.getDeskripsi());
            preparedStatement.setInt(7, oldMembership.getId());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                int iOldMembership = getObservableList().indexOf(oldMembership);
                getObservableList().set(iOldMembership, newMembership);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database query error
        }
        return false;
    }
}
