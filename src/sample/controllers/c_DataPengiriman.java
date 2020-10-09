package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import sample.models.m_DataPeminjaman;
import sample.models.m_DataPengiriman;
import sample.utils.Helpers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class c_DataPengiriman extends Helpers implements Initializable {

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
    private DatePicker fieldTglKirim;

    @FXML
    private Label fieldId;

    @FXML
    private ComboBox<String> fieldStatus;

    @FXML
    private TextField fieldOngkir;

    private String idPeminjaman="", kodeBuku="", judul="",tglPinjam="",tglKembali="";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            showDataPeminjaman();
            ObservableList<String> data = FXCollections.observableArrayList("belum", "sudah");
            fieldStatus.setItems(data);
            fieldStatus.setValue("belum");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    void SimpanClicked(ActionEvent event) throws SQLException {
        if(idPeminjaman.equals("")){
            showAlert(Alert.AlertType.WARNING, "Peringatan", null, "Data Peminjaman belum dipilih !!");
        }
        else if(fieldStatus.getValue().equals("")||fieldTglKirim.getValue().equals("")||fieldOngkir.getText().equals("")) {
            showAlert(Alert.AlertType.WARNING, "Peringatan", null, "Harap lengkapi data pengiriman !!");
        }
        else {
            String id = fieldId.getText();
            String status = fieldStatus.getValue();
            String tanggal = fieldTglKirim.getValue().toString();
            Integer ongkir = Integer.parseInt(fieldOngkir.getText());
            m_DataPengiriman.insertPengiriman(id, status, tanggal, ongkir);
            if(m_DataPengiriman.getStatusInsert()==true){
                showAlert(Alert.AlertType.INFORMATION, "Sukses", null, "Data Pengiriman Berhasil Dimasukkan");
            }
            else{
                showAlert(Alert.AlertType.ERROR, "Error", null, "Data Pengiriman gagal dimasukkan..");
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

    private void showDataPeminjaman() throws SQLException {
        ObservableList<m_DataPeminjaman> list = m_DataPeminjaman.getDataPeminjamanKirim();

        kolomIdPeminjam.setCellValueFactory(new PropertyValueFactory<m_DataPeminjaman, String>("idPeminjam"));
        kolomKodeBuku.setCellValueFactory(new PropertyValueFactory<m_DataPeminjaman, String>("kodeBuku"));
        kolomNamaPeminjam.setCellValueFactory(new PropertyValueFactory<m_DataPeminjaman, String>("nama_lengkap"));
        kolomJudul.setCellValueFactory(new PropertyValueFactory<m_DataPeminjaman, String>("judul"));
        kolomTglPinjam.setCellValueFactory(new PropertyValueFactory<m_DataPeminjaman, String>("tglPinjam"));
        kolomTglKembali.setCellValueFactory(new PropertyValueFactory<m_DataPeminjaman, String>("tglKembali"));
        kolomJumlah.setCellValueFactory(new PropertyValueFactory<m_DataPeminjaman, Integer>("jumlah"));

        tabelPeminjaman.setItems(list);
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

}
