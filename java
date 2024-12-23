import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Account {
    private static int accountNumberCounter = 1001;
    private int accountNumber;
    private String accountHolder;
    private double balance;
    private List<String> transactionHistory;

    public Account(String accountHolder) {
        this.accountNumber = accountNumberCounter++;
        this.accountHolder = accountHolder;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
        transactionHistory.add("Account created with initial balance: $0.00");
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add("Deposited: $" + amount + " | Balance: $" + balance);
        } else {
            System.out.println("Deposit amount must be positive.");
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactionHistory.add("Withdrew: $" + amount + " | Balance: $" + balance);
            return true;
        } else if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive.");
            return false;
        } else {
            System.out.println("Insufficient balance.");
            return false;
        }
    }

    public void printTransactionHistory() {
        System.out.println("Transaction History for Account " + accountNumber + ":");
        for (String transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }

    public void printAccountDetails() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder: " + accountHolder);
        System.out.println("Current Balance: $" + balance);
    }
}

class Bank {
    private List<Account> accounts;

    public Bank() {
        accounts = new ArrayList<>();
    }

    public Account createAccount(String accountHolder) {
        Account newAccount = new Account(accountHolder);
        accounts.add(newAccount);
        System.out.println("Account created for " + accountHolder + " with Account Number: " + newAccount.getAccountNumber());
        return newAccount;
    }

    public Account getAccount(int accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber() == accountNumber) {
                return account;
            }
        }
        return null;
    }

    public void displayAllAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts available.");
        } else {
            System.out.println("List of all accounts:");
            for (Account account : accounts) {
                account.printAccountDetails();
                System.out.println("-----------");
            }
        }
    }

    public boolean transferFunds(Account fromAccount, Account toAccount, double amount) {
        if (fromAccount.withdraw(amount)) {
            toAccount.deposit(amount);
            System.out.println("Transferred: $" + amount + " from Account " + fromAccount.getAccountNumber() + " to Account " + toAccount.getAccountNumber());
            return true;
        }
        return false;
    }
}

class Transaction {
    private String transactionType;
    private double amount;
    private String details;

    public Transaction(String transactionType, double amount, String details) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.details = details;
    }

    public void printTransaction() {
        System.out.println(transactionType + ": $" + amount + " | " + details);
    }
}

public class BankingApp {
    private static Bank bank = new Bank();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            showMenu();
            choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    createNewAccount();
                    break;
                case 2:
                    depositMoney();
                    break;
                case 3:
                    withdrawMoney();
                    break;
                case 4:
                    transferMoney();
                    break;
                case 5:
                    viewAccountDetails();
                    break;
                case 6:
                    viewTransactionHistory();
                    break;
                case 7:
                    viewAllAccounts();
                    break;
                case 8:
                    System.out.println("Exiting the Banking Application...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 8);
    }

    private static void showMenu() {
        System.out.println("\n--- Banking System ---");
        System.out.println("1. Create New Account");
        System.out.println("2. Deposit Money");
        System.out.println("3. Withdraw Money");
        System.out.println("4. Transfer Money");
        System.out.println("5. View Account Details");
        System.out.println("6. View Transaction History");
        System.out.println("7. View All Accounts");
        System.out.println("8. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void createNewAccount() {
        System.out.print("Enter account holder name: ");
        String name = scanner.nextLine();
        bank.createAccount(name);
    }

    private static void depositMoney() {
        System.out.print("Enter account number: ");
        int accountNumber = scanner.nextInt();
        Account account = bank.getAccount(accountNumber);
        if (account != null) {
            System.out.print("Enter amount to deposit: $");
            double amount = scanner.nextDouble();
            account.deposit(amount);
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void withdrawMoney() {
        System.out.print("Enter account number: ");
        int accountNumber = scanner.nextInt();
        Account account = bank.getAccount(accountNumber);
        if (account != null) {
            System.out.print("Enter amount to withdraw: $");
            double amount = scanner.nextDouble();
            account.withdraw(amount);
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void transferMoney() {
        System.out.print("Enter source account number: ");
        int fromAccountNumber = scanner.nextInt();
        Account fromAccount = bank.getAccount(fromAccountNumber);
        if (fromAccount != null) {
            System.out.print("Enter destination account number: ");
            int toAccountNumber = scanner.nextInt();
            Account toAccount = bank.getAccount(toAccountNumber);
            if (toAccount != null) {
                System.out.print("Enter amount to transfer: $");
                double amount = scanner.nextDouble();
                bank.transferFunds(fromAccount, toAccount, amount);
            } else {
                System.out.println("Destination account not found.");
            }
        } else {
            System.out.println("Source account not found.");
        }
    }

    private static void viewAccountDetails() {
        System.out.print("Enter account number: ");
        int accountNumber = scanner.nextInt();
        Account account = bank.getAccount(accountNumber);
        if (account != null) {
            account.printAccountDetails();
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void viewTransactionHistory() {
        System.out.print("Enter account number: ");
        int accountNumber = scanner.nextInt();
        Account account = bank.getAccount(accountNumber);
        if (account != null) {
            account.printTransactionHistory();
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void viewAllAccounts() {
        bank.displayAllAccounts();
    }
}
