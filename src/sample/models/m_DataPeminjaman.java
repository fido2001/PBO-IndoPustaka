package sample.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.utils.ConnectDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class m_DataPeminjaman {
    private String idPeminjam;
    private String kodeBuku;
    private String nama_lengkap;
    private String judul;
    private String alamat;
    private String tglPinjam;
    private String tglKembali;
    private Integer jumlah;
    private static boolean statusInsert=false;

    public String getIdPeminjam() {
        return idPeminjam;
    }

    public String getKodeBuku() {
        return  kodeBuku;
    }

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public String getAlamat() { return alamat; }

    public String getJudul() {
        return judul;
    }

    public String getTglPinjam() {
        return tglPinjam;
    }

    public String getTglKembali() {
        return tglKembali;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public m_DataPeminjaman(String idPeminjam, String kodeBuku, String nama_lengkap, String alamat, String judul, String tglPinjam, String tglKembali, Integer jumlah) {
        this.idPeminjam = idPeminjam;
        this.kodeBuku = kodeBuku;
        this.nama_lengkap = nama_lengkap;
        this.alamat = alamat;
        this.judul = judul;
        this.tglPinjam = tglPinjam;
        this.tglKembali = tglKembali;
        this.jumlah = jumlah;
    }

    public static ObservableList<m_DataPeminjaman> getDataPeminjaman() throws SQLException {
        ObservableList<m_DataPeminjaman> dataPeminjaman = FXCollections.observableArrayList();
        Connection conn = new ConnectDB().connect();
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery("SELECT dp.idPeminjaman, dp.kodeBuku, dp.alamatPeminjaman, da.nama_lengkap, db.judul, dp.tanggalPinjam, dp.tanggalKembali, dp.jumlah FROM data_peminjaman dp JOIN data_akun da ON da.idAkun=dp.idAkun JOIN data_buku db ON db.kodeBuku=dp.kodeBuku");
        m_DataPeminjaman peminjaman;
        while(rs.next()){
            peminjaman = new m_DataPeminjaman(rs.getString("idPeminjaman") , rs.getString("kodeBuku"),rs.getString("nama_lengkap"), rs.getString("alamatPeminjaman"),rs.getString("judul"), rs.getString("tanggalPinjam"), rs.getString("tanggalKembali"), rs.getInt("jumlah"));
            dataPeminjaman.add(peminjaman);
        }
        return dataPeminjaman;
    }

    public static ObservableList<m_DataPeminjaman> getDataPeminjamanKirim() throws SQLException {
        ObservableList<m_DataPeminjaman> dataPeminjaman = FXCollections.observableArrayList();
        Connection conn = new ConnectDB().connect();
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery("SELECT dp.idPeminjaman, dp.kodeBuku, dp.alamatPeminjaman,da.nama_lengkap, db.judul, dp.tanggalPinjam, dp.tanggalKembali, dp.jumlah FROM data_peminjaman dp JOIN data_akun da ON da.idAkun=dp.idAkun JOIN data_buku db ON db.kodeBuku=dp.kodeBuku WHERE caraPengambilan='Antar Gojek'");
        m_DataPeminjaman peminjaman;
        while(rs.next()){
            peminjaman = new m_DataPeminjaman(rs.getString("idPeminjaman") , rs.getString("kodeBuku"),rs.getString("nama_lengkap"), rs.getString("alamatPeminjaman"),rs.getString("judul"), rs.getString("tanggalPinjam"), rs.getString("tanggalKembali"), rs.getInt("jumlah"));
            dataPeminjaman.add(peminjaman);
        }
        return dataPeminjaman;
    }

    public static ObservableList<m_DataPeminjaman> getDataPeminjamanById() throws SQLException {
        ObservableList<m_DataPeminjaman> dataPeminjaman = FXCollections.observableArrayList();
        Connection conn = new ConnectDB().connect();
        Statement stat = conn.createStatement();
        String id = m_Login.getIdAkun();
        ResultSet rs = stat.executeQuery("SELECT dp.idPeminjaman, dp.kodeBuku, dp.alamatPeminjaman, da.nama_lengkap, db.judul, dp.tanggalPinjam, dp.tanggalKembali, dp.jumlah FROM data_peminjaman dp JOIN data_akun da ON da.idAkun=dp.idAkun JOIN data_buku db ON db.kodeBuku=dp.kodeBuku" + " WHERE dp.idAkun='"+id+"' AND caraPengambilan='Antar Gojek'");
        m_DataPeminjaman peminjaman;
        while(rs.next()){
            peminjaman = new m_DataPeminjaman(rs.getString("idPeminjaman") , rs.getString("kodeBuku"),rs.getString("nama_lengkap"), rs.getString("alamatPeminjaman"),rs.getString("judul"), rs.getString("tanggalPinjam"), rs.getString("tanggalKembali"), rs.getInt("jumlah"));
            dataPeminjaman.add(peminjaman);
        }
        return dataPeminjaman;
    }

    public static ObservableList<m_DataPeminjaman> getDataPeminjamanByIdd() throws SQLException {
        ObservableList<m_DataPeminjaman> dataPeminjaman = FXCollections.observableArrayList();
        Connection conn = new ConnectDB().connect();
        Statement stat = conn.createStatement();
        String id = m_Login.getIdAkun();
        ResultSet rs = stat.executeQuery("SELECT dp.idPeminjaman, dp.kodeBuku, dp.alamatPeminjaman, da.nama_lengkap, db.judul, dp.tanggalPinjam, dp.tanggalKembali, dp.jumlah FROM data_peminjaman dp JOIN data_akun da ON da.idAkun=dp.idAkun JOIN data_buku db ON db.kodeBuku=dp.kodeBuku" + " WHERE dp.idAkun='"+id+"'");
        m_DataPeminjaman peminjaman;
        while(rs.next()){
            peminjaman = new m_DataPeminjaman(rs.getString("idPeminjaman") , rs.getString("kodeBuku"),rs.getString("nama_lengkap"), rs.getString("alamatPeminjaman"),rs.getString("judul"), rs.getString("tanggalPinjam"), rs.getString("tanggalKembali"), rs.getInt("jumlah"));
            dataPeminjaman.add(peminjaman);
        }
        return dataPeminjaman;
    }

    public static void insertPengembalian(String idPeminjaman, String kodeBuku, String statusPengembalian, String tanggalPengembalian, String caraPengambalian, Integer denda ) throws SQLException {
        Connection conn = new ConnectDB().connect();
        Statement stat = conn.createStatement();
        stat.executeUpdate("INSERT INTO data_pengembalian VALUES(null, '"+idPeminjaman+"','"+kodeBuku+"','"+statusPengembalian+"','"+tanggalPengembalian+"','"+caraPengambalian+"','"+denda+"')");
        statusInsert=true;
    }

    public static boolean getStatusInsert(){
        return statusInsert;
    }
}
