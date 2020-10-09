package sample.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.utils.ConnectDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class m_Donasi {
    private String nama_lengkap;
    private String judul;
    private String penulis;
    private Integer jumlah;
    private String tanggal_donasi;

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public String getJudul() {
        return judul;
    }

    public String getPenulis() {
        return penulis;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public String getTanggal_donasi() {
        return tanggal_donasi;
    }

    public m_Donasi(String nama_lengkap, String judul, String penulis, Integer jumlah, String tanggal_donasi) {
        this.nama_lengkap = nama_lengkap;
        this.judul = judul;
        this.penulis = penulis;
        this.jumlah = jumlah;
        this.tanggal_donasi = tanggal_donasi;
    }

    private static boolean statusInsert=false;

    public static void insertDonasi(String judul, String penulis, int jumlah, String tanggal_donasi){
        try {
            Connection con = new ConnectDB().connect();
            Statement stat = con.createStatement();
            String idAkun = m_Login.getIdAkun();
            stat.executeUpdate("insert into data_donasi values (null,'"+idAkun+"','"
                    +judul+"','"+penulis+"','"+jumlah+"','"+tanggal_donasi+"')");
            statusInsert=true;
        } catch (Exception e) {
            statusInsert=false;
            System.out.println(e);
        }
    }

    public static ObservableList<m_Donasi> getDonasi() throws SQLException {
        ObservableList<m_Donasi> dataDonasi = FXCollections.observableArrayList();
        Connection conn = new ConnectDB().connect();
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery("SELECT da.nama_lengkap, dd.judul, dd.penulis, dd.jumlah, dd.tanggal_donasi FROM data_donasi dd JOIN data_akun da ON da.idAkun=dd.idAkun");
        m_Donasi donasi;
        while(rs.next()){
            donasi = new m_Donasi(rs.getString("nama_lengkap"), rs.getString("judul"), rs.getString("penulis"), rs.getInt("jumlah"), rs.getString("tanggal_donasi"));
            dataDonasi.add(donasi);
        }
        return dataDonasi;
    }

    public static boolean getStatusInsert(){
        return statusInsert;
    }
}
