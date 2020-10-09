package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.models.m_Akun;
import sample.utils.Helpers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class c_AkunSaya extends Helpers implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            setData();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    m_Akun model = new m_Akun();

    @FXML
    private TextField nama_lengkap, no_hp, tempat_lahir, username, no_ktp, email;

    @FXML
    private PasswordField password;

    @FXML
    private DatePicker tanggal_lahir;

    @FXML
    private TextArea alamat_lengkap;

    @FXML
    private Label idAkun;

    @FXML
    private TextField fieldNama;

    @FXML
    private TextField fieldUsrnm;

    @FXML
    private TextField fieldHp;

    @FXML
    private TextField fieldTglLahir;

    @FXML
    private TextField fieldTmpLahir;

    @FXML
    private TextField fieldKtp;

    @FXML
    private TextField fieldEmail;

    @FXML
    private TextField fieldAlamat;

    @FXML
    private void btnHome(ActionEvent event) { changePageMasyarakat(event, "v_Home"); }

    @FXML
    private void btnAkun(ActionEvent event) { changePageMasyarakat(event, "v_AkunSaya"); }

    @FXML
    private void btnDonasi(ActionEvent event) { changePageMasyarakat(event, "v_Donasi"); }

    @FXML
    private void btnPeminjaman(ActionEvent event) { changePageMasyarakat(event, "v_RekapPeminjaman"); }

    @FXML
    private void btnPenerimaan(ActionEvent event) { changePageMasyarakat(event, "v_Penerimaan"); }

    public void setData() throws SQLException {
        m_Akun.getDataAkun();
        String nama = m_Akun.getNama_lengkap();
        String username = m_Akun.getUsername();
        String email = m_Akun.getEmail();
        String no_hp = m_Akun.getNo_hp();
        String no_ktp = m_Akun.getNo_ktp();
        String alamat_lengkap = m_Akun.getAlamat_lengkap();
        String tanggal_lahir = m_Akun.getTanggal_lahir();
        String tempat_lahir = m_Akun.getTempat_lahir();
        fieldNama.setText(nama);
        fieldUsrnm.setText(username);
        fieldEmail.setText(email);
        fieldHp.setText(no_hp);
        fieldKtp.setText(no_ktp);
        fieldAlamat.setText(alamat_lengkap);
        fieldTglLahir.setText(tanggal_lahir);
        fieldTmpLahir.setText(tempat_lahir);
    }

    @FXML
    void EditClicked(ActionEvent event) {
        if(nama_lengkap.getText().equals("")||password.getText().equals("")||tanggal_lahir.getValue().equals("")||no_hp.getText().equals("")||tempat_lahir.getText().equals("")||alamat_lengkap.getText().equals("")||username.getText().equals("")||no_ktp.getText().equals("")||email.getText().equals("")) {
            showAlert(Alert.AlertType.WARNING, "Peringatan", null, "Lengkapi data terlebih dahulu !!");
        }else {
            String idText=idAkun.getText();
            String usrnmText=username.getText();
            String namaText=nama_lengkap.getText();
            String passText=stringToMD5(password.getText());
            String almText=alamat_lengkap.getText();
            String tglLhrText=tanggal_lahir.getValue().toString();
            String tmpLhrText=tempat_lahir.getText();
            String hpText=no_hp.getText();
            String ktpText=no_ktp.getText();
            String emailText=email.getText();
            model.updateAkun(usrnmText, passText, namaText,  almText, tmpLhrText, tglLhrText, ktpText,hpText, emailText);
            if(model.getStatusUpdate()==true){
                showAlert(Alert.AlertType.INFORMATION, "Sukses", null, "Data Akun Berhasil Diubah");
            }
            else{
                showAlert(Alert.AlertType.ERROR, "Error", null, "Data Akun gagal diubah..");
            }
        }
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
