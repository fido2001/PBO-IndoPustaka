package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sample.models.m_DataBuku;
import sample.models.m_Login;
import sample.utils.Helpers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class c_HomeMasyarakat extends Helpers implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            showDataBuku();
            ObservableList<String> data = FXCollections.observableArrayList("Ambil Sendiri", "Antar Gojek");
            caraPengambilan.setItems(data);
            caraPengambilan.setValue("Ambil Sendiri");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    private TableView<m_DataBuku> tabelBuku;

    @FXML
    private TableColumn<m_DataBuku, String> kolomKode;

    @FXML
    private TableColumn<m_DataBuku, String> kolomJudul;

    @FXML
    private TableColumn<m_DataBuku, String> kolomKategori;

    @FXML
    private TableColumn<m_DataBuku, String> kolomPenulis;

    @FXML
    private TableColumn<m_DataBuku, Integer> kolomJumlah;

    @FXML
    private TableColumn<m_DataBuku, String> kolomKeterangan;

    @FXML
    private TextField fieldKode;

    @FXML
    private TextField fieldJudul;

    @FXML
    private TextField fieldKategori;

    @FXML
    private TextField fieldPenulis;

    @FXML
    private Label fieldId;

    @FXML
    private TextField jumlahPinjam;

    @FXML
    private DatePicker tglPinjam;

    @FXML
    private DatePicker tglKembali;

    @FXML
    private ComboBox<String> caraPengambilan;

    @FXML
    private TextArea alamat;

    @FXML
    private TextField filterField;

    private String idAkun, kodeBuku="", judul="", kategori="", penulis="", keterangan="";
    private Integer jumlah=null;

    @FXML
    private void btnAkun(ActionEvent event) { changePageMasyarakat(event, "v_AkunSaya"); }

    @FXML
    private void btnDonasi(ActionEvent event) { changePageMasyarakat(event, "v_Donasi"); }

    @FXML
    private void btnPenerimaan(ActionEvent event) { changePageMasyarakat(event, "v_Penerimaan"); }

    @FXML
    private void btnPeminjaman(ActionEvent event) { changePageMasyarakat(event, "v_RekapPeminjaman"); }

    @FXML
    private void logout(ActionEvent event) { changePage(event, "v_Dashboard"); }

    public void showDataBuku() throws SQLException {
        ObservableList<m_DataBuku> list = m_DataBuku.getDataBuku();

        kolomKode.setCellValueFactory(new PropertyValueFactory<m_DataBuku, String>("kodeBuku"));
        kolomJudul.setCellValueFactory(new PropertyValueFactory<m_DataBuku, String>("judul"));
        kolomKategori.setCellValueFactory(new PropertyValueFactory<m_DataBuku, String>("kategori"));
        kolomPenulis.setCellValueFactory(new PropertyValueFactory<m_DataBuku, String>("penulis"));
        kolomJumlah.setCellValueFactory(new PropertyValueFactory<m_DataBuku, Integer>("jumlah"));
        kolomKeterangan.setCellValueFactory(new PropertyValueFactory<m_DataBuku, String>("keterangan"));

        tabelBuku.setItems(list);

        FilteredList<m_DataBuku> filteredData = new FilteredList<>(list, b -> true);

        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(m_dataBuku -> {
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if(m_dataBuku.getJudul().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if(m_dataBuku.getPenulis().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                else if (m_dataBuku.getKategori().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                else if (m_dataBuku.getKeterangan().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                else if (m_dataBuku.getKodeBuku().toLowerCase().indexOf(lowerCaseFilter) != -1)
                    return true;
                else
                    return false;
            });
        });
        SortedList<m_DataBuku> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(tabelBuku.comparatorProperty());

        tabelBuku.setItems(sortedData);
    }

    @FXML
    void ambilKode(MouseEvent event) throws IOException{
        if(event.getClickCount()==1){
            kodeBuku = tabelBuku.getSelectionModel().getSelectedItem().getKodeBuku();
            judul = tabelBuku.getSelectionModel().getSelectedItem().getJudul();
            kategori = tabelBuku.getSelectionModel().getSelectedItem().getKategori();
            penulis = tabelBuku.getSelectionModel().getSelectedItem().getPenulis();
            jumlah = tabelBuku.getSelectionModel().getSelectedItem().getJumlah();
            keterangan = tabelBuku.getSelectionModel().getSelectedItem().getKeterangan();
            fieldKode.setText(kodeBuku);
            fieldJudul.setText(judul);
            fieldKategori.setText(kategori);
            fieldPenulis.setText(penulis);
        }
    }

    @FXML
    void PinjamClicked(ActionEvent event) throws SQLException {
        if(kodeBuku.equals("")){
            showAlert(Alert.AlertType.WARNING, "Peringatan", null, "Buku belum dipilih !!");
        }
        else if(jumlahPinjam.getText().equals("")||tglPinjam.getValue().equals("")||tglKembali.getValue().equals("")||caraPengambilan.getValue().equals("")) {
            showAlert(Alert.AlertType.WARNING, "Peringatan", null, "Harap lengkapi data peminjaman !!");
        }
        else {
            String kodeBuku = fieldKode.getText();
            String alm = alamat.getText();
            String tanggalPinjam = tglPinjam.getValue().toString();
            String tanggalKembali = tglKembali.getValue().toString();
            String caraAmbil = caraPengambilan.getValue();
            Integer jmlPinjam = Integer.parseInt(jumlahPinjam.getText());
            m_DataBuku.insertPeminjaman(kodeBuku, alm, tanggalPinjam, tanggalKembali, caraAmbil, jmlPinjam);
            if(m_DataBuku.getStatusInsert()==true){
                showAlert(Alert.AlertType.INFORMATION, "Sukses", null, "Data Peminjaman Berhasil Dimasukkan");
                showDataBuku();
            }
            else{
                showAlert(Alert.AlertType.ERROR, "Error", null, "Data Peminjaman gagal dimasukkan..");
            }
        }
    }

}
