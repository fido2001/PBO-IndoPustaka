package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import sample.models.m_Donasi;
import sample.utils.Helpers;

import java.net.URL;
import java.util.ResourceBundle;

public class c_Donasi extends Helpers implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clear();
    }

    @FXML
    private TextField penulis, judul, jumlah;

    @FXML
    private DatePicker tanggal_donasi;

    @FXML
    private Label idAkun;

    private void clear() {
        penulis.setText("");
        jumlah.setText("");
        tanggal_donasi.setValue(null);
        jumlah.setText("");
        judul.setText("");
    }

    @FXML
    void DonasiClicked(ActionEvent event) {
        if(judul.getText().equals("") || penulis.getText().equals("") || tanggal_donasi.getValue().equals("") || jumlah.getText().equals("")) {
            showAlert(Alert.AlertType.WARNING, "Peringatan", null, "Silakan masukkan data buku dengan lengkap !");
        } else{
            m_Donasi.insertDonasi(judul.getText(), penulis.getText(), Integer.parseInt(jumlah.getText()), tanggal_donasi.getValue().toString());
            if (m_Donasi.getStatusInsert()==true) {
                showAlert(Alert.AlertType.INFORMATION, "Sukses", null, "Data Donasi Buku Berhasil Ditambahkan...");
                clear();
            }else {
                showAlert(Alert.AlertType.ERROR, "Error", null, "Gagal Menambah Data Donasi!!");
            }
        }
    }

    @FXML
    private void btnAkun(ActionEvent event) { changePageMasyarakat(event, "v_AkunSaya"); }

    @FXML
    private void btnDonasi(ActionEvent event) { changePageMasyarakat(event, "v_Donasi"); }

    @FXML
    private void btnHome(ActionEvent event) { changePageMasyarakat(event, "v_Home"); }

    @FXML
    private void btnPeminjaman(ActionEvent event) { changePageMasyarakat(event, "v_RekapPeminjaman"); }

    @FXML
    private void btnPenerimaan(ActionEvent event) { changePageMasyarakat(event, "v_Penerimaan"); }

    @FXML
    private void logout(ActionEvent event) { changePage(event, "v_Dashboard"); }

}
