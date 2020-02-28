package creations;

import controllers.AuthorController;
import controllers.BookController;
import controllers.CustomerController;

import java.util.Scanner;

public class DisplayMenu {

    public static void runMenu() {
        BookController bookController = BookController.getInstance();
        AuthorController authorController = AuthorController.getInstance();
        CustomerController customerController = CustomerController.getInstance();
        Scanner scanner = new Scanner(System.in);

        boolean whileLoop = true;

        while (whileLoop) {
            System.out.println("--------MAIN---MENU--------");
            System.out.println("1. Enter books CRUD");
            System.out.println("2. Enter authors CRUD");
            System.out.println("3. Enter customers CRUD");
            System.out.println("4. Show all not taken books");
            System.out.println("5. Take a book from library");
            System.out.println("6. Return book");
            System.out.println("7. For Exit");
            switch (Integer.parseInt(scanner.nextLine())) {
                case 1:
                    System.out.println("--------BOOK---MENU-------");
                    System.out.println("1. Create book");
                    System.out.println("2. Add authors to book");
                    System.out.println("3. Find all books");
                    System.out.println("4. Find book by id");
                    System.out.println("5. Update book by id");
                    System.out.println("6. Delete book by id");
                    System.out.println("7. Back to previous menu");
                    System.out.println("8. For exit");

                    switch (Integer.parseInt(scanner.nextLine())) {
                        case 1:
                            bookController.initializeBook();
                            break;
                        case 2:
                            bookController.authorsOfBook();
                            break;
                        case 3:
                            System.out.println(bookController.findAllBooks());
                            break;
                        case 4:
                            System.out.println(bookController.findBookById());
                            break;
                        case 5:
                            bookController.updateBook();
                            break;
                        case 6:
                            bookController.deleteBookById();
                            break;
                        case 7:
                            break;
                        case 8:
                            whileLoop = false;
                            break;
                    }
                    break;
                case 2:
                    System.out.println("--------AUTHOR---MENU--------");
                    System.out.println("1. Create author");
                    System.out.println("2. Find all authors");
                    System.out.println("3. Find author by id");
                    System.out.println("4. Update author by id");
                    System.out.println("5. Delete author by id");
                    System.out.println("6. Back to previous menu");
                    System.out.println("7. For exit");
                    switch (Integer.parseInt(scanner.nextLine())) {
                        case 1:
                            authorController.initializeAuthor();
                            break;
                        case 2:
                            System.out.println(authorController.findAllAuthors());
                            break;
                        case 3:
                            System.out.println(authorController.findAuthorById());
                            break;
                        case 4:
                            authorController.updateAuthor();
                            break;
                        case 5:
                            authorController.deleteAuthorById();
                            break;
                        case 6:
                            break;
                        case 7:
                            whileLoop = false;
                            break;
                    }
                    break;
                case 3:
                    System.out.println("-----CUSTOMER---MENU------");
                    System.out.println("1. Create customer");
                    System.out.println("2. Find all customers");
                    System.out.println("3. Find customer by id");
                    System.out.println("4. Update customer by id");
                    System.out.println("5. Delete customer by id");
                    System.out.println("6. Back to previous menu");
                    System.out.println("7. For exit");
                    switch (Integer.parseInt(scanner.nextLine())) {
                        case 1:
                            customerController.initializeCustomer();
                            break;
                        case 2:
                            System.out.println(customerController.findAllCustomers());
                            break;
                        case 3:
                            System.out.println(customerController.findCustomerById());
                            break;
                        case 4:
                            customerController.updateCustomer();
                            break;
                        case 5:
                            customerController.deleteCustomerById();
                            break;
                        case 6:
                            break;
                        case 7:
                            whileLoop = false;
                            break;
                    }
                    break;
                case 4:
                    System.out.println(bookController.findAllNotTakenBooks());
                    break;
                case 5:
                    customerController.takeBookFromLibrary();
                    break;
                case 6:
                    customerController.returnBookToLibrary();
                    break;
                case 7:
                    whileLoop = false;
                    break;
            }
        }
    }
}
