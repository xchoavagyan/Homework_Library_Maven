package creations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static models.DatabaseConstants.*;

public class CreateDatabaseAndTables {
    public static void createDatabaseAndTables() {
        try (Connection conn = DriverManager.getConnection(DB_URL_D, USER, PASSWORD)) {
            String query = "CREATE DATABASE IF NOT EXISTS library";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.executeUpdate();
            if (conn != null) {
                System.out.println("Database Created");
            }
        } catch (SQLException ex) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            ex.printStackTrace();
        }
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String createUsersTable = "CREATE TABLE IF NOT EXISTS books " +
                    "(   id       int not null auto_increment," +
                    "    title     varchar(255)," +
                    "    description varchar(255)," +
                    "    number_of_pages int," +
                    "    state enum('TAKEN','NOT_TAKEN')," +
                    "    primary key (id));";
            PreparedStatement preparedStatement = conn.prepareStatement(createUsersTable);
            preparedStatement.executeUpdate();
            if (conn != null) {
                System.out.println("Table Books Created");
            }
        } catch (SQLException ex) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            ex.printStackTrace();
        }
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String createTasksTable = "CREATE TABLE IF NOT EXISTS authors" +
                    "(    id      int not null auto_increment," +
                    "    name    varchar(255)," +
                    "    surname   varchar(255)," +
                    "    primary key (id));";
            PreparedStatement preparedStatement = conn.prepareStatement(createTasksTable);
            preparedStatement.executeUpdate();
            if (conn != null) {
                System.out.println("Table Authors Created");
            }
        } catch (SQLException ex) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            ex.printStackTrace();
        }
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String createTasksTable = "CREATE TABLE IF NOT EXISTS books_authors" +
                    "(  id      int not null auto_increment," +
                    "   book_id    int," +
                    "   author_id   int," +
                    "   foreign key (book_id) references books(id) ON DELETE CASCADE ON UPDATE RESTRICT," +
                    "   foreign key (author_id) references authors(id) ON DELETE CASCADE ON UPDATE RESTRICT," +
                    "   primary key (id));";
            PreparedStatement preparedStatement = conn.prepareStatement(createTasksTable);
            preparedStatement.executeUpdate();
            if (conn != null) {
                System.out.println("Table Books_Authors Created");
            }
        } catch (SQLException ex) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            ex.printStackTrace();
        }
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String createTasksTable = "CREATE TABLE IF NOT EXISTS customers" +
                    "(    id      int not null auto_increment," +
                    "    name    varchar(255)," +
                    "    surname   varchar(255)," +
                    "    password_hash varchar(255)," +
                    "    primary key (id));";
            PreparedStatement preparedStatement = conn.prepareStatement(createTasksTable);
            preparedStatement.executeUpdate();
            if (conn != null) {
                System.out.println("Table Customers Created");
            }
        } catch (SQLException ex) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            ex.printStackTrace();
        }
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String createTasksTable = "CREATE TABLE IF NOT EXISTS orders" +
                    "(   id      int not null auto_increment," +
                    "    customer_id int unique ," +
                    "    book_id int," +
                    "    return_date date," +
                    "    foreign key (customer_id) references customers(id) ON DELETE CASCADE ON UPDATE RESTRICT," +
                    "    primary key (id));";
            PreparedStatement preparedStatement = conn.prepareStatement(createTasksTable);
            preparedStatement.executeUpdate();
            if (conn != null) {
                System.out.println("Table Orders Created");
            }
        } catch (SQLException ex) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            ex.printStackTrace();
        }
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String createTasksTable = "CREATE TABLE IF NOT EXISTS orders_history" +
                    "(   id      int not null auto_increment," +
                    "    customer_id int," +
                    "    book_id int," +
                    "    return_date date," +
                    "    primary key (id));";
            PreparedStatement preparedStatement = conn.prepareStatement(createTasksTable);
            preparedStatement.executeUpdate();
            if (conn != null) {
                System.out.println("Table Orders_History Created");
            }
        } catch (SQLException ex) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            ex.printStackTrace();
        }
    }

}
