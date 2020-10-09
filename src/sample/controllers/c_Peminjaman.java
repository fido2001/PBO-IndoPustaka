package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.models.m_Akun;
import sample.utils.Helpers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class c_Peminjaman extends Helpers implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            setDataPeminjam();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    private Label idAkun;

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
    private DatePicker fieldTglPinjam;

    @FXML
    private DatePicker fieldTglKembali;

    @FXML
    private TextField fieldNama;

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
    void PinjamClicked(ActionEvent event) {

    }

    @FXML
    private void btnHome(ActionEvent event) { changePageMasyarakat(event, "v_Home"); }

    @FXML
    private void btnAkun(ActionEvent event) { changePageMasyarakat(event, "v_AkunSaya"); }

    @FXML
    private void btnDonasi(ActionEvent event) { changePageMasyarakat(event, "v_Donasi"); }

    @FXML
    private void btnPeminjaman(ActionEvent event) { changePageMasyarakat(event, "v_Peminjaman"); }

    @FXML
    void logout(ActionEvent event) { changePage(event, "v_Dashboard"); }

    public void setDataPeminjam() throws SQLException {
        m_Akun.getDataAkun();
        String nama = m_Akun.getNama_lengkap();
        String email = m_Akun.getEmail();
        String no_hp = m_Akun.getNo_hp();
        String no_ktp = m_Akun.getNo_ktp();
        String alamat_lengkap = m_Akun.getAlamat_lengkap();
        String tanggal_lahir = m_Akun.getTanggal_lahir();
        String tempat_lahir = m_Akun.getTempat_lahir();
        fieldNama.setText(nama);
        fieldEmail.setText(email);
        fieldHp.setText(no_hp);
        fieldKtp.setText(no_ktp);
        fieldAlamat.setText(alamat_lengkap);
        fieldTglLahir.setText(tanggal_lahir);
        fieldTmpLahir.setText(tempat_lahir);
    }
}
