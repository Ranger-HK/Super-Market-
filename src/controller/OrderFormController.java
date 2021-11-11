package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Item;
import view.tm.CartTM;
import view.tm.ItemTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public class OrderFormController {

    public AnchorPane contextOrderForm;
    public Label lblTime;
    public Label lblDate;
    public JFXComboBox<String> cmbItemCode;
    public JFXTextField txtDescription;
    public JFXTextField txtPacketSize;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtQuantity;
    public JFXTextField txtDiscount;
    public TableView<CartTM> tblManage;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colQuantity;
    public TableColumn colDiscount;
    public TableColumn colTotal;
    public Label lblTotal;
    public TableColumn colUnitPrice;


    public static ObservableList<CartTM> obList = FXCollections.observableArrayList();

    int cartSelectedRowForRemove=-1;

    public void initialize(){

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));



        try {
            loadItemIds();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                SetItemData(newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        tblManage.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            cartSelectedRowForRemove = (int) newValue;

        });


        loadDateAndTime();
    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MMMM-dd");
        lblDate.setText(f.format(date));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e->{
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(
                    currentTime.getHour()+ " : "+currentTime.getMinute()+ " : "+currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    public void backToCashierLogin(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/SuperMarketForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window   = (Stage) contextOrderForm.getScene().getWindow();
        window.setScene(new Scene(load));

    }

    private void loadItemIds() throws SQLException, ClassNotFoundException {
        List<String>itemIds = new ItemController().getAllIds();
        cmbItemCode.getItems().addAll(itemIds);
    }

    private void SetItemData(String itemCode) throws SQLException, ClassNotFoundException {
        Item i1 = new ItemController().getItem(itemCode);

        if (i1==null){
            new Alert(Alert.AlertType.WARNING,"Empty Result Set");

        }else {
            txtDescription.setText(i1.getDescription());
            txtUnitPrice.setText(String.valueOf(i1.getUnitPrice()));
            txtPacketSize.setText(i1.getPackSize());
            txtQtyOnHand.setText(String.valueOf(i1.getQtyOnHand()));
            txtDiscount.setText(String.valueOf(i1.getDiscount()));

        }
    }

    public void manageCustomerOrderOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageCustomerOrder.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window   = (Stage) contextOrderForm.getScene().getWindow();
        window.setScene(new Scene(load));

    }

    public void deleteOnAction(ActionEvent actionEvent) {
        if (cartSelectedRowForRemove==-1){
            new Alert(Alert.AlertType.WARNING,"Please Select a row").show();
        }else{
            new Alert(Alert.AlertType.CONFIRMATION,"Delete Completed").show();

            obList.remove(cartSelectedRowForRemove);
            calculateCost();
            tblManage.refresh();
        }
        clearField();
    }

    public void addToCartOnAction(ActionEvent actionEvent) {
        String description = txtDescription.getText();
        int packetSize = Integer.parseInt(txtPacketSize.getText());
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        int qtyForCustomer = Integer.parseInt(txtQuantity.getText());
        int discount = Integer.parseInt(txtDiscount.getText());
        double total = qtyForCustomer * unitPrice - qtyForCustomer * unitPrice * discount / 100;


        if(qtyOnHand< qtyForCustomer) {
            new Alert(Alert.AlertType.WARNING, "Invalid QTY").show();
            return;
        }


        CartTM tm = new CartTM(
                cmbItemCode.getValue(),
                description,
                qtyForCustomer,
                unitPrice,
                discount,
                total
        );

        int rowNumber = isExists(tm);

        if (rowNumber==-1) {
            obList.add(tm);

        }else {
            CartTM temp = obList.get(rowNumber);
            CartTM newTm = new CartTM(
                    temp.getCode(),
                    temp.getDescription(),
                    temp.getQty()+qtyForCustomer,
                    unitPrice,
                    temp.getDiscount(),
                    total+ temp.getTotal()

            );

            if(qtyOnHand< temp.getQty()+qtyForCustomer) {
                new Alert(Alert.AlertType.WARNING, "Invalid QTY").show();
                return;
            }


            obList.remove(rowNumber);
            obList.add(newTm);

        }



        tblManage.setItems(obList);
        calculateCost();
        clearField();

    }

    private int isExists(CartTM tm){
        for (int i = 0; i < obList.size(); i++){
            if (tm.getCode().equals(obList.get(i).getCode())){
                return i;
            }
        }
        return -1;
    }

    void calculateCost(){
        double ttl = 0;
        for (CartTM tm:obList
        ) {
            ttl+= tm.getTotal();
        }

        String formatTotal = new DecimalFormat("0.00").format(ttl);
        lblTotal.setText(formatTotal+" /=");
    }

    public void cancelOrderOnAction(ActionEvent actionEvent) {
        clearField();
        lblTotal.setText("");
        obList.clear();
    }

    public void orderBtnOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/OrderButton.fxml");
        Parent load = FXMLLoader.load(resource);
        Scene scene=new Scene(load);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.setTitle("Order Details");
        stage.show();


    }

    private void clearField(){
       txtDescription.clear();
       txtPacketSize.clear();
       txtUnitPrice.clear();
       txtQtyOnHand.clear();
       txtQuantity.clear();
       txtDiscount.clear();
       cmbItemCode.requestFocus();
       tblManage.getSelectionModel().clearSelection();
       cmbItemCode.setValue("");

    }

    public void moveToPacketSize (ActionEvent actionEvent) {
        txtPacketSize.requestFocus();
    }

    public void moveToUnitePrice (ActionEvent actionEvent) {
        txtUnitPrice.requestFocus();
    }

    public void moveToQtyOnHand (ActionEvent actionEvent) {
        txtQtyOnHand.requestFocus();
    }

    public void moveToQuntity (ActionEvent actionEvent) {
        txtQuantity.requestFocus();
    }

    public void moveToDiscount (ActionEvent actionEvent) {
        txtDiscount.requestFocus();
    }

    public static ObservableList getCart(){
        return obList;
    }

}
