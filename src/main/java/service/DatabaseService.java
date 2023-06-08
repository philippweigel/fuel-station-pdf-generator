package service;

import config.DatabaseConfig;
import model.CompleteData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseService {
    public String getCustomerNameFromCompleteData(CompleteData completeData) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        String customerName = null;

        try {
            connection = DatabaseConfig.getConnection();
            String query = "SELECT first_name, last_name FROM customer WHERE id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, completeData.getCustomerId());
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                customerName = firstName + " " + lastName;
                System.out.println("Customer Name: " + customerName);
            } else {
                System.out.println("Customer not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the database resources
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return customerName;
    }
}
