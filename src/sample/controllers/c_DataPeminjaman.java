package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import sample.models.m_DataBuku;
import sample.models.m_DataPeminjaman;
import sample.models.m_Donasi;
import sample.utils.Helpers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class c_DataPeminjaman extends Helpers implements Initializable {

    @FXML
    private TableView<m_DataPeminjaman> tabelPeminjaman;

    @FXML
    private TableColumn<m_DataPeminjaman, String > kolomKodeBuku;

    @FXML
    private TableColumn<m_DataPeminjaman, String > kolomIdPeminjam;

    @FXML
    private TableColumn<m_DataPeminjaman, String > kolomNamaPeminjam;

    @FXML
    private TableColumn<m_DataPeminjaman, String> kolomJudul;

    @FXML
    private TableColumn<m_DataPeminjaman, String> kolomTglPinjam;

    @FXML
    private TableColumn<m_DataPeminjaman, String> kolomTglKembali;

    @FXML
    private TableColumn<m_DataPeminjaman, Integer> kolomJumlah;

    @FXML
    private TextField fieldKode;

    @FXML
    private TextField fieldJudul;

    @FXML
    private TextField fieldTglPinjam;

    @FXML
    private TextField fieldTglKembali;

    @FXML
    private DatePicker fieldTglPengembalian;

    @FXML
    private Label fieldId;

    @FXML
    private TextField fieldCaraPengembalian;

    @FXML
    private ComboBox<String> fieldStatus;

    @FXML
    private TextField fieldDenda;

    private String idPeminjaman="", kodeBuku="", judul="",tglPinjam="",tglKembali="";

    @FXML
    void SimpanClicked(ActionEvent event) throws SQLException {
        if(idPeminjaman.equals("")){
            showAlert(Alert.AlertType.WARNING, "Peringatan", null, "Data Peminjaman belum dipilih !!");
        }
        else {
            String id = fieldId.getText();
            String kode = fieldKode.getText();
            String status = fieldStatus.getValue();
            String tanggal = fieldTglPengembalian.getValue().toString();
            String cara = fieldCaraPengembalian.getText();
            Integer denda = Integer.parseInt(fieldDenda.getText());
            m_DataPeminjaman.insertPengembalian(id, kode, status, tanggal, cara, denda);
            if(m_DataPeminjaman.getStatusInsert()==true){
                showAlert(Alert.AlertType.INFORMATION, "Sukses", null, "Data Peminjaman Berhasil Dimasukkan");
                showDataPeminjaman();
            }
            else{
                showAlert(Alert.AlertType.ERROR, "Error", null, "Data Peminjaman gagal dimasukkan..");
            }
        }
    }

    @FXML
    void ambilIdPeminjaman(MouseEvent event) {
        idPeminjaman = tabelPeminjaman.getSelectionModel().getSelectedItem().getIdPeminjam();
        kodeBuku = tabelPeminjaman.getSelectionModel().getSelectedItem().getKodeBuku();
        judul = tabelPeminjaman.getSelectionModel().getSelectedItem().getJudul();
        tglPinjam = tabelPeminjaman.getSelectionModel().getSelectedItem().getTglPinjam();
        tglKembali = tabelPeminjaman.getSelectionModel().getSelectedItem().getTglKembali();
        fieldId.setText(idPeminjaman);
        fieldKode.setText(kodeBuku);
        fieldJudul.setText(judul);
        fieldTglPinjam.setText(tglPinjam);
        fieldTglKembali.setText(tglKembali);
    }

    @FXML
    private void btnHome(ActionEvent event) { changePagePetugas(event, "v_Home"); }

    @FXML
    private void btnDataMasyarakat(ActionEvent event) { changePagePetugas(event, "v_DataMasyarakat"); }

    @FXML
    private void btnDonasi(ActionEvent event) { changePagePetugas(event, "v_DataDonasi"); }

    @FXML
    private void btnPengembalian(ActionEvent event) { changePagePetugas(event, "v_Pengembalian"); }

    @FXML
    private void btnPengiriman(ActionEvent event) { changePagePetugas(event, "v_Pengiriman"); }

    @FXML
    private void logout(ActionEvent event) { changePage(event, "v_Dashboard"); }

    private void showDataPeminjaman() throws SQLException {
        ObservableList<m_DataPeminjaman> list = m_DataPeminjaman.getDataPeminjaman();

        kolomIdPeminjam.setCellValueFactory(new PropertyValueFactory<m_DataPeminjaman, String>("idPeminjam"));
        kolomKodeBuku.setCellValueFactory(new PropertyValueFactory<m_DataPeminjaman, String>("kodeBuku"));
        kolomNamaPeminjam.setCellValueFactory(new PropertyValueFactory<m_DataPeminjaman, String>("nama_lengkap"));
        kolomJudul.setCellValueFactory(new PropertyValueFactory<m_DataPeminjaman, String>("judul"));
        kolomTglPinjam.setCellValueFactory(new PropertyValueFactory<m_DataPeminjaman, String>("tglPinjam"));
        kolomTglKembali.setCellValueFactory(new PropertyValueFactory<m_DataPeminjaman, String>("tglKembali"));
        kolomJumlah.setCellValueFactory(new PropertyValueFactory<m_DataPeminjaman, Integer>("jumlah"));

        tabelPeminjaman.setItems(list);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            showDataPeminjaman();
            ObservableList<String> data = FXCollections.observableArrayList("tepat waktu", "terlambat", "buku hilang");
            fieldStatus.setItems(data);
            fieldStatus.setValue("tepat waktu");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}

