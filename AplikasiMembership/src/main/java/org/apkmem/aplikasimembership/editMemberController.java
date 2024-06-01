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
import javafx.scene.input.MouseEvent;
import org.apkmem.aplikasimembership.data.Membership;
import org.apkmem.aplikasimembership.data.Users;
import org.apkmem.aplikasimembership.util.DBConnector;
import org.apkmem.aplikasimembership.util.SessionManager;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * @editor David.Seay-71220909
 */
public class editMemberController implements Initializable {

    @FXML
    private Button btnDaftarMember;

    @FXML
    private Button btnHapus;

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
    private ChoiceBox<String> cbJenisPaker;

    @FXML
    private TextField dateAkhir;

    @FXML
    private TextField dateMulai;

    @FXML
    private TableColumn<Membership, String> desc;

    @FXML
    private TableColumn<Membership, Integer> id;

    @FXML
    private TableColumn<Membership, String> jenisMembership;

    @FXML
    private Hyperlink linkAbout;

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
    private TextArea txtDesc;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtStatus;


    @FXML
    private TextField txtNamaAplikasi;

    private int FK = SessionManager.getInstance().getId();
    private FilteredList<Membership> membershipsFilteredList;
    Membership selectedMembership;
    private Connection connection;
    private final String DB_URL = "jdbc:sqlite:memberDB.sqlite";

    private static editMemberController instance;
    private String selectedAppName;

    public editMemberController() {}

    public static editMemberController getInstance() {
        if (instance == null) {
            instance = new editMemberController();
        }
        return instance;
    }

    public String getSelectedAppName() {
        return selectedAppName;
    }

    public void setSelectedAppName(String selectedAppName) {
        this.selectedAppName = selectedAppName;
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
        if (isMembershipsUpdated()) {
            if (updateCatatan(selectedMembership, new Membership(selectedMembership.getId(), txtNamaAplikasi.getText(), cbJenisPaker.getSelectionModel().getSelectedItem(), dateMulai.getText(), dateAkhir.getText(), txtStatus.getText(), txtDesc.getText()))) {
                new Alert(Alert.AlertType.INFORMATION, "Membership Dirubah!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Membership gagal Dirubah!").show();
            }
        }
        bersihkan();
    }

    @FXML
    void onBtnHapus(ActionEvent event) {
        if (selectedMembership != null && deleteMembership(selectedMembership)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Membership Dihapus!");
            alert.show();
            bersihkan();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        membershipsFilteredList = new FilteredList<>(FXCollections.observableList(FXCollections.observableArrayList()));
        table.setItems(membershipsFilteredList);
        txtSearch.textProperty().addListener(
                (observableValue, oldValue, newValue) -> membershipsFilteredList.setPredicate(createPredicate(newValue))
        );
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        namaAplikasi.setCellValueFactory(new PropertyValueFactory<>("nama_apk"));
        jenisMembership.setCellValueFactory(new PropertyValueFactory<>("jenis_membership"));
        tglMulai.setCellValueFactory(new PropertyValueFactory<>("tgl_mulai"));
        tglBerakhir.setCellValueFactory(new PropertyValueFactory<>("tgl_berhenti"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        desc.setCellValueFactory(new PropertyValueFactory<>("deskripsi"));
        connection = DBConnector.getInstance().getConnection();
        createTable();
        String selectedAppName = editMemberController.getInstance().getSelectedAppName();
        if (selectedAppName != null) {
            getAllData(selectedAppName);
        }
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Membership>() {
            @Override
            public void changed(ObservableValue<? extends Membership> observableValue, Membership course, Membership t1) {
                if (observableValue.getValue() != null) {
                    selectedMembership = observableValue.getValue();
                    txtNamaAplikasi.setText(observableValue.getValue().getNama_apk());
                    cbJenisPaker.setValue(observableValue.getValue().getJenis_membership());
                    dateMulai.setText(observableValue.getValue().getTgl_mulai());
                    dateAkhir.setText(observableValue.getValue().getTgl_berhenti());
                    txtStatus.setText(observableValue.getValue().getStatus());
                    txtDesc.setText(observableValue.getValue().getDeskripsi());

                }
            }
        });

        //setup combo box
        cbJenisPaker.getItems().add(Membership.JENIS_PAKET_BRONZE);
        cbJenisPaker.getItems().add(Membership.JENIS_PAKET_SILVER);
        cbJenisPaker.getItems().add(Membership.JENIS_PAKET_GOLD);
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
        txtNamaAplikasi.clear();
        dateMulai.clear();
        dateAkhir.clear();
        txtStatus.clear();
        txtDesc.clear();
        txtSearch.clear();
        txtNamaAplikasi.requestFocus();
        dateMulai.requestFocus();
        dateAkhir.requestFocus();
        txtStatus.requestFocus();
        txtDesc.requestFocus();
        txtSearch.requestFocus();
        cbJenisPaker.getSelectionModel().clearSelection();
        cbJenisPaker.getSelectionModel().select(0);
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

    private void getAllData(String appName) {
        String query = "SELECT * FROM memberships WHERE id_user = ? AND nama_apk = ?";
        getObservableList().clear();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, FK);
            preparedStatement.setString(2, appName);
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
        if (!selectedMembership.getNama_apk().equalsIgnoreCase(txtNamaAplikasi.getText()) ||
                !selectedMembership.getJenis_membership().equalsIgnoreCase(cbJenisPaker.getSelectionModel().getSelectedItem()) ||
                !selectedMembership.getTgl_mulai().equalsIgnoreCase(dateMulai.getText()) ||
                !selectedMembership.getTgl_berhenti().equalsIgnoreCase(dateAkhir.getText()) ||
                !selectedMembership.getStatus().equalsIgnoreCase(txtStatus.getText()) ||
                !selectedMembership.getDeskripsi().equalsIgnoreCase(txtDesc.getText())) {
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
