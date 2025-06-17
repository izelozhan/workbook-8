import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DataManager {
    private BasicDataSource basicDataSource;

    public DataManager(BasicDataSource basicDataSource) {
        this.basicDataSource = basicDataSource;
    }

    public List<Actor> getActorsByName(String inputFirstName, String inputLastName) throws SQLException {
        Connection connection = basicDataSource.getConnection();

        List<Actor> actors = new ArrayList<>();

        String query = "SELECT actor_id, first_name, last_name FROM actor WHERE first_name LIKE ? AND last_name LIKE ?";
        //Connection connection = dataSource.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(  1, "%" + inputFirstName + "%");
            preparedStatement.setString(2, "%" + inputLastName + "%");

            ResultSet results = preparedStatement.executeQuery();

            while (results.next()) {
                int actorId = results.getInt("actor_id");
                String firstName = results.getString("first_name");
                String lastName = results.getString("last_name");

                Actor actor = new Actor(actorId, firstName, lastName);
                actors.add(actor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return actors;
    }

    public List<Film> getFilmsByActorId(int actorId) throws SQLException {
        Connection connection = basicDataSource.getConnection();

        List<Film> films = new ArrayList<>();

        String query = "SELECT f.film_id, f.title, f.description, f.release_year, f.length FROM film f JOIN film_actor fa ON f.film_id = fa.film_id WHERE fa.actor_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, actorId);

            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                int FilmID = result.getInt("film_id");
                String title = result.getString("title");
                String description = result.getString("description");
                int releaseYear = result.getInt("release_year");
                int length = result.getInt("length");

                Film film = new Film(FilmID, title, description, releaseYear, length);
                films.add(film);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return films;
    }
}
