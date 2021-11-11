package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class SuperMarketFormController {
    public AnchorPane txtId;


    public void openCashierOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/CashierLogin.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window   = (Stage) txtId.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void openAdminOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/AdminLogin.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window   = (Stage) txtId.getScene().getWindow();
        window.setScene(new Scene(load));

    }
}
