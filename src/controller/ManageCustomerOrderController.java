package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.tm.CartTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

public class ManageCustomerOrderController {

    public AnchorPane contextManage;
    public JFXTextField txtSearchOrder;
    public TableView tblManageCustomerOrder;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colQty;
    public TableColumn colDiscount;
    public TableColumn colNextTotal;
    public JFXTextField txtItemCode;
    public JFXTextField txtDescription;
    public JFXTextField txtQty;
    public JFXTextField txtDiscount;
    public JFXTextField txtNextTotal;
    public JFXButton contextSearch;
    public Label lblCid;
    public JFXComboBox txtComboBox;

    static ObservableList<CartTM> observableList = FXCollections.observableArrayList();
    public TableColumn colNetTotal;
    public JFXTextField txtNetTotal;




    public void backOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/OrderForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) contextManage.getScene().getWindow();
        window.setScene(new Scene(load));

    }


    public void btnSearchOnAction(ActionEvent actionEvent) {

    }


    public void btnConfirmEditOnAction(ActionEvent actionEvent) {

    }

}
