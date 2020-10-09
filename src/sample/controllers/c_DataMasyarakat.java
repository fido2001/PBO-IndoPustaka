package sample.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.models.m_DataMasyarakat;
import sample.utils.Helpers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class c_DataMasyarakat extends Helpers implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            showDataMasyarakat();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    private TableView<m_DataMasyarakat> tabelMasyarakat;

    @FXML
    private TableColumn<m_DataMasyarakat, String> kolomNama;

    @FXML
    private TableColumn<m_DataMasyarakat, String> kolomAlamat;

    @FXML
    private TableColumn<m_DataMasyarakat, String> kolomKTP;

    @FXML
    private TableColumn<m_DataMasyarakat, String> kolomHP;

    @FXML
    private TableColumn<m_DataMasyarakat, String> kolomEmail;

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

    private void showDataMasyarakat() throws SQLException {
        ObservableList<m_DataMasyarakat> list = m_DataMasyarakat.getDataMasyarakat();

        kolomNama.setCellValueFactory(new PropertyValueFactory<m_DataMasyarakat, String>("nama"));
        kolomAlamat.setCellValueFactory(new PropertyValueFactory<m_DataMasyarakat, String>("alamat"));
        kolomKTP.setCellValueFactory(new PropertyValueFactory<m_DataMasyarakat, String>("no_ktp"));
        kolomHP.setCellValueFactory(new PropertyValueFactory<m_DataMasyarakat, String>("no_hp"));
        kolomEmail.setCellValueFactory(new PropertyValueFactory<m_DataMasyarakat, String>("email"));

        tabelMasyarakat.setItems(list);
    }

    @FXML
    private void logout(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../views/v_Dashboard.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
