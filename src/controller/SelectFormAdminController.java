package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class SelectFormAdminController {
    public AnchorPane contextSelectForm;


    public void openSystemReportOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/SystemReportForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window   = (Stage) contextSelectForm.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void openManageItemOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageItemForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window   = (Stage) contextSelectForm.getScene().getWindow();
        window.setScene(new Scene(load));
    }


    public void backToCashierLogin(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/SuperMarketForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window   = (Stage) contextSelectForm.getScene().getWindow();
        window.setScene(new Scene(load));

    }
}
