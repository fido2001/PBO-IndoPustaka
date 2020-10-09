package sample.models;

import sample.utils.ConnectDB;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class m_DataPengiriman {
    private static boolean statusInsert=false;

    public static void insertPengiriman(String idPeminjaman, String statusPengiriman, String tanggalKirim, Integer ongkir ) throws SQLException {
        Connection conn = new ConnectDB().connect();
        Statement stat = conn.createStatement();
        stat.executeUpdate("INSERT INTO data_pengiriman VALUES(null, '"+idPeminjaman+"','"+statusPengiriman+"','"+tanggalKirim+"',null,'belum','"+ongkir+"')");
        statusInsert=true;
    }

    public static void updatePengiriman(String id, String tglTerima) throws SQLException {
        Connection conn = new ConnectDB().connect();
        Statement stat = conn.createStatement();
        stat.executeUpdate("UPDATE data_pengiriman SET"+ " tanggalTerima='"+tglTerima+"',"
                + " konfirmasiPenerimaan = 'sudah'"
                + " WHERE idPeminjaman='"+id+"'");
        statusInsert=true;
    }

    public static boolean getStatusInsert(){
        return statusInsert;
    }
}
