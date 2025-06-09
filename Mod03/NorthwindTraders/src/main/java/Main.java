import java.sql.*;

public class Main {
    public static void main(String[] args) {

        //jdbc:mysql://[host][:port]/[database][?propertyName1=propertyValue1]
        String url = "jdbc:mysql://localhost:3306/northwind";
        String username = "root";
        String password = "yearup";

        //open connection
        //use a statement to execute
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Connected to the database!");
            // create statement, the statement is tied to the open connection
            Statement statement = connection.createStatement();

            //define query
            String query = "SELECT productID, productName, unitPrice, unitsInStock FROM products";
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                while (results.next()) {
                    System.out.println("Product Id: " + results.getInt("ProductID"));
                    System.out.println("Name: " + results.getString("ProductName"));
                    System.out.println("Price: " + results.getDouble("UnitPrice"));
                    System.out.println("Stock: " + results.getInt("UnitsInStock"));
                    System.out.println("------------------");
                }
            }

        } catch (SQLException e) {
            throw new Error(e);
        }


    }
}
