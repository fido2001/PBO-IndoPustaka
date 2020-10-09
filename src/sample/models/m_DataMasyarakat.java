package sample.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.utils.ConnectDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class m_DataMasyarakat {
    private String nama;
    private String alamat;
    private String no_ktp;
    private String no_hp;
    private String email;

    public String getNama() {
        return nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getNo_ktp() {
        return no_ktp;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public String getEmail() {
        return email;
    }

    public m_DataMasyarakat(String nama, String alamat, String no_ktp, String no_hp, String email) {
        this.nama = nama;
        this.alamat = alamat;
        this.no_ktp = no_ktp;
        this.no_hp = no_hp;
        this.email = email;
    }

    public static ObservableList<m_DataMasyarakat> getDataMasyarakat() throws SQLException {
        ObservableList<m_DataMasyarakat> dataMasyarakat = FXCollections.observableArrayList();
        Connection conn = new ConnectDB().connect();
        Statement stat = conn.createStatement();
        String level = "masyarakat";
        ResultSet rs = stat.executeQuery("SELECT * FROM data_akun WHERE level ='"+level+"'");
        m_DataMasyarakat masyarakat;
        while(rs.next()){
            masyarakat = new m_DataMasyarakat(rs.getString("nama_lengkap"), rs.getString("alamat_lengkap"), rs.getString("no_ktp"), rs.getString("no_hp"), rs.getString("email"));
            dataMasyarakat.add(masyarakat);
        }
        return dataMasyarakat;
    }
}
