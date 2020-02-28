package services;

import models.Author;
import models.Book;
import models.BookState;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static models.DatabaseConstants.*;

public class AuthorService {
    private static AuthorService instance = null;

    private AuthorService() {
    }

    public static AuthorService getInstance() {
        if (instance == null) {
            instance = new AuthorService();
        }
        return instance;
    }

    public void create(Author author) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            if (connection != null) {
                String query = "INSERT INTO authors(name,surname) VALUES (?,?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, author.getName());
                statement.setString(2, author.getSurname());
                statement.executeUpdate();
                System.out.println("Author is Added to Library");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void update(int id, Author author) {

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            if (conn != null) {

                String query = "UPDATE authors SET name = ?, surname = ? WHERE id = ?";

                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, author.getName());
                preparedStatement.setString(2, author.getSurname());
                preparedStatement.setInt(3, id);
                preparedStatement.executeUpdate();
                System.out.println("Author is Updated");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteById(int id) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            if (conn != null) {

                String query = "DELETE FROM authors WHERE id = ?";

                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
                System.out.println("Author is Deleted");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Author findById(int id) {

        Author author = new Author();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            if (conn != null) {

                String query = "SELECT * FROM authors WHERE id = ?";

                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setInt(1, id);

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    author.setName(resultSet.getString("name"));
                    author.setSurname(resultSet.getString("surname"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return author;
    }

    public List<Author> findAll() {

        List<Author> authors = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            if (conn != null) {

                String query = "SELECT * FROM authors ";

                PreparedStatement preparedStatement = conn.prepareStatement(query);

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Author author = new Author();
                    author.setName(resultSet.getString("name"));
                    author.setSurname(resultSet.getString("surname"));
                    authors.add(author);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return authors;
    }
}
