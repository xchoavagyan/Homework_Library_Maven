package services;

import models.Book;
import models.BookState;
import models.Customer;
import models.Order;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static models.DatabaseConstants.*;

public class CustomerService {
    private static CustomerService instance = null;

    private CustomerService() {
    }

    public static CustomerService getInstance() {
        if (instance == null) {
            instance = new CustomerService();
        }
        return instance;
    }

    public void create(Customer customer) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            if (connection != null) {
                String query = "INSERT INTO customers (name, surname, password_hash) VALUES (?,?,?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, customer.getName());
                statement.setString(2, customer.getSurname());
                statement.setString(3, customer.getPassword());
                statement.executeUpdate();
                System.out.println("Customer is Added to Library");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void update(int id, Customer customer) {

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            if (conn != null) {

                String query = "UPDATE customers SET name = ?, surname = ?, password_hash = ? WHERE id = ?";

                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, customer.getName());
                preparedStatement.setString(2, customer.getSurname());
                preparedStatement.setString(3, customer.getPassword());
                preparedStatement.setInt(4, id);
                preparedStatement.executeUpdate();
                System.out.println("Customer is Updated");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteById(int id) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            if (conn != null) {

                String query = "DELETE FROM customers WHERE id = ?";

                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
                System.out.println("Customer is Deleted");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Customer findById(int id) {

        Customer customer = new Customer();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            if (conn != null) {

                String query = "SELECT * FROM customers WHERE id = ?";

                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setInt(1, id);

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    customer.setName(resultSet.getString("name"));
                    customer.setSurname(resultSet.getString("surname"));
                    customer.setPassword(resultSet.getString("password_hash"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return customer;
    }

    public List<Customer> findAll() {

        List<Customer> customers = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            if (conn != null) {

                String query = "SELECT * FROM customers ";

                PreparedStatement preparedStatement = conn.prepareStatement(query);

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Customer customer = new Customer();
                    customer.setName(resultSet.getString("name"));
                    customer.setSurname(resultSet.getString("surname"));
                    customer.setPassword(resultSet.getString("password_hash"));
                    customers.add(customer);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return customers;
    }

    public boolean checkPassword(int id, String password) {
        boolean flag = false;
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            if (conn != null) {

                String customerPassword = null;
                String query = "SELECT password_hash FROM customers WHERE id = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    customerPassword = resultSet.getString("password_hash");
                }

                if (BCrypt.checkpw(password, customerPassword)) {
                    flag = true;
                } else {
                    System.out.println("Password is Incorrect");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            System.out.println("Customer Id is incorrect");
        }
        return flag;
    }

    public void takeABookFromLibrary(int customerID, String password, int bookID) {

        if (checkPassword(customerID,password)) {

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            if (conn != null) {
                String state = null;
                String query = "SELECT state FROM books WHERE id =?";

                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setInt(1, bookID);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    state = resultSet.getString("state");
                }

                if (state.equals("NOT_TAKEN")) {
                    Order order = new Order();
                    String query1 = "INSERT INTO orders (customer_id, book_id,return_date) VALUES (?,?,?);";
                    String query2 = "INSERT INTO orders_history (customer_id, book_id,return_date) VALUES (?,?,?);";
                    String query3 = "UPDATE books SET state = 'TAKEN' WHERE id = ?;";

                    PreparedStatement preparedStatement1 = conn.prepareStatement(query1);
                    preparedStatement1.setInt(1, customerID);
                    preparedStatement1.setInt(2, bookID);
                    preparedStatement1.setDate(3, Date.valueOf(order.getReturnDate()));

                    PreparedStatement preparedStatement2 = conn.prepareStatement(query2);
                    preparedStatement2.setInt(1, customerID);
                    preparedStatement2.setInt(2, bookID);
                    preparedStatement2.setDate(3, Date.valueOf(order.getReturnDate()));

                    PreparedStatement preparedStatement3 = conn.prepareStatement(query3);
                    preparedStatement3.setInt(1, bookID);

                    preparedStatement1.executeUpdate();
                    preparedStatement2.executeUpdate();
                    preparedStatement3.executeUpdate();
                    System.out.println("!!!!Take your book!!!!");
                    System.out.println("Return Date :" + order.getReturnDate().toString());
                } else {
                    System.out.println("That book was Taken");
                }
            }
        } catch (SQLException ex) {
            System.out.println("Something goes wrong");
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            System.out.println("Book ID is incorrect");
        }
    }else{
            System.out.println("Customer identification failed");
        }
    }

    public void returnBookToLibrary(int bookID) {

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            if (conn != null) {

                String query1 = "DELETE FROM orders WHERE book_id = ?";
                String query2 = "UPDATE books SET state = 'NOT_TAKEN' WHERE id = ?";

                PreparedStatement preparedStatement = conn.prepareStatement(query1);
                preparedStatement.setInt(1, bookID);

                PreparedStatement preparedStatement1 = conn.prepareStatement(query2);
                preparedStatement1.setInt(1, bookID);

                preparedStatement.executeUpdate();
                preparedStatement1.executeUpdate();
                System.out.println("Your book is Returned");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
