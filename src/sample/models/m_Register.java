package sample.models;

import sample.utils.ConnectDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class m_Register {
    Connection con = new ConnectDB().connect();
    private int rowUsername=0;
    private boolean statusInsert=false;
    public void cekUsername(String username){
        try {
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery("select username from data_akun where username='"+username+"'");
            while(rs.next()){
                rowUsername=rs.getRow();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void insertAkun(String username,  String password, String nama_lengkap, String alamat_lengkap, String tempat_lahir, String tanggal_lahir, String no_ktp, String no_hp, String email){
        try {
            Statement stat = con.createStatement();
            stat.executeUpdate("insert into data_akun values (null,'"+username+"','"
                    +password+"','"+nama_lengkap+"','"+alamat_lengkap+"','"+tempat_lahir+"','"+tanggal_lahir+"','"
                    +no_ktp+"','"+email+"','"+no_hp+"','masyarakat')");
            statusInsert=true;
        } catch (Exception e) {
            statusInsert=false;
            System.out.println(e);
        }
    }

    public int getRowUsername(){
        return rowUsername;
    }

    public boolean getStatusInsert(){
        return statusInsert;
    }
}
