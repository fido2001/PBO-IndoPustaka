package sample.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.utils.ConnectDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class m_DataBuku {
    private String kodeBuku;
    private String judul;
    private String kategori;
    private String penulis;
    private Integer jumlah;
    private String keterangan;
    private static int rowKodeBuku=0;

    public String getKodeBuku() {
        return kodeBuku;
    }

    public String getJudul() {
        return judul;
    }

    public String getKategori() {
        return kategori;
    }

    public String getPenulis() {
        return penulis;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public String getKeterangan() {
        return keterangan;
    }

    private static boolean statusInsert=false, statusUpdate=false, statusDelete=false;

    public m_DataBuku(String kodeBuku, String judul, String kategori, String penulis, Integer jumlah, String keterangan) {
        this.kodeBuku = kodeBuku;
        this.judul = judul;
        this.kategori = kategori;
        this.penulis = penulis;
        this.jumlah = jumlah;
        this.keterangan = keterangan;
    }

    public static void cekKodeBuku(String kodeBuku){
        try {
            Connection conn = new ConnectDB().connect();
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("select kodeBuku from data_buku where kodeBuku='"+kodeBuku+"'");
            while(rs.next()){
                rowKodeBuku=rs.getRow();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static ObservableList<m_DataBuku> getDataBuku() throws SQLException {
        ObservableList<m_DataBuku> dataBuku = FXCollections.observableArrayList();
        Connection conn = new ConnectDB().connect();
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery("SELECT * FROM data_buku");
        m_DataBuku buku;
        while(rs.next()){
            buku = new m_DataBuku(rs.getString("kodeBuku"), rs.getString("judul"), rs.getString("kategori"), rs.getString("penulis"), rs.getInt("jumlah"), rs.getString("keterangan"));
            dataBuku.add(buku);
        }
        return dataBuku;
    }

    public static void insertBuku(String kodeBuku, String judul, String kategori, String penulis, Integer jumlah, String keterangan) throws SQLException {
        Connection conn = new ConnectDB().connect();
        Statement stat = conn.createStatement();
        stat.executeUpdate("INSERT INTO data_buku VALUES('"+kodeBuku+"','"+judul+"','"+kategori+"','"+penulis+"','"+jumlah+"','"+keterangan+"')");
        statusInsert=true;
    }

    public static void updateBuku(String kodeBuku, String judul, String kategori, String penulis, Integer jumlah, String keterangan) throws SQLException {
        Connection conn = new ConnectDB().connect();
        Statement stat = conn.createStatement();
        stat.executeUpdate("UPDATE data_buku SET"
                + " kodeBuku='"+kodeBuku+"',"
                + " judul='"+judul+"',"
                + " kategori='"+kategori+"',"
                + " penulis='"+penulis+"',"
                + " jumlah='"+jumlah+"',"
                + " keterangan='"+keterangan+"'"
                + " WHERE kodeBuku='"+kodeBuku+"'");
        statusUpdate=true;
    }

    public static void deleteBuku(String kodeBuku) throws SQLException {
        Connection conn = new ConnectDB().connect();
        Statement stat = conn.createStatement();
        stat.executeUpdate("delete from data_buku where kodeBuku='"+kodeBuku+"'");
        statusDelete=true;
    }

    public static void insertPeminjaman(String kodeBuku, String alamatPeminjaman, String tanggalPinjam, String tanggalKembali, String caraPengambilan, Integer jumlah ) throws SQLException {
        String idAkun = m_Login.getIdAkun();
        Connection conn = new ConnectDB().connect();
        Statement stat = conn.createStatement();
        stat.executeUpdate("INSERT INTO data_peminjaman VALUES(null, '"+idAkun+"','"+kodeBuku+"','"+alamatPeminjaman+"','"+tanggalPinjam+"','"+tanggalKembali+"','menunggu konfirmasi','"+caraPengambilan+"', '"+jumlah+"')");
        statusInsert=true;
    }

    public static boolean getStatusInsert(){
        return statusInsert;
    }

    public static boolean getStatusUpdate() { return statusUpdate; }

    public static boolean getStatusDelete() { return statusDelete; }

    public static int getRowKodeBuku(){
        return rowKodeBuku;
    }

}
