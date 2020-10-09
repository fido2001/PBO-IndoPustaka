package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import sample.models.m_Login;
import sample.utils.Config;
import sample.utils.ConnectDB;
import sample.utils.Helpers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class c_Login extends Helpers implements Initializable{

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button login_btn;

    @FXML
    private void Login(ActionEvent event) throws SQLException {
        String username_text = username.getText();
        String password_text = password.getText();
        if (username.getText().equals("") || password.getText().equals("")){
            showAlert(AlertType.WARNING, "Peringatan", null, "Silahkan masukkan username dan password terlebih dahulu !");
            username.requestFocus();
        } else {
            m_Login log = new m_Login();
            log.setLogin(username_text, stringToMD5(password_text));
            if (log.getLogin()==true){
                if (log.getLevel().equals("petugas")){
                    changePageLog(event, "petugas");
                } else {
                    changePageLog(event, "masyarakat");
                }
            } else {
                showAlert(AlertType.ERROR, "Error", null, "Username dan Password yang anda masukkan salah !!");
                username.setText("");
                password.setText("");
                username.requestFocus();
            }
        }
    }

    @FXML
    private void btnBack(ActionEvent event) {
        changePage(event, "v_Dashboard");
    }

    @FXML
    private void klikRegister(ActionEvent event) {
        changePage(event, "v_Register");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Config.connection = ConnectDB.connect();
    }

    private void changePageLog(ActionEvent event, String aktor){
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../views/" + aktor + "/v_Home.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}
