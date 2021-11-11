package model;

import java.util.ArrayList;

public class Order {
    private String orderId;
    private String orderDate;
    private String customerId;
    private ArrayList<OrderDetails> items;

    public Order(String orderId, String orderDate, String customerId, ArrayList<OrderDetails> items) {
        this.setOrderId(orderId);
        this.setOrderDate(orderDate);
        this.setCustomerId(customerId);
        this.setItems(items);
    }

    public Order() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public ArrayList<OrderDetails> getItems() {
        return items;
    }

    public void setItems(ArrayList<OrderDetails> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", customerId='" + customerId + '\'' +
                ", items=" + items +
                '}';
    }
}
