package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Item;
import view.tm.ItemTM;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ManageItemFormController {
    public AnchorPane contextManageItem;
    public JFXTextField txtDescription;
    public JFXTextField txtPacketSize;
    public JFXTextField txtItemCode;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtUnitPrice;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colPacketSize;
    public TableColumn colUnitPrice;
    public TableColumn colQtyOnHand;
    public TableView<ItemTM> tblBox;
    public TableColumn colDiscount;
    public JFXTextField txtDiscount;
    public JFXButton updateBtn;
    public JFXButton deleteItemBtn;
    public JFXButton addItemBtn;

    int SelectedRow=-1;




    public void backToAdmin(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/SuperMarketForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) contextManageItem.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void backToSelectFormAdmin(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/SelectFormAdmin.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) contextManageItem.getScene().getWindow();
        window.setScene(new Scene(load));

    }

    public void addItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        String itemCode = txtItemCode.getText();
        String description = txtDescription.getText();
        String packetSize = txtPacketSize.getText();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        int discount = Integer.parseInt(txtDiscount.getText());

        ItemTM i1 = new ItemTM(
                itemCode,
                description,
                packetSize,
                unitPrice,
                qtyOnHand,
                discount


        );
        getItemList().add(i1);

        tblBox.setItems(getItemList());


        Item i = new Item(txtItemCode.getText(), txtDescription.getText(), txtPacketSize.getText(), Double.parseDouble(txtUnitPrice.getText()), Integer.parseInt(txtQtyOnHand.getText()), Integer.parseInt(txtDiscount.getText()));
        if (new ItemController().SaveItem(i))
            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
        else
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
        showItem();
        clearField();
    }


    public void initialize() {
        try {
            showItem();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        updateBtn.setDisable(true);
        deleteItemBtn.setDisable(true);

        tblBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            SelectedRow = (int) newValue;
            if (SelectedRow == -1) {
                addItemBtn.setDisable(false);
                updateBtn.setDisable(true);
                deleteItemBtn.setDisable(true);
                txtItemCode.setEditable(true);
            }else{
                addItemBtn.setDisable(true);
                updateBtn.setDisable(false);
                deleteItemBtn.setDisable(false);
                txtItemCode.setEditable(false);
            }
        });

    }


    private void clearField() {
        txtItemCode.clear();
        txtDescription.clear();
        txtPacketSize.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();
        txtDiscount.clear();
        txtItemCode.requestFocus();
        addItemBtn.setDisable(false);
        updateBtn.setDisable(true);
        deleteItemBtn.setDisable(true);
        txtItemCode.setEditable(true);

    }

    public void clearFiledOnAction(ActionEvent actionEvent) {
        clearField();

    }

    public void deleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ItemTM item = tblBox.getSelectionModel().getSelectedItem();
        String itemCode = item.getItemCode();

        if (new ItemController().deleteItem(itemCode)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Do You Want To Delete Selected Item");
            showItem();

        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
        clearField();
    }



    public void editOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ItemTM i1 = new ItemTM(
                txtItemCode.getText(),
                txtDescription.getText(),
                txtPacketSize.getText(),
                Double.parseDouble(txtUnitPrice.getText()),
                Integer.parseInt(txtQtyOnHand.getText()),
                Integer.parseInt(txtDiscount.getText())
        );


                if (new  ItemController().updateItem(i1)){
                    new Alert(Alert.AlertType.CONFIRMATION,"Updated").show();
                    showItem();

                }else{
                    new Alert(Alert.AlertType.WARNING,"Try Again").show();

                }

    }

    public ObservableList<ItemTM> getItemList() throws SQLException, ClassNotFoundException {
        ObservableList<ItemTM> obList = FXCollections.observableArrayList();

        Connection con = DbConnection.getInstance().getConnection();
        String query = "SELECT * FROM Item";
        PreparedStatement stm = con.prepareStatement(query);
        ResultSet rst = stm.executeQuery();

        while (rst.next()) {
            obList.add(new ItemTM(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getInt(5),
                    rst.getInt(6)
            ));

        }
       return obList;

    }

    public void showItem() throws SQLException, ClassNotFoundException {

        ObservableList<ItemTM> list = getItemList();

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPacketSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));

        tblBox.setItems(list);

    }


    public void moveToQtyOnHand(ActionEvent actionEvent) {
        txtQtyOnHand.requestFocus();
    }

    public void moveToPacketSize(ActionEvent actionEvent) {
        txtPacketSize.requestFocus();
    }

    public void moveToUnitPrice(ActionEvent actionEvent) {
        txtUnitPrice.requestFocus();
    }

    public void moveToDescription(ActionEvent actionEvent) {
        txtDescription.requestFocus();
    }

    public void moveToDiscount(ActionEvent actionEvent) {
        txtDiscount.requestFocus();

    }

    public void handleMouseOnAction(MouseEvent mouseEvent) {
        ItemTM item = null;
        try {
            item = tblBox.getSelectionModel().getSelectedItem();
            txtItemCode.setText(item.getItemCode());
            txtUnitPrice.setText(""+item.getUnitPrice());
            txtDescription.setText(item.getDescription());
            txtQtyOnHand.setText(""+item.getQtyOnHand());
            txtDiscount.setText(""+item.getDiscount());
            txtPacketSize.setText(""+item.getPackSize());
        } catch (Exception e) {

        }


    }
}

