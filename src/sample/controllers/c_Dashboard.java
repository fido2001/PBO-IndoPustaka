package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import sample.utils.Helpers;


public class c_Dashboard extends Helpers {
    @FXML
    private void gantilogin(ActionEvent event) {
        changePage(event, "v_Login");
    }

    @FXML
    private void gantiregister(ActionEvent event) {
        changePage(event, "v_Register");
    }
}
