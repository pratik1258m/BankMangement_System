package project;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
class Customer {
    private String name;
    private String id;
    private double balance;
    private ArrayList<Transaction> transactions;
    public Customer(String name, String id, double initialDeposit) {
        this.name = name;
        this.id = id;
        this.balance = initialDeposit;
        this.transactions = new ArrayList<>();
        transactions.add(new Transaction("Initial Deposit", initialDeposit, id));
    }
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getBalance() {
        return balance;
    }
    public void deposit(double amount) {
        balance += amount;
        transactions.add(new Transaction("Deposit", amount, id));
    }
    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            transactions.add(new Transaction("Withdrawal", amount, id));
        } else {
            System.out.println("Insufficient balance!");
        }
    }
    public void transfer(double amount, Customer recipient) {
        if (balance >= amount) {
            this.withdraw(amount);
            recipient.deposit(amount);
            transactions.add(new Transaction("Transfer to " + recipient.getId(), amount, id));
            recipient.transactions.add(new Transaction("Transfer from " + id, amount, recipient.getId()));
        } else {
            System.out.println("Insufficient balance for transfer!");
        }
    }
    public void showTransactions() {
        System.out.println("\nTransaction History for Account: " + id);
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }
}
class Transaction {
    private Date date;
    private String type;
    private double amount;
    private String accountNumber;
    public Transaction(String type, double amount, String accountNumber) {
        this.date = new Date();
        this.type = type;
        this.amount = amount;
        this.accountNumber = accountNumber;
    }
    @Override
    public String toString() {
        return date + " | " + type + " | Amount: $" + amount + " | Account: " + accountNumber;
    }
}
public class BankManagementSystem {
    private static ArrayList<Customer> customers = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static int accountCounter = 1000;
    public static void main(String[] args) {
        while (true) {
            System.out.println("\nBank Management System");
            System.out.println("1. Create New Account");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Transfer Money");
            System.out.println("5. Check Balance");
            System.out.println("6. Show Transaction History");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            int choice = getIntInput();
            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    deposit();
                    break;
                case 3:
                    withdraw();
                    break;
                case 4:
                    transfer();
                    break;
                case 5:
                    checkBalance();
                    break;
                case 6:
                    showTransactions();
                    break;
                case 7:
                    System.out.println("Thank you for using our banking system!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    private static void createAccount() {
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();
        String id = "ACC" + accountCounter++;
        System.out.print("Enter initial deposit: $");
        double deposit = getDoubleInput();
        customers.add(new Customer(name, id, deposit));
        System.out.println("Account created successfully! Account ID: " + id);
    }

    private static void deposit() {
        Customer customer = findCustomer("Enter account ID: ");
        if (customer != null) {
            System.out.print("Enter deposit amount: $");
            double amount = getDoubleInput();
            customer.deposit(amount);
            System.out.println("Deposit successful. New balance: $" + customer.getBalance());
        }
    }
    private static void withdraw() {
        Customer customer = findCustomer("Enter account ID: ");
        if (customer != null) {
            System.out.print("Enter withdrawal amount: $");
            double amount = getDoubleInput();
            customer.withdraw(amount);
            System.out.println("Withdrawal successful. New balance: $" + customer.getBalance());
        }
    }
    private static void transfer() {
        Customer sender = findCustomer("Enter sender account ID: ");
        if (sender == null) return;
        
        Customer receiver = findCustomer("Enter receiver account ID: ");
        if (receiver == null) return;

        System.out.print("Enter transfer amount: $");
        double amount = getDoubleInput();
        sender.transfer(amount, receiver);
        System.out.println("Transfer successful.");
    }
    private static void checkBalance() {
        Customer customer = findCustomer("Enter account ID: ");
        if (customer != null) {
            System.out.println("Current balance: $" + customer.getBalance());
        }
    }
    private static void showTransactions() {
        Customer customer = findCustomer("Enter account ID: ");
        if (customer != null) {
            customer.showTransactions();
        }
    }
    private static Customer findCustomer(String prompt) {
        System.out.print(prompt);
        String id = scanner.nextLine();
        for (Customer c : customers) {
            if (c.getId().equalsIgnoreCase(id)) {
                return c;
            }
        }
        System.out.println("Account not found!");
        return null;
    }
    private static int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
    private static double getDoubleInput() {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
}
