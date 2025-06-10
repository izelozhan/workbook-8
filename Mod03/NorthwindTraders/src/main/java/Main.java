import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        //jdbc:mysql://[host][:port]/[database][?propertyName1=propertyValue1]
        // for driver manager
//        String url = "jdbc:mysql://localhost:3306/northwind";
//        String username = "root";
//        String password = "password";

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/northwind");
        dataSource.setUsername("root");
        dataSource.setPassword("password");

        //open connection & use a statement to execute
        try (Connection connection = dataSource.getConnection()) {
            Scanner scanner = new Scanner(System.in);
            boolean exit = true;
            System.out.println("Connected to the database!");

            while (exit) {
                System.out.println("\nWhat do you want to do?");
                System.out.println("1) Display all products");
                System.out.println("2) Display all customers");
                System.out.println("3) Display all categories");
                System.out.println("0) Exit");
                System.out.print("Select an option: ");

                String userSelection = scanner.nextLine();

                switch (userSelection) {
                    case "0" -> {
                        System.out.println("Exiting.. Bye!");
                        exit = false;
                    }
                    case "1" -> displayProducts(connection);
                    case "2" -> displayCustomers(connection);
                    case "3" -> displayAllCategories(connection, scanner);
                    default -> System.out.println("Try again, invalid option.");
                }
            }
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    // pass connection to method and use statement inside to execute
    // query is different for each, so just pass the connection
    // use try-with-resources for automatically close the resource

    private static void displayProducts(Connection connection) throws SQLException {
        String query = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM products";

        try (Statement statement = connection.createStatement();
             ResultSet results = statement.executeQuery(query)) {

            System.out.println("\n--- Products ---");
            while (results.next()) {
                System.out.println("Product Id: " + results.getInt("ProductID"));
                System.out.println("Name: " + results.getString("ProductName"));
                System.out.println("Price: " + results.getDouble("UnitPrice"));
                System.out.println("Stock: " + results.getInt("UnitsInStock"));
                System.out.println("------------------");
            }
        }
    }

    private static void displayCustomers(Connection connection) throws SQLException {
        String query = "SELECT contactName, companyName, city, country, phone FROM customers order by country";

        try (Statement statement = connection.createStatement();
             ResultSet results = statement.executeQuery(query)) {

            System.out.println("\n--- Customers ---");
            while (results.next()) {
                System.out.println("Contact Name: " + results.getString("contactName"));
                System.out.println("Company Name: " + results.getString("companyName"));
                System.out.println("City: " + results.getString("city"));
                System.out.println("Country: " + results.getString("country"));
                System.out.println("Phone: " + results.getString("phone"));
                System.out.println("------------------");
            }
        }
    }

    private static void displayAllCategories(Connection connection, Scanner scanner) throws SQLException {
        String query = "SELECT categoryID, categoryName FROM categories ORDER BY CategoryID";

        //create statement for categories-print categories
        try (Statement statement = connection.createStatement()) {
            //get results from query using ResultSet
            ResultSet results = statement.executeQuery(query);
            System.out.println("\n--- Categories ---");
            while (results.next()) {
                System.out.println("Category ID: " + results.getInt("categoryID"));
                System.out.println("Category Name: " + results.getString("categoryName"));
            }
        }


        System.out.println("Please enter a Category ID to see products from that category: ");

        int categoryId = Integer.parseInt(scanner.nextLine());

        String productsQuery = "SELECT productID, productName, unitPrice, unitsInStock " +
                "FROM products WHERE CategoryID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(productsQuery)) {

            preparedStatement.setInt(1, categoryId);
            ResultSet results = preparedStatement.executeQuery();

            System.out.println("\n--- Products in Category " + categoryId + " ---");

            while (results.next()) {
                System.out.println("Product Id: " + results.getInt("ProductID"));
                System.out.println("Name: " + results.getString("ProductName"));
                System.out.println("Price: " + results.getDouble("UnitPrice"));
                System.out.println("Stock: " + results.getInt("UnitsInStock"));
                System.out.println("------------------");
            }
        }
    }
}
