import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setUrl("jdbc:mysql://localhost:3306/sakila");
        dataSource.setUsername("root");
        dataSource.setPassword("password");

        try (Connection connection = dataSource.getConnection()) {

            DataManager dataManager = new DataManager(dataSource, connection);

            boolean exit = true;
            System.out.println("Connected to the database!");

            while (exit) {
                System.out.println("Welcome to Sakila!");
                System.out.println("1) Display actors by last name");
                System.out.println("2) See movies by actor first/last name");
                System.out.println("0) Exit");
                System.out.println("Select an option: ");

                String userSelection = scanner.nextLine();

                if (userSelection.equals("0")) {
                    System.out.println("Exiting.. Bye!");
                    exit = false;
                } else if (userSelection.equals("1")) {

                    System.out.println("What is your favorite actor's first name?");
                    System.out.print("\nPlease enter: ");

                    String inputFirstName = scanner.nextLine();

                    System.out.println("What is your favorite actor's last name?");
                    System.out.print("\nPlease enter: ");

                    String inputLastName = scanner.nextLine();
                    List<Actor> actors = dataManager.getActorsByName(inputFirstName, inputLastName);
                    if (actors.isEmpty()) {
                        System.out.println("No actors found! Try again!");
                    }

                    for (Actor actor : actors) {
                        System.out.println(actor.toString());
                    }

                } else if (userSelection.equals("2")) {
                    System.out.println("Please enter a id for actor to get movies: ");
                    int actorId = scanner.nextInt();
                    List<Film> films = dataManager.getFilmsByActorId(actorId);
                    if (films.isEmpty()) {
                        System.out.println("No films found for this actor.");
                    }

                    for (Film film : films) {
                        System.out.println(film.getTitle() + " (" + film.getReleaseYear() + ")");
                    }
                }
            }

        } catch (SQLException e) {
            throw new Error(e);
        }


    }

//    private static void displayActorsWithLastName(Connection connection, String inputLastName) throws SQLException {
//        String query = "SELECT first_name, last_name FROM actor WHERE last_name LIKE ?";
//
//        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//            preparedStatement.setString(1, "%" + inputLastName + "%");
//            ResultSet results = preparedStatement.executeQuery();
//
//            while (results.next()) {
//                String firstName = results.getString("first_name");
//                String lastName = results.getString("last_name");
//
//                System.out.println("First Name: " + firstName);
//                System.out.println("Last Name : " + lastName);
//                System.out.println("------------------");
//
//            }
//        }
//    }
//
//    private static void displayMoviesByActor(Connection connection, String inputFirstName, String inputLastName) throws SQLException {
//        String query = "SELECT film.title FROM actor JOIN film_actor ON actor.actor_id = film_actor.actor_id JOIN film ON film_actor.film_id = film.film_id WHERE actor.first_name LIKE ? AND actor.last_name LIKE ?";
//
//        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//            preparedStatement.setString(1, inputFirstName);
//            preparedStatement.setString(2, inputLastName);
//
//            ResultSet results = preparedStatement.executeQuery();
//
//            if (results.next()) {
//                System.out.println("Movies featuring " + inputFirstName + " " + inputLastName + ":");
//                do {
//                    String movieTitle = results.getString("title");
//                    System.out.println("- " + movieTitle);
//                }
//                while (results.next());
//            } else {
//                System.out.println("No matches!");
//            }
//        }
//    }
}

