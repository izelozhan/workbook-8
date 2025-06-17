import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        Scanner scanner = new Scanner(System.in);

        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setUrl("jdbc:mysql://localhost:3306/northwind");
        dataSource.setUsername(args[0]);
        dataSource.setPassword(args[1]);

        DataManager dataManager = new DataManager(dataSource);

        while (true) {
            try{
                System.out.println("Choose an option: ");
                System.out.println("1) Display All Shippers");
                System.out.println("2) Create New Shipper");
                System.out.println("3) Search by Shipper ID");
                System.out.println("4) Delete Shipper");
                System.out.println("5) Update Shipper");
                System.out.println("0) Exit");

                String choice = scanner.nextLine();

                if (choice.equals("1")) {
                    dataManager.list().forEach(System.out::println);
                } else if (choice.equals("2")) {
                    System.out.println("Please enter company name: ");
                    String companyName = scanner.nextLine();
                    System.out.println("Please enter a phone number: ");
                    String phone = scanner.nextLine();

                    Shipper shipper = dataManager.create(companyName, phone);
                    System.out.println("Shipper is created! #" + shipper.getShipperId());
                } else if (choice.equals("3")) {
                    System.out.println("Please enter shipper id to search");
                    int id = scanner.nextInt();

                    Shipper shipper = dataManager.get(id);
                    if (shipper == null) {
                        throw new RuntimeException("Shipper is not found");
                    } else {
                        System.out.println(shipper);
                    }
                } else if (choice.equals("4")) {
                    System.out.println("Please enter shipper id to delete");
                    int id = scanner.nextInt();

                    Shipper shipper = dataManager.get(id);
                    if (shipper == null) {
                        throw new RuntimeException("Shipper is not found");
                    } else {

                        dataManager.delete(shipper);
                        System.out.println("Deleted!");
                    }
                } else if (choice.equals("5")) {
                    System.out.println("Please enter shipper id: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Please enter a phone number: ");
                    String phone = scanner.nextLine();

                    Shipper shipper = dataManager.get(id);
                    shipper.setPhone(phone);

                    dataManager.update(shipper);

                    System.out.println("Shipper is updated! #" + shipper.getShipperId());
                } else if (choice.equals("6")) {
                    break;
                }

            } catch (Exception e) {
                System.out.println("Error occured: " + e.getMessage());
            }
        }

    }
}
