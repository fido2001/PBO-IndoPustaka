package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import sample.models.m_DataBuku;
import sample.utils.Helpers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class c_TambahBuku extends Helpers implements Initializable {

    @FXML
    private TextField kodeBuku;

    @FXML
    private TextField kategori;

    @FXML
    private TextField penulis;

    @FXML
    private TextField judul;

    @FXML
    private TextField jumlah;

    @FXML
    private ComboBox<String> keterangan;

    @FXML
    void TambahClicked(ActionEvent event) throws SQLException {
        if(kodeBuku.getText().equals("")||kategori.getText().equals("")||penulis.getText().equals("")||judul.getText().equals("")||jumlah.getText().equals("")||keterangan.getItems().equals("")) {
            showAlert(Alert.AlertType.WARNING, "Peringatan", null, "Silakan masukkan data buku dengan lengkap !");
        }
        else {
            m_DataBuku.cekKodeBuku(kodeBuku.getText());
            if(m_DataBuku.getRowKodeBuku()>0){
                showAlert(Alert.AlertType.WARNING, "Peringatan", null, "Kode Buku sudah terdaftar..\nSilahkan gunakan Kode Buku yang lain..");
            }
            else {
                m_DataBuku.insertBuku(kodeBuku.getText(), judul.getText(), kategori.getText(), penulis.getText(), Integer.parseInt(jumlah.getText()), keterangan.getValue());
                if (m_DataBuku.getStatusInsert()==true){
                    showAlert(Alert.AlertType.INFORMATION, "Sukses", null, "Data Buku Berhasil Ditambahkan...");
                    changePagePetugas(event, "v_Home");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", null, "Gagal Menambah Data Buku!!");
                }
            }
        }
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
    void logout(ActionEvent event) { changePage(event, "v_Dashboard"); }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> data = FXCollections.observableArrayList("tersedia", "kosong");
        keterangan.setItems(data);
        keterangan.setValue("tersedia");
    }

}
