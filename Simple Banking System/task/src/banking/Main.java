package banking;

public class Main {
    public static void main(String[] args) {
        String pathDatabase = "";
        if (args[0].matches("-fileName")) {
            pathDatabase = args[1];
        }
        BaseBank baseBank = new BaseBank(pathDatabase);
        baseBank.menu();
    }
}