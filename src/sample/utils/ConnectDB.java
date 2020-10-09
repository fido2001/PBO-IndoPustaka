package sample.utils;
import java.sql.*;

public class ConnectDB {
    private static Connection con = null;

    public static Connection connect(){
        if(con == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/indo_pustaka", "root", "");
                return con;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return con;
    }
}
