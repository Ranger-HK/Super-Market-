package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class AddCustomerFormController {

    public JFXButton cancelContext;
    public JFXTextField txtCustomerId;
    public JFXTextField txtCustomerTitle;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCity;
    public JFXTextField txtProvince;
    public JFXTextField txtPostelCode;
    public JFXTextField txtCustomerAddress;

    public void cancleOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) cancelContext.getScene().getWindow();
        stage.close();


    }

    public void addCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Customer c = new Customer(
                txtCustomerId.getText(),
                txtCustomerTitle.getText(),
                txtCustomerName.getText(),
                txtCustomerAddress.getText(),
                txtCity.getText(),
                txtProvince.getText(),
                txtPostelCode.getText()
        );
        if (new CustomerController().saveCustomer(c)){
            new Alert(Alert.AlertType.CONFIRMATION,"Saved").show();
        }else{
            new Alert(Alert.AlertType.WARNING,"Try Again").show();
        }
        Stage stage = (Stage) cancelContext.getScene().getWindow();
        stage.close();
    }

    public void moveToCustomerTitle(ActionEvent actionEvent) {
        txtCustomerTitle.requestFocus();

    }

    public void moveToCustomerName(ActionEvent actionEvent) {
        txtCustomerName.requestFocus();
    }

    public void moveToCustomerAddress(ActionEvent actionEvent) {
        txtCustomerAddress.requestFocus();
    }

    public void moveToCustomerProvince(ActionEvent actionEvent) {
        txtProvince.requestFocus();
    }

    public void moveToCustomerPostelCode(ActionEvent actionEvent) {
        txtPostelCode.requestFocus();
    }

    public void moveToCustomerCity(ActionEvent actionEvent) {
        txtCity.requestFocus();

    }
}

