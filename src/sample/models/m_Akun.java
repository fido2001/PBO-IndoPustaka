package sample.models;

import javafx.fxml.Initializable;
import sample.utils.ConnectDB;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class m_Akun implements Initializable {
    private String idAkun;
    private static String username;
    private static String nama_lengkap;
    private static String alamat_lengkap;
    private static String tempat_lahir;
    private static String tanggal_lahir;
    private static String email;
    private static String no_hp;
    private static String no_ktp;
    Connection con = new ConnectDB().connect();
    private boolean statusUpdate=false;

    public static void getDataAkun() throws SQLException {
        Connection conn = new ConnectDB().connect();
        Statement stat = conn.createStatement();
        String id = m_Login.getIdAkun();
        ResultSet rs = stat.executeQuery("SELECT * FROM data_akun WHERE idAkun ='"+id+"'");
        while(rs.next()){
            nama_lengkap = rs.getString("nama_lengkap");
            username = rs.getString("username");
            alamat_lengkap = rs.getString("alamat_lengkap");
            tempat_lahir = rs.getString("tempat_lahir");
            tanggal_lahir = rs.getString("tanggal_lahir");
            no_ktp = rs.getString("no_ktp");
            no_hp = rs.getString("no_hp");
            email = rs.getString("email");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            getDataAkun();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateAkun(String username, String password, String nama_lengkap, String alamat_lengkap, String tempat_lahir, String tanggal_lahir, String email, String no_hp, String no_ktp){
        String id = m_Login.getIdAkun();
        try {
            Statement stat = con.createStatement();
            stat.executeUpdate("update data_akun set"
                    + " username='"+username+"',"
                    + " password='"+password+"',"
                    + " nama_lengkap='"+nama_lengkap+"',"
                    + " alamat_lengkap='"+alamat_lengkap+"',"
                    + " tempat_lahir='"+tempat_lahir+"',"
                    + " tanggal_lahir='"+tanggal_lahir+"',"
                    + " no_ktp='"+no_ktp+"',"
                    + " no_hp='"+no_hp+"',"
                    + " email='"+email+"'"
                    + " where idAkun='"+id+"'");
            statusUpdate=true;
        } catch (Exception e) {
            System.out.println(e);
            statusUpdate=false;
        }
    }

    public boolean getStatusUpdate(){
        return statusUpdate;
    }

    public static String getNama_lengkap() { return nama_lengkap; }

    public static String getUsername() { return username; }

    public static String getEmail() { return email; }

    public static String getNo_hp() { return no_hp; }

    public static String getNo_ktp() { return no_ktp; }

    public static String getTempat_lahir() { return tempat_lahir; }

    public static String getTanggal_lahir() { return tanggal_lahir; }

    public static String getAlamat_lengkap() { return alamat_lengkap; }


}
