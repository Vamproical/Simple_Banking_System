package banking;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class BaseBank {
    private final Random random = new Random();
    private final Scanner scanner = new Scanner(System.in);
    private final ArrayList<String> accounts = new ArrayList<>();
    private final ArrayList<String> PINforAccounts = new ArrayList<>();

    private void createAccount() {
        System.out.println("Your card has been created");
        System.out.println("Your card number:");
        StringBuilder numberOfCard = new StringBuilder();
        StringBuilder PINforCard = new StringBuilder();
        numberOfCard.append(400000);
        for (int i = 0; i < 9; i++) {
            numberOfCard.append(random.nextInt(9));
        }
        numberOfCard.append(5);
        accounts.add(numberOfCard.toString());
        System.out.println(numberOfCard.toString());
        System.out.println("Your card PIN:");
        for (int i = 0; i < 4; i++) {
            PINforCard.append(random.nextInt(9));
        }
        PINforAccounts.add(PINforCard.toString());
        System.out.println(PINforCard.toString());
    }

    private int logAccount() {
        int checkError = 0;
        System.out.println("Enter your card number:");
        String checkCardNumber = scanner.next();
        System.out.println("Enter your PIN:");
        String checkPIN = scanner.next();
        boolean isExistAccount = false, isExistPIN = false;
        for (String account : accounts) {
            if (checkCardNumber.equals(account)) {
                isExistAccount = true;
                break;
            }
        }
        for (String piNforAccount : PINforAccounts) {
            if (checkPIN.equals(piNforAccount)) {
                isExistPIN = true;
                break;
            }
        }
        if (!isExistAccount || !isExistPIN) {
            System.out.println("Wrong card number or PIN!");
        }
        else {
            System.out.println("You have successfully logged in!");
            boolean flag = true;
            while (flag) {
            System.out.println("1. Balance");
            System.out.println("2. Log out");
            System.out.println("0. Exit");
            int choose = scanner.nextInt();
                switch (choose) {
                    case 1:
                        System.out.println("Balance: 0");
                        break;
                    case 2:
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
