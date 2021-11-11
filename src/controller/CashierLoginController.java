package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class CashierLoginController {
    public JFXTextField userNameContext;
    public JFXPasswordField passwordContext;
    public Label lblWarning;
    public AnchorPane contextId;



    public void openOrderForm(ActionEvent actionEvent) throws IOException {
        if (userNameContext.getText().equals("Ravi") & passwordContext.getText().equals("1999")){
        URL resource = getClass().getResource("../view/OrderForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window   = (Stage) contextId.getScene().getWindow();
        window.setScene(new Scene(load));

        }else{
            new Alert(Alert.AlertType.WARNING,"invalid Password").show();

        }
    }

    public void backToSuperMarketForm(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/SuperMarketForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window   = (Stage) contextId.getScene().getWindow();
        window.setScene(new Scene(load));


    }

    public void goToPassOnAction(ActionEvent actionEvent) {
        passwordContext.requestFocus();
    }
}
