package controller;

import db.DbConnection;
import model.Item;
import view.tm.ItemTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemController implements ItemInterface {
   /* public List<String> getCustomerIds() throws SQLException, ClassNotFoundException, SQLException, SQLException {
        ResultSet rst = DbConnection.getInstance().
                getConnection().prepareStatement("SELECT * FROM Item").executeQuery();
        List<String> item = new ArrayList<>();
        while (rst.next()) {
            item.add(
                    rst.getString(1)
            );
        }
        return item;


    }*/


    @Override
    public boolean SaveItem(Item item) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO Item VALUES(?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setObject(1, item.getItemCode());
        statement.setObject(2, item.getDescription());
        statement.setObject(3, item.getPackSize());
        statement.setObject(4, item.getQtyOnHand());
        statement.setObject(5, item.getUnitPrice());
        statement.setObject(6, item.getDiscount());

        return statement.executeUpdate() > 0;

    }

    @Override
    public List<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT  * FROM Item").executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()) {
            ids.add(rst.getString(1)
            );
        }
        return ids;
    }

    @Override
    public Item getItem(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Item WHERE  ItemCode='" + id + "'").executeQuery();
        if (rst.next()) {
            return new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getInt(5),
                    rst.getInt(6));
        } else {
            return null;

        }
    }

    @Override
    public boolean deleteItem(String itemCode) throws SQLException, ClassNotFoundException {
        return DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Item WHERE ItemCode='"+itemCode+"'" ).executeUpdate() > 0;
    }

    @Override
    public boolean updateItem(ItemTM item) throws SQLException, ClassNotFoundException {
        PreparedStatement rst = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Item SET Discription=?,PackSize=?, UnitPrice=?,QtyOnHand=?,discount=? WHERE ItemCode=?");
        rst.setObject(1,item.getDescription());
        rst.setObject(2,item.getPackSize());
        rst.setObject(3,item.getUnitPrice());
        rst.setObject(4,item.getQtyOnHand());
        rst.setObject(5,item.getDiscount());
        rst.setObject(6,item.getItemCode());
        return rst.executeUpdate() > 0;
    }
}

