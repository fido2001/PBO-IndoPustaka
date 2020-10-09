package sample.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.models.m_Donasi;
import sample.utils.Helpers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class c_DataDonasi extends Helpers implements Initializable {
    @FXML
    private TableView<m_Donasi> tabelDonasi;

    @FXML
    private TableColumn<m_Donasi, String> kolomDonatur;

    @FXML
    private TableColumn<m_Donasi, String> kolomJudul;

    @FXML
    private TableColumn<m_Donasi, String> kolomPenulis;

    @FXML
    private TableColumn<m_Donasi, Integer> kolomJumlah;

    @FXML
    private TableColumn<m_Donasi, String> kolomTanggal;

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

    private void showDonasi() throws SQLException {
        ObservableList<m_Donasi> list = m_Donasi.getDonasi();

        kolomDonatur.setCellValueFactory(new PropertyValueFactory<m_Donasi, String>("nama_lengkap"));
        kolomJudul.setCellValueFactory(new PropertyValueFactory<m_Donasi, String>("judul"));
        kolomPenulis.setCellValueFactory(new PropertyValueFactory<m_Donasi, String>("penulis"));
        kolomJumlah.setCellValueFactory(new PropertyValueFactory<m_Donasi, Integer>("jumlah"));
        kolomTanggal.setCellValueFactory(new PropertyValueFactory<m_Donasi, String>("tanggal_donasi"));

        tabelDonasi.setItems(list);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            showDonasi();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
