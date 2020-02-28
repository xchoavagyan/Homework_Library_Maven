package controllers;

import models.Customer;
import services.CustomerService;

import java.util.List;
import java.util.Scanner;

public class CustomerController {
    private static CustomerController instance = null;
    private static CustomerService customerService = CustomerService.getInstance();
    Scanner scanner = new Scanner(System.in);

    private CustomerController() {
    }

    public static CustomerController getInstance() {
        if (instance == null) {
            instance = new CustomerController();
        }
        return instance;
    }

    public void initializeCustomer() {
        Customer customer = new Customer();
        System.out.println("Enter customer name:");
        customer.setName(scanner.nextLine());
        System.out.println("Enter customer surname:");
        customer.setSurname(scanner.nextLine());
        System.out.println("Enter customer password:");
        customer.setPassword(scanner.nextLine());
        customerService.create(customer);
    }

    public List<Customer> findAllCustomers() {
        return customerService.findAll();
    }

    public Customer findCustomerById() {
        System.out.println("Enter CustomerID to find:");
        return customerService.findById(Integer.parseInt(scanner.nextLine()));
    }

    public void updateCustomer() {
        System.out.println("Enter CustomerID to update:");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter customer name:");
        String name = scanner.nextLine();
        System.out.println("Enter customer surname:");
        String surname = scanner.nextLine();
        System.out.println("Enter customer password:");
        String password = scanner.nextLine();
        Customer customer = new Customer(name, surname, password);
        customerService.update(id, customer);
    }

    public void deleteCustomerById() {
        System.out.println("Enter CustomerID to delete:");
        customerService.deleteById(Integer.parseInt(scanner.nextLine()));
    }

    public void takeBookFromLibrary() {
        System.out.println("Enter CustomerID:");
        int customerID = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter Customer password:");
        String password = scanner.nextLine();
        System.out.println("Enter BookID :");
        int bookID = Integer.parseInt(scanner.nextLine());
        customerService.takeABookFromLibrary(customerID, password, bookID);
    }

    public void returnBookToLibrary() {
        System.out.println("Enter BookID:");
        int bookID = Integer.parseInt(scanner.nextLine());
        customerService.returnBookToLibrary(bookID);
    }
}

