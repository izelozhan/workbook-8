import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataManagerTest {

    @org.junit.Test
    public void testGetActorsByName()  {
        DataManager dm = new DataManager(null, null);

        try {
            List<Actor> actors = dm.getActorsByName("ed", "chase");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}