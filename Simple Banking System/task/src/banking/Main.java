package banking;

import org.jetbrains.annotations.NotNull;

public class Main {
    public static void main(@NotNull String[] args) {
        String pathDatabase = "card.s3db";
        if (args[0].equals("-fileName") && args[1] != null) {
            pathDatabase = args[1];
        } else {
            System.out.println("Database name is not supplied. \nExiting the program.");
        }
        BaseBank baseBank = new BaseBank(pathDatabase);
        baseBank.menu();
    }
}