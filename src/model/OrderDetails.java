package model;

public class OrderDetails {
    private String itemCode;
    private String orderId;
    private int qtyOnSell;
    private double discount;

    public OrderDetails(String itemCode, String orderId, int qtyOnSell, double discount) {
        this.setItemCode(itemCode);
        this.setOrderId(orderId);
        this.setQtyOnSell(qtyOnSell);
        this.setDiscount(discount);
    }

    public OrderDetails() {
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getQtyOnSell() {
        return qtyOnSell;
    }

    public void setQtyOnSell(int qtyOnSell) {
        this.qtyOnSell = qtyOnSell;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "itemCode='" + itemCode + '\'' +
                ", orderId='" + orderId + '\'' +
                ", qtyOnSell=" + qtyOnSell +
                ", discount=" + discount +
                '}';
    }
}
