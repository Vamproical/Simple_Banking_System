package banking;

import java.util.Random;

public class Account {
    private final Random random = new Random();
    private String cardNumber;
    private String PIN;
    private int balance;

    public Account() {
        generateCardNumber();
        generatePIN();
        balance = 0;
    }

    private void generatePIN() {
        StringBuilder PINforCard = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            PINforCard.append(random.nextInt(9));
        }
        PIN = PINforCard.toString();
    }

    private void generateCardNumber() {
        StringBuilder numberOfCard = new StringBuilder();
        numberOfCard.append(400000);
        for (int i = 0; i < 9; i++) {
            numberOfCard.append(random.nextInt(9));
        }
        numberOfCard.append(5);
        cardNumber = numberOfCard.toString();
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPIN() {
        return PIN;
    }

    public int getBalance() {
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
