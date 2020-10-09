package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import sample.models.m_Register;
import sample.utils.Helpers;

import java.net.URL;
import java.util.ResourceBundle;

public class c_Register extends Helpers implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clear();
    }

    @FXML
    private void btnBack(ActionEvent event) {
        changePage(event, "v_Dashboard");
    }

    m_Register model = new m_Register();

    @FXML
    private TextField nama_lengkap;

    @FXML
    private PasswordField password;

    @FXML
    private TextField no_hp;

    @FXML
    private DatePicker tanggal_lahir;

    @FXML
    private TextField tempat_lahir;

    @FXML
    private TextArea alamat_lengkap;

    @FXML
    private TextField username;

    @FXML
    private TextField no_ktp;

    @FXML
    private TextField email;

    private void clear(){
        username.setText("");
        nama_lengkap.setText("");
        email.setText("");
        password.setText("");
        no_ktp.setText("");
        no_hp.setText("");
        alamat_lengkap.setText("");
        tanggal_lahir.setAccessibleText("");
        tempat_lahir.setText("");
    }

    @FXML
    private void RegisterClicked(ActionEvent event) {
        if(nama_lengkap.getText().equals("")||password.getText().equals("")||tanggal_lahir.getValue().equals("")||no_hp.getText().equals("")||tempat_lahir.getText().equals("")||alamat_lengkap.getText().equals("")||username.getText().equals("")||no_ktp.getText().equals("")||email.getText().equals("")){
            showAlert(Alert.AlertType.WARNING, "Peringatan", null, "Silahkan masukkan data dengan lengkap !");
        } else {
            model.cekUsername(username.getText());
            if (model.getRowUsername()>0) {
                showAlert(Alert.AlertType.WARNING, "Peringatan", null, "Username sudah terdaftar..\nSilahkan gunakan username yang lain..");
                username.setText("");
                username.requestFocus();
            }
            else {
                model.insertAkun(username.getText(), stringToMD5(password.getText()), nama_lengkap.getText(), alamat_lengkap.getText(), tempat_lahir.getText(), tanggal_lahir.getValue().toString(), no_ktp.getText(), no_hp.getText(), email.getText());
                if (model.getStatusInsert()==true) {
                    showAlert(Alert.AlertType.INFORMATION, "Sukses", null, "Silahkan Login menggunakan Username dan Password");
                    clear();
                }
                else {
                    showAlert(Alert.AlertType.ERROR, "Error", null, "Gagal Melakukan Pendaftaran!");
                }
            }
        }
    }
}
