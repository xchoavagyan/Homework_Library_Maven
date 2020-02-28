package controllers;

import models.Book;
import services.BookService;

import java.util.List;
import java.util.Scanner;

public class BookController {
    private static BookController instance = null;
    private static BookService bookService= BookService.getInstance();
    Scanner scanner = new Scanner(System.in);
    private BookController() {
    }

    public static BookController getInstance() {
        if (instance == null) {
            instance = new BookController();
        }
        return instance;
    }
    public void initializeBook() {
        Book book = new Book();
        System.out.println("Enter book title:");
        book.setTitle(scanner.nextLine());
        System.out.println("Enter book description:");
        book.setDescription(scanner.nextLine());
        System.out.println("Enter book`s number of pages:");
        book.setNumberOfPages(Integer.parseInt(scanner.nextLine()));
        bookService.create(book);
    }

    public void authorsOfBook() {
        System.out.println("Enter book id:");
        int bookID = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter author id:");
        int authorID = Integer.parseInt(scanner.nextLine());
        bookService.addAuthorToBook(bookID,authorID);
    }

    public List<Book> findAllBooks() {
        return bookService.findAll();
    }

    public Book findBookById() {
        System.out.println("Enter BookID to find:");
        return bookService.findById(Integer.parseInt(scanner.nextLine()));
    }

    public List<Book> findAllNotTakenBooks() {
        return bookService.findAllNotTakenBooks();
    }

    public void updateBook() {
        System.out.println("Enter BookID to update:");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter book title:");
        String title = scanner.nextLine();
        System.out.println("Enter book description:");
        String description = scanner.nextLine();
        System.out.println("Enter book`s number of pages:");
        int numberOfPages = Integer.parseInt(scanner.nextLine());
        Book book = new Book();
        book.setTitle(title);
        book.setDescription(description);
        book.setNumberOfPages(numberOfPages);
        bookService.update(id, book);
    }

    public void deleteBookById() {
        System.out.println("Enter BookID to delete:");
        bookService.deleteById(Integer.parseInt(scanner.nextLine()));
    }
}
