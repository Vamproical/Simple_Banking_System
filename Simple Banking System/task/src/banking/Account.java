package banking;

import java.util.Random;

public class Account {
    private final Random random = new Random();
    private String cardNumber;
    private String PIN;
    private final double balance;

    public Account() {
        generateCardNumber();
        generatePIN();
        balance = 0;
    }

    public Account(String number, String pin, double balance) {
        this.cardNumber = number;
        this.PIN = pin;
        this.balance = balance;
    }

    private void generatePIN() {
        StringBuilder PINforCard = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            PINforCard.append(random.nextInt(9));
        }
        PIN = PINforCard.toString();
    }

    private int luhnAlgorith(String number) {
        int lastDigit;
        int sumNumbers = 0;
        boolean isSecond = true;
        for (int i = number.length() - 1; i >= 0; i--) {
            int digits = number.charAt(i) - '0';
            if (isSecond) {
                digits *= 2;
            }
            if (digits > 9) {
                digits -= 9;
            }
            isSecond = !isSecond;
            sumNumbers += digits;
        }
        lastDigit = (sumNumbers * 9) % 10;
        return lastDigit;
    }

    private void generateCardNumber() {
        StringBuilder numberOfCard = new StringBuilder();
        numberOfCard.append(400000);
        for (int i = 0; i < 9; i++) {
            numberOfCard.append(random.nextInt(9));
        }
        numberOfCard.append(luhnAlgorith(numberOfCard.toString()));
        cardNumber = numberOfCard.toString();
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPIN() {
        return PIN;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "Card{" +
                ", cardNumber='" + cardNumber + '\'' +
                ", PIN='" + PIN + '\'' +
                ", balance=" + balance +
                '}';
    }
}
