package banking;

import org.sqlite.SQLiteDataSource;

import java.util.Scanner;

public class BaseBank {
    SQLiteDataSource sqLiteDataSource = new SQLiteDataSource();
    private final Scanner scanner = new Scanner(System.in);
    private final String dataBaseName;
    private final DataBase dataBase;
    Account account = new Account();

    public BaseBank(String dataBaseName) {
        this.dataBaseName = dataBaseName;
        sqLiteDataSource.setUrl("jdbc:sqlite:" + dataBaseName);
        dataBase = new DataBase(sqLiteDataSource);
    }

    private void createAccount() {
        System.out.println("Your card has been created");
        Account account = new Account();
        dataBase.insert(account);
        System.out.println("Your card number:");
        System.out.println(account.getCardNumber());
        System.out.println("Your card PIN:");
        System.out.println(account.getPIN());
    }

    private int logAccount() {
        int checkError = 0;
        System.out.println("Enter your card number:");
        String cardNumber = scanner.next();
        System.out.println("Enter your PIN:");
        String pinCode = scanner.next();
        account = dataBase.findByNumber(cardNumber);
        if (account == null || !pinCode.equals(account.getPIN())) {
            System.out.println("Wrong card number or PIN!");
        } else {
            System.out.println("You have successfully logged in!");
            boolean flag = true;
            while (flag) {
                System.out.println("1. Balance");
                System.out.println("2. Add income");
                System.out.println("3. Do transfer");
                System.out.println("4. Close account");
                System.out.println("5. Log out");
                System.out.println("0. Exit");
                int choose = scanner.nextInt();
                switch (choose) {
                    case 1:
                        System.out.println("Balance: " + account.getBalance());
                        break;
                    case 2:
                        System.out.println("Enter income:");
                        int income = scanner.nextInt();
                        dataBase.updateBalance(account.getCardNumber(), income);
                        System.out.println("Income was added!");
                        break;
                    case 3:
                        System.out.println("Transfer\n" +
                                "Enter card number:");
                        String cardNum = scanner.next();
                        Account checkNumber = dataBase.findByNumber(cardNum);
                        if (!account.checkLuhnAlgorithm(cardNum)) {
                            System.out.println("Probably you made mistake in the card number. Please try again!");
                            break;
                        }
                        if (cardNum.equals(account.getCardNumber())) {
                            System.out.println("You can't transfer money to the same account!");
                            break;
                        }
                        if (checkNumber == null) {
                            System.out.println("Such a card does not exist.");
                            break;
                        }
                        System.out.println("Enter how much money you want to transfer:");
                        int transfer = scanner.nextInt();
                        if (transfer > account.getBalance()) {
                            System.out.println("Not enough money!");
                            break;
                        }
                        dataBase.updateBalance(account.getCardNumber(), -transfer);
                        dataBase.updateBalance(checkNumber.getCardNumber(), transfer);
                        System.out.println("Success!");
                        break;
                    case 4:
                        System.out.println("The account has been closed!");
                        dataBase.deleteCard(account.getCardNumber());
                        break;
                    case 5:
                        System.out.println("You have successfully logged out!");
                        flag = false;
                        break;
                    case 0:
                        System.out.println("Bye!");
                        checkError = 1;
                        flag = false;
                        break;
                }
            }
        }
        return checkError;
    }

    public void menu() {
        boolean flag = true;
        while (flag) {
            System.out.println("1. Create an account");
            System.out.println("2. Log into account");
            System.out.println("0. Exit");
            int choose = scanner.nextInt();
            switch (choose) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    if (logAccount() == 1) {
                        flag = false;
                    }
                    break;
                case 0:
                    flag = false;
                    break;
            }
        }
    }
}
