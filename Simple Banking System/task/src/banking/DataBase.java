package banking;

import java.sql.*;

public class DataBase {
    String nameDataBase = "card.s3db";

    public DataBase() {
        createNewTable();
    }

    public void createNewTable() {
        String url = "jdbc:sqlite:";

        // SQL statement for creating a new table
        String sql = "CREATE TABLE card (\n"
                + "	id INTEGER,\n"
                + "	number TEXT,\n"
                + "	pin TEXT,\n"
                + "	balance INTEGER DEFAULT 0\n"
                + ");";
        try (Connection conn = DriverManager.getConnection(url + nameDataBase);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url + nameDataBase);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Insert a new row into the card table
     *
     * @param number
     * @param pin
     */
    public void insert(int i, String number, String pin) {
        String sql = "INSERT INTO card(id,number,pin) VALUES(?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, i);
            pstmt.setString(2, number);
            pstmt.setString(3, pin);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
