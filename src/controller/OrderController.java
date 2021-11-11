package controller;

import db.DbConnection;
import model.Order;
import model.OrderDetails;
import view.tm.CartTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderController {

    public String getOrderID() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM `order` ORDER BY orderId DESC LIMIT 1").executeQuery();
        if (rst.next()){
            int tempId = Integer.parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId+1;

            if (tempId<9) {
                return "O-00" + tempId;

            }else if (tempId<99) {
                return "O-0" + tempId;

            }else {
                return "O-"+tempId;
            }
        }else{
            return "O-001";
        }
    }

    public  boolean placeOrder(Order order ) throws SQLException {
        Connection con = null;
        try {
            con = DbConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement stm = DbConnection.getInstance().getConnection().
                    prepareStatement("INSERT INTO `order` Values(?,?,?)");
            stm.setObject(1,order.getOrderId());
            stm.setObject(2,order.getOrderDate());
            stm.setObject(3,order.getCustomerId());

            if (stm.executeUpdate() > 0){

                if (saveOrderDetails(order.getOrderId(),order.getItems())){
                    con.commit();
                    return true;
                }else{
                    con.rollback();
                    return false;
                }

            }else{
                con.rollback();
                return false;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            {
                con.setAutoCommit(true);
            }

        }
        return false;
    }

    private boolean saveOrderDetails(String orderId, ArrayList<OrderDetails> items) throws SQLException, ClassNotFoundException {
        for (OrderDetails temp : items
        ) {
            PreparedStatement stm = DbConnection.getInstance().getConnection().
                    prepareStatement("INSERT INTO `order detail` VALUES(?,?,?,?)");
            stm.setObject(1,temp.getItemCode());
            stm.setObject(2,orderId);
            stm.setObject(3,temp.getQtyOnSell());
            stm.setObject(4,temp.getDiscount());

            if (stm.executeUpdate() > 0) {
                if (updateQty(temp.getItemCode(), temp.getQtyOnSell())) {

                } else {
                    return false;
                }

            }else {
                return false;
            }
        }
        return true;
    }


    private boolean updateQty(String itemCode,int qty) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().
                prepareStatement("UPDATE Item SET  QtyOnHand=( QtyOnHand-'"+qty+"') WHERE ItemCode='"+itemCode+ "'");

        return stm.executeUpdate() > 0;
    }

}
