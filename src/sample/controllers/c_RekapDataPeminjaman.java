package sample.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.models.m_DataPeminjaman;
import sample.utils.Helpers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class c_RekapDataPeminjaman extends Helpers implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            showDataPeminjaman();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    private AnchorPane blur;

    @FXML
    private TableView<m_DataPeminjaman> tabelPeminjaman;

    @FXML
    private TableColumn<m_DataPeminjaman, String> kolomKodeBuku;

    @FXML
    private TableColumn<m_DataPeminjaman, String> kolomIdPeminjam;

    @FXML
    private TableColumn<m_DataPeminjaman, String> kolomNamaPeminjam;

    @FXML
    private TableColumn<m_DataPeminjaman, String> kolomJudul;

    @FXML
    private TableColumn<m_DataPeminjaman, String> kolomAlamat;

    @FXML
    private TableColumn<m_DataPeminjaman, String> kolomTglPinjam;

    @FXML
    private TableColumn<m_DataPeminjaman, String> kolomTglKembali;

    @FXML
    private TableColumn<m_DataPeminjaman, Integer> kolomJumlah;

    @FXML
    void ambilIdPeminjaman(MouseEvent event) {
    }

    private void showDataPeminjaman() throws SQLException {
        ObservableList<m_DataPeminjaman> list = m_DataPeminjaman.getDataPeminjamanByIdd();

        kolomIdPeminjam.setCellValueFactory(new PropertyValueFactory<m_DataPeminjaman, String>("idPeminjam"));
        kolomKodeBuku.setCellValueFactory(new PropertyValueFactory<m_DataPeminjaman, String>("kodeBuku"));
        kolomNamaPeminjam.setCellValueFactory(new PropertyValueFactory<m_DataPeminjaman, String>("nama_lengkap"));
        kolomJudul.setCellValueFactory(new PropertyValueFactory<m_DataPeminjaman, String>("judul"));
        kolomAlamat.setCellValueFactory(new PropertyValueFactory<m_DataPeminjaman, String>("alamat"));
        kolomTglPinjam.setCellValueFactory(new PropertyValueFactory<m_DataPeminjaman, String>("tglPinjam"));
        kolomTglKembali.setCellValueFactory(new PropertyValueFactory<m_DataPeminjaman, String>("tglKembali"));
        kolomJumlah.setCellValueFactory(new PropertyValueFactory<m_DataPeminjaman, Integer>("jumlah"));

        tabelPeminjaman.setItems(list);
    }

    @FXML
    private void btnHome(ActionEvent event) { changePageMasyarakat(event, "v_Home"); }

    @FXML
    private void btnAkun(ActionEvent event) { changePageMasyarakat(event, "v_AkunSaya"); }

    @FXML
    private void btnPenerimaan(ActionEvent event) { changePageMasyarakat(event, "v_Penerimaan"); }

    @FXML
    private void btnDonasi(ActionEvent event) { changePageMasyarakat(event, "v_Donasi"); }

    @FXML
    private void btnPeminjaman(ActionEvent event) { changePageMasyarakat(event, "v_RekapPeminjaman"); }

    @FXML
    void logout(ActionEvent event) {

    }

}
