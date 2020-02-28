package services;

import static models.DatabaseConstants.*;

import models.BookState;

import models.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class BookService {
    private static BookService instance = null;

    private BookService() {
    }

    public static BookService getInstance() {
        if (instance == null) {
            instance = new BookService();
        }
        return instance;
    }

    public void create(Book book) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            if (connection != null) {
                String query = "INSERT INTO books(title, description, number_of_pages,state) VALUES (?,?,?,?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, book.getTitle());
                statement.setString(2, book.getDescription());
                statement.setInt(3, book.getNumberOfPages());
                statement.setString(4, book.getState().toString());
                statement.executeUpdate();
                System.out.println("Book is Added to Library");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void update(int id, Book book) {

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            if (conn != null) {

                String query = "UPDATE books SET title = ?, description = ?, number_of_pages = ? WHERE id = ?";

                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, book.getTitle());
                preparedStatement.setString(2, book.getDescription());
                preparedStatement.setInt(3, book.getNumberOfPages());
                preparedStatement.setInt(4, id);
                preparedStatement.executeUpdate();
                System.out.println("Book is Updated");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteById(int id) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            if (conn != null) {

                String query = "DELETE FROM books WHERE id = ?";

                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
                System.out.println("Book is Deleted");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Book findById(int id) {

        Book book = new Book();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            if (conn != null) {

                String query = "SELECT * FROM books WHERE id = ?";

                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setInt(1, id);

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    book.setTitle(resultSet.getString("title"));
                    book.setDescription(resultSet.getString("description"));
                    book.setNumberOfPages(resultSet.getInt("number_of_pages"));
                    book.setState(BookState.valueOf(resultSet.getString("state")));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return book;
    }

    public List<Book> findAll() {

        List<Book> books = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            if (conn != null) {

                String query = "SELECT * FROM books ";

                PreparedStatement preparedStatement = conn.prepareStatement(query);

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Book book = new Book();
                    book.setTitle(resultSet.getString("title"));
                    book.setDescription(resultSet.getString("description"));
                    book.setNumberOfPages(resultSet.getInt("number_of_pages"));
                    book.setState(BookState.valueOf(resultSet.getString("state")));
                    books.add(book);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return books;
    }

    public List<Book> findAllNotTakenBooks() {

        List<Book> books = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            if (conn != null) {

                String query = "SELECT * FROM books WHERE state IN ('NOT_TAKEN')";

                PreparedStatement preparedStatement = conn.prepareStatement(query);

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Book book = new Book();
                    book.setTitle(resultSet.getString("title"));
                    book.setDescription(resultSet.getString("description"));
                    book.setNumberOfPages(resultSet.getInt("number_of_pages"));
                    book.setState(BookState.valueOf(resultSet.getString("state")));
                    books.add(book);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return books;
    }

    public void addAuthorToBook(int bookID, int authorID) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            if (conn != null) {

                String query = "INSERT INTO books_authors (book_id, author_id) VALUES (?,?)";

                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setInt(1, bookID);
                preparedStatement.setInt(2, authorID);
                preparedStatement.executeUpdate();
                System.out.println("Author is added to book");
            }
        } catch (SQLException ex) {
            System.out.println("Incorrect BookID or CustomerID");
        }
    }

}
