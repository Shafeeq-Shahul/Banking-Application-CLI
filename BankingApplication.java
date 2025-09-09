/*
    Banking Application based on Command Line Interface(CLI)
    * It showcases the basic functionality of the Bank System such as deposit, withdrawal and able to see the transaction history.
    * It enables beginners to build projects in java.
    * It helps to learn and understand OOPs (Encapsulation, Inheritance, Abstraction, Polymorphism)
*/
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// interface helps to attains pure abstraction
interface BankMethods {

    double getBalance();

    void deposit(double depositAmount);

    void withDraw(double WithdrawAmount);
}

// Class BankAccount - to store the name and account number of the customer and null exceptions
class BankAccount {
    private String accountNo;
    private String name;

    public BankAccount(String accountNo, String name) {
        if (accountNo == null || accountNo.trim().isEmpty() ||
                name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Account number and name must not be empty or null");
        }
        this.accountNo = accountNo;
        this.name = name;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        if (accountNo == null || accountNo.trim().isEmpty()) {
            throw new IllegalArgumentException("Account number cannot be zero");
        }
        this.accountNo = accountNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer's name should not be null");
        }
        this.name = name;
    }

    @Override
    public String toString() {
        return "BankAccount = {" +
                "accountNo='" + accountNo + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

}

class BankMethodsImp implements BankMethods {
    // balance of the customer
    // setter - violets the balance amount
    private double balance;

    public double getBalance() {
        return balance;
    }

    public void deposit(double depositAmount) {
        this.balance += depositAmount;
    }

    public void withDraw(double withDrawAmount) {
        this.balance -= withDrawAmount;
    }
}

class BankService {
    private final BankAccount bankAccount;
    private final BankMethodsImp bankMethodsImp;

    // to store the transaction history
    private final List<String> transactionHistory = new ArrayList<>();

    // Dependency Injection - Constructor Injection
    public BankService(BankAccount bankAccount,
                       BankMethodsImp bankMethodsImp) {
        this.bankAccount = bankAccount;
        this.bankMethodsImp = bankMethodsImp;
    }

    public void viewAccount() {
        System.out.println(bankAccount);
        System.out.println("Available Balance : " + bankMethodsImp.getBalance());
    }

    public void depositAmount(double deposit) {
        if (deposit <= 0) {
            System.out.println("\nPlease re run the Application...\n");
            throw new IllegalArgumentException("Deposit Amount should not be zero...");
        }
        else {
            bankMethodsImp.deposit(deposit);
            logTransaction("\uD83D\uDCB0Deposit", deposit);
            System.out.println("Available Balance : " + bankMethodsImp.getBalance());
        }
    }

    public void withDrawAmount(double withDraw) {
        if (withDraw <= 0) {
            System.out.println("\nPlease re run the Application...\n");
            throw new IllegalArgumentException("Withdraw Amount should not be zero...");
        }
        else if (withDraw > bankMethodsImp.getBalance()) {
            System.out.println("\nPlease re run the Application...\n");
            throw new IllegalArgumentException("Withdraw Amount should not greater than Balance amount...");
        }
        else {
            bankMethodsImp.withDraw(withDraw);
            logTransaction("\uD83D\uDCB8WITHDRAW", withDraw);
            System.out.println("Available Balance : " + bankMethodsImp.getBalance());
        }
    }

    public void logTransaction(String type, double amount) {
        // format() method - used to format the string with time and amount details
        String entry = String.format("[%s] %s ₹%,.2f", LocalDateTime.now(), type, amount);
        transactionHistory.add(entry);
    }

    public void viewTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("📭 No transactions yet.");
        }
        else {
            System.out.println("📜 Transaction History:");
            System.out.println("Customer name : " + bankAccount.getName());
            System.out.println("Customer A/C No. : " + bankAccount.getAccountNo());
            transactionHistory.forEach(
                    System.out::println
            );
        }
    }

}

public class BankingApplication {
    
    // used for Command Line Interface(CLI)
    public static void displayApplicationMenu() {
        String option = """
                +======================================================================+
                |                        >> APPLICATION MENU <<                        |
                +----------------------------------------------------------------------+
                | [1] > VIEW ACCOUNT                                                   |
                | [2] > DEPOSIT                                                        |
                | [3] > WITHDRAW                                                       |
                | [4] > EXIT                                                           |
                | [5] > VIEW TRANSACTION HISTORY                                       |
                +======================================================================+
                """;
        System.out.println(option);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the Account Number : ");
        String accountNo = scanner.nextLine();
        System.out.print("Enter your name :");
        String customerName = scanner.nextLine();
        System.out.println("\nAccount has been created at " + LocalDateTime.now() + "\n");
        BankAccount bankAccount = new BankAccount(
                accountNo,
                customerName
        );
        BankMethodsImp bankMethodsImp = new BankMethodsImp();
        BankService bankService = new BankService(
                bankAccount,
                bankMethodsImp
        );
        boolean isExitedTheApplication = true;
        while (isExitedTheApplication) {
            BankingApplication.displayApplicationMenu();
            System.out.print("Enter the option : ");
            int option = scanner.nextInt();
            if (option == 1) {
                bankService.viewAccount();
            }
            else if (option == 2) {
                System.out.print("Enter the deposit amount : ");
                double depositAmount = scanner.nextDouble();
                bankService.depositAmount(depositAmount);
            }
            else if (option == 3) {
                System.out.print("Enter the Withdraw amount : ");
                double withdrawAmount = scanner.nextDouble();
                bankService.withDrawAmount(withdrawAmount);
            }
            else if (option == 4) {
                isExitedTheApplication = false;
                System.out.println("\n🙏 Thank you for using the application. Have a great day!\n");
                System.out.println("🕒 [" + LocalDateTime.now() + "] Operation completed.");
                scanner.close();
            }
            else if (option == 5) {
                bankService.viewTransactionHistory();
            }
            else {
                System.out.println("Invalid Options... Press 1, 2 or 3");
            }
        }
    }


}

