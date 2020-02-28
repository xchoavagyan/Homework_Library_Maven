package controllers;

import models.Author;
import services.AuthorService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AuthorController {
    private static AuthorController instance = null;
    private static AuthorService authorService = AuthorService.getInstance();
    Scanner scanner = new Scanner(System.in);

    private AuthorController() {
    }

    public static AuthorController getInstance() {
        if (instance == null) {
            instance = new AuthorController();
        }
        return instance;
    }

    public void initializeAuthor() {
        Author author = new Author();
        System.out.println("Enter author name:");
        author.setName(scanner.nextLine());
        System.out.println("Enter author surname:");
        author.setSurname(scanner.nextLine());
        authorService.create(author);
    }

    public List<Author> findAllAuthors() {
        return authorService.findAll();
    }

    public Author findAuthorById() {
        System.out.println("Enter AuthorID:");
        return authorService.findById(Integer.parseInt(scanner.nextLine()));
    }

    public void updateAuthor() {
        System.out.println("Enter AuthorID to update:");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter author name:");
        String name = scanner.nextLine();
        System.out.println("Enter author surname:");
        String surname = scanner.nextLine();
        Author author = new Author(name, surname);
        authorService.update(id, author);
    }

    public void deleteAuthorById() {
        System.out.println("Enter AuthorID to delete:");
        authorService.deleteById(Integer.parseInt(scanner.nextLine()));
    }
}
