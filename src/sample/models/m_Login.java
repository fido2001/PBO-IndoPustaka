package sample.models;

import sample.utils.ConnectDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class m_Login {
    private boolean status_login=false;
    private String level;
    private static String idAkun;
    Connection con = new ConnectDB().connect();

    public void setLogin(String username, String password) throws SQLException {
        int row=0;
        Statement stat = con.createStatement();
        ResultSet rs = stat.executeQuery("select * from data_akun where username='"+username+"' and password='"+password+"'");
        while(rs.next()){
            row=rs.getRow();
            this.idAkun=rs.getString("idAkun");
            this.level=rs.getString("level");
            System.out.println("Login Sukses");
        }
        if(row>0){
            status_login=true;
        }
        else{
            status_login=false;
        }
    }

    public boolean getLogin(){
        return status_login;
    }

    public String getLevel() {return level;}

    public static String getIdAkun() {return idAkun;}
}
