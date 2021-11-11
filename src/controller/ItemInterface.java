package controller;

import model.Item;
import view.tm.ItemTM;

import java.sql.SQLException;
import java.util.List;

public interface ItemInterface {
    public boolean SaveItem(Item item) throws SQLException, ClassNotFoundException;
    public List<String>getAllIds() throws SQLException, ClassNotFoundException;
    public Item getItem(String id) throws SQLException, ClassNotFoundException;
    public boolean deleteItem(String itemCode) throws SQLException, ClassNotFoundException;
    public boolean updateItem(ItemTM item) throws SQLException, ClassNotFoundException;

}
