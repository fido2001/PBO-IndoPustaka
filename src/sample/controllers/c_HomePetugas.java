package sample.controllers;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import sample.models.m_DataBuku;
import sample.utils.Helpers;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;


public class c_HomePetugas extends Helpers implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            showDataBuku();
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
    private TextField fieldJumlah;

    @FXML
    private TextField filterField;

    @FXML
    private ComboBox<String> fieldKeterangan;

    private String kodeBuku = "";
    private String judul = "";
    private String kategori = "";
    private String penulis = "";
    private String keterangan = "";
    private Integer jumlah = null;

    @FXML
    private void btnTambahBuku(ActionEvent event) { changePagePetugas(event, "v_TambahBuku"); }

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

    private void showDataBuku() throws SQLException {
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
    private void HapusClicked(ActionEvent event) throws SQLException {
        if(kodeBuku.equals("")) {
            showAlert(Alert.AlertType.WARNING, "Peringatan", null, "Data Buku belum dipilih !!");
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Hapus data buku");
            alert.setHeaderText("Kode Buku\t\t: "+kodeBuku
                    +"\nJudul\t: "+judul);
            alert.setContentText("Anda yakin ingin menghapus data ini ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                m_DataBuku.deleteBuku(fieldKode.getText());
                if(m_DataBuku.getStatusDelete()) {
                    showAlert(Alert.AlertType.INFORMATION, "Sukses", null, "Data berhasil dihapus..");
                    showDataBuku();
                }
                else {
                    showAlert(Alert.AlertType.ERROR, "Error", null, "Data gagal dihapus..");
                }
            }
        }
    }

    @FXML
    private void SimpanClicked(ActionEvent event) throws SQLException{
        if(kodeBuku.equals("")) {
            showAlert(Alert.AlertType.WARNING, "Peringatan", null, "Data Buku belum dipilih !!");
        }
        else if(Integer.parseInt(fieldJumlah.getText()) < 0){
            showAlert(Alert.AlertType.WARNING, "Peringatan", null, "Data Buku tidak boleh kurang dari 0 !!");
        }
        else{
            String kode = fieldKode.getText();
            String judul = fieldJudul.getText();
            String penulis = fieldPenulis.getText();
            String kategori = fieldKategori.getText();
            String keterangan = fieldKeterangan.getValue();
            Integer jumlah = Integer.parseInt(fieldJumlah.getText());
            m_DataBuku.updateBuku(kode, judul, kategori, penulis, jumlah, keterangan);
            if(m_DataBuku.getStatusUpdate()){
                showAlert(Alert.AlertType.INFORMATION, "Sukses", null, "Data Buku Berhasil diedit");
                showDataBuku();
            }
            else{
                showAlert(Alert.AlertType.ERROR, "Error", null, "Data Buku gagal diedit..");
            }
        }
    }

    @FXML
    private void ambilKodeBuku(MouseEvent event) {
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
        fieldJumlah.setText(String.valueOf(jumlah));
        fieldKeterangan.setValue(keterangan);
    }
}
