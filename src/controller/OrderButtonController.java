package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
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
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Customer;
import model.Order;
import model.OrderDetails;
import view.tm.CartTM;

import javax.naming.Binding;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderButtonController {
    public JFXButton btnCancel;
    public JFXButton contextOrderButton;
    public JFXTextField txtAddress;
    public JFXTextField txtName;
    public JFXTextField txtTitle;
    public JFXComboBox<String> cmbCustomerId;
    public TableView<CartTM> tblId;
    public TableColumn colCode;
    public TableColumn colDescription;
    public TableColumn colQty;
    public TableColumn colTotal;
    public TableColumn colDiscount;

    static ObservableList<CartTM>parentCartObList = FXCollections.observableArrayList();

    public Label lblOrderId;
    public Label lblDate;
    public Label lblTime;
    public JFXButton btnConformOrder;




    public JFXTextField lblTotalAmount;
    public JFXTextField lblSavedAmount;
    public JFXTextField lnlAmountPaid;
    public JFXTextField lblRemainingBalance;

    double netPrice;

    public void initialize(){
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));

        loadDateAndTime();
        setOrderId();

        try {
            loadCustomerIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setCustomerData(newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        Bindings.bindContentBidirectional(parentCartObList, OrderFormController.getCart());
        tblId.setItems(parentCartObList);

        for (CartTM tm : tblId.getItems()){
            netPrice+=tm.getTotal();
        }
        lblTotalAmount.setText(new DecimalFormat("0.00").format(netPrice)+" /= ");

        double unit = 0.00;
        double main = 0.00;
        int qty  = 0;
        for (CartTM tm : tblId.getItems()){
            qty+=tm.getQty();
            unit+=tm.getUnitPrice();
            main+=qty * unit;

        }
        lblSavedAmount.setText(new DecimalFormat("0.00").format(main-netPrice)+" /= ");
    }

    public void backToOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();


    }

    public void addCustomerOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/AddCustomerForm.fxml ");
        Parent load = FXMLLoader.load(resource);
        Scene scene=new Scene(load);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.setTitle("Add Customer");
        stage.show();


    }

    private void  loadCustomerIds() throws SQLException, ClassNotFoundException {
        List<String>id = new CustomerController().getAllCustomerIds();
        cmbCustomerId.getItems().addAll(id);
    }

    private void setCustomerData(String id) throws SQLException, ClassNotFoundException {
        Customer c1 = new CustomerController().passCustomerDetails(id);
        if (c1 == null){
            new Alert(Alert.AlertType.WARNING,"Empty Result Set").show();

        }else{
            txtTitle.setText(c1.getCustomerTitle());
            txtName.setText(c1.getCustomerName());
            txtAddress.setText(c1.getCustomerAddress());
        }
    }

    public void conformOrderOnAction(ActionEvent actionEvent) throws SQLException {
        Stage stage = (Stage) btnConformOrder.getScene().getWindow(); stage.close();
        ArrayList<OrderDetails> items = new ArrayList<>();
        for (CartTM tempTm : parentCartObList
             ) {
                items.add(
                        new OrderDetails(
                                tempTm.getCode(),
                                lblOrderId.getText(),
                                tempTm.getQty(),
                                tempTm.getDiscount()

                        ));

        }
        Order order = new Order(
                lblOrderId.getText(),
                lblDate.getText(),
                cmbCustomerId.getValue(),
                items
        );
        if (new OrderController().placeOrder(order)){
            new Alert(Alert.AlertType.CONFIRMATION,"Success").show();
            setOrderId();


        }else{
            new Alert(Alert.AlertType.WARNING,"Try Again").show();
        }



    }

    private void setOrderId(){
        try {
            lblOrderId.setText(new OrderController().getOrderID());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
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

    public void refreshOnAction(ActionEvent actionEvent) {
        cmbCustomerId.getItems().clear();
        try {
            loadCustomerIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void amountPaidOnAction(ActionEvent actionEvent) {
        int AmountPaidText = Integer.parseInt(lnlAmountPaid.getText());
        lblRemainingBalance.setText(new DecimalFormat("0.00").format(AmountPaidText-netPrice)+" /= ");
    }

    public void moveToAddress(ActionEvent actionEvent) {

    }

    public void moveToCustomerName(ActionEvent actionEvent) {

    }
}
