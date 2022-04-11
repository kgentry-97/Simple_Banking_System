package banking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbControl {

    private static String dbUrl;

    private static void createUrl(String filename) {
        String tempUrl = "jdbc:sqlite:" + filename;
        dbUrl = tempUrl;
    }


    public static void createDb(String filename) {
        createUrl(filename);
        String createTable = "create table IF NOT EXISTS card ( id integer primary key, number text not null, pin text not null, balance integer default 0 );";

        try (Connection connection = DriverManager.getConnection(dbUrl)) {
            if (connection != null) {
                try (Statement statement = connection.createStatement()) {
                    statement.executeUpdate(createTable);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertRecord(AccountInfo newAccount) {
        String insertRecord = String.format("insert into card ( number, pin, balance ) values ( \"%s\", \"%s\", %d );",
                newAccount.getAccountNumber(), newAccount.getPin(), newAccount.getBalance());

        try (Connection connection = DriverManager.getConnection(dbUrl)) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(insertRecord);
                connection.commit();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static AccountInfo findRecord(String accountNumber) {

        String findAccount = String.format("Select * from card where number = \"%s\";", accountNumber);
        AccountInfo accountInfo = null;

        try (Connection connection = DriverManager.getConnection(dbUrl)) {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(findAccount);

                while (resultSet.next()) {
                    String cardNumber = resultSet.getString("number");
                    String pin = resultSet.getString("pin");
                    int balance = resultSet.getInt("balance");
                    accountInfo = new AccountInfo(cardNumber, pin, balance);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return accountInfo;
    }

    public static void deleteRecord(String accountNumber){
        String deleteRecord = "delete from card where number = ?";

        try (Connection connection = DriverManager.getConnection(dbUrl)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteRecord)) {
                preparedStatement.setString(1, accountNumber);
                preparedStatement.executeUpdate();
            } catch (SQLException e){
                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateBalance(int updateValue, String accountNumber ){
        String updateBalance = "UPDATE card SET balance = ? WHERE number = ?";

        try (Connection connection = DriverManager.getConnection(dbUrl)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateBalance)) {
                preparedStatement.setInt(1, updateValue);
                preparedStatement.setString(2, accountNumber );
                preparedStatement.executeUpdate();

            } catch (SQLException e){
                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static int getBalance(String accountNumber ){
        String updateBalance = "select balance from card WHERE number = ?";
        int balance = 0;

        try (Connection connection = DriverManager.getConnection(dbUrl)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateBalance)) {
                preparedStatement.setString(1, accountNumber );
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    balance = resultSet.getInt("balance");
                }
            } catch (SQLException e){
                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return balance;
    }
}
