package controller;

import model.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerServise {
    public boolean saveCustomer(Customer c) throws SQLException, ClassNotFoundException;
    public Customer passCustomerDetails(String id) throws SQLException, ClassNotFoundException;
    public List<String>getAllCustomerIds() throws SQLException, ClassNotFoundException;
}
