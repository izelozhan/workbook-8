import java.sql.*;

public class Main {
    public static void main(String[] args) {

        //jdbc:mysql://[host][:port]/[database][?propertyName1=propertyValue1]
        String url = "jdbc:mysql://localhost:3306/northwind";
        String username = "root";
        String password = "password";

        //open connection
        //use a statement to execute
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Connected to the database!");
            // create statement, the statement is tied to the open connection
            Statement statement = connection.createStatement();

            //define query
            String query = "SELECT * FROM products ";
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                String productName = results.getString("productName");
                System.out.println("Product Name");
                System.out.println(productName);
                String unitsOnOrder = results.getString("unitsOnOrder");

                if (!unitsOnOrder.equals("0")) {
                    System.out.println("Units On Order");

                    System.out.println(unitsOnOrder);
                }
            }

        } catch (SQLException e) {
            throw new Error(e);
        }


    }
}
