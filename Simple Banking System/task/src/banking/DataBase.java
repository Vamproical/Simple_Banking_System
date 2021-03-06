package banking;

import javax.sql.DataSource;
import java.sql.*;

public class DataBase {
    private final DataSource dataSource;

    public DataBase(DataSource dataSource) {
        this.dataSource = dataSource;
        createNewTable();
    }

    public void createNewTable() {
        try (final Connection connection = dataSource.getConnection();
             final Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DROP TABLE IF EXISTS card");
            stmt.executeUpdate("CREATE TABLE card ("
                    + " id INTEGER,"
                    + " number TEXT,"
                    + " pin TEXT,"
                    + " balance INTEGER DEFAULT 0"
                    + ")");
        } catch (SQLException e) {
            System.out.println("Error while creating table " + e.getMessage());
        }
    }

    private Connection connect() {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void insert(Account account) {
        String sql = "INSERT INTO card(number,pin,balance) VALUES(?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, account.getCardNumber());
            pstmt.setString(2, account.getPIN());
            pstmt.setInt(3, (int) account.getBalance());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateBalance(String cardNum, int income) {
        String sql = "UPDATE card SET balance = ?"
                + "WHERE number = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, income);
            pstmt.setString(2, cardNum);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteCard(String cardNum) {
        String sql = "DELETE FROM card WHERE number = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cardNum);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Account findByNumber(String number) {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement stmt = connection.prepareStatement("SELECT * FROM card WHERE number=?")) {

            stmt.setString(1, number);

            try (final ResultSet res = stmt.executeQuery()) {
                if (res.next()) {
                    final String pin = res.getString("pin");
                    final double balance = res.getDouble("balance");
                    return new Account(number, pin, balance);
                } else {
                    return null;
                }

            }
        } catch (SQLException e) {
            System.out.println("Error reading from file");
            throw new RuntimeException("Error while finding element");
        }
    }
}
