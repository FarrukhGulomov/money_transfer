package dev.farruh.model;

import org.postgresql.replication.PGReplicationConnectionImpl;

import java.sql.*;

public class DatabaseService {
    private String url = "jdbc:postgresql://localhost:5432/transfer_db";
    private String dbUser = "postgres";
    private String dbPassword = "admin";

    public String addUser(User user) {
        String query = "INSERT INTO users (name, card_number, card_exp_date, card_code) VALUES(?,?,?,?);";

        try (Connection con = DriverManager.getConnection(url, dbUser, dbPassword);
             PreparedStatement prStatement = con.prepareStatement(query)) {
            Class.forName("org.postgresql.Driver");
            prStatement.setString(1, user.getName());
            prStatement.setString(2, user.getCardNumber());
            prStatement.setString(3, user.getCardExpDate());
            prStatement.setString(4, user.getCardCode());
            prStatement.execute();
            return "Ro'yxatdan muvaffaqiyatli o'tdingiz!";
        } catch (SQLException e) {
            if (e.getMessage().contains("users_card_number_key")) {
                System.err.println("Bu karta raqam ro'yxatdan o'tgan,\nIltimos boshqa kartani kiritib ko'ring!");
                return "";
            }
            throw new RuntimeException(e);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer getBalance(User user) {
        String query = "SELECT * FROM users;";
        try (Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String cardNumber = resultSet.getString("card_number");
                String cardCode = resultSet.getString("card_code");
                Integer balance = resultSet.getInt("balance");
                if (user.getCardNumber().equals(cardNumber) & user.getCardCode().equals(cardCode)) {
                    return balance;
                }

            }
            return -1;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer addBalance(User user, Integer deposit) {
        String sql = "UPDATE users SET balance = balance + ? WHERE card_number = ?;";
        try {
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, deposit);
            preparedStatement.setString(2, user.getCardNumber());
            int count = preparedStatement.executeUpdate();
            return count;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer withdrawalBalance(User user, Integer withdrawal) {
        String query = "UPDATE users SET balance = balance - ? WHERE card_number = ? ;";
        try (Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1,withdrawal);
            preparedStatement.setString(2,user.getCardNumber());
            int count = preparedStatement.executeUpdate();
            return count;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean isCardNumber(User user) {
        String query = "SELECT * FROM users;";
        try (Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                String cardNumber = resultSet.getString("card_number");
                String cardCode = resultSet.getString("card_code");

                if (user.getCardNumber().equals(cardNumber) ) {
                    return true;
                }

            }
            return false;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
