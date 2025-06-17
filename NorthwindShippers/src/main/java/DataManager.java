import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private BasicDataSource basicDataSource;

    public DataManager(BasicDataSource basicDataSource) {
        this.basicDataSource = basicDataSource;
    }

    public List<Shipper> list() throws SQLException {
        Connection connection = basicDataSource.getConnection();

        List<Shipper> shippers = new ArrayList<>();

        String query = "SELECT shipperId, companyName, phone FROM shippers";

        try (Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                int shipperId = result.getInt("shipperId");
                String companyName = result.getString("companyName");
                String phone = result.getString("phone");

                Shipper shipper = new Shipper(shipperId, companyName, phone);
                shippers.add(shipper);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return shippers;
    }

    public Shipper create(String companyName, String phone) throws SQLException {
        // Create the connection and prepared statement
        Connection connection = basicDataSource.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("insert into shippers (companyName, phone) values (?, ?);", Statement.RETURN_GENERATED_KEYS);
        // set the parameter
        preparedStatement.setString(1, companyName);
        preparedStatement.setString(2, phone);
        // execute the query
        int rows = preparedStatement.executeUpdate();
        ResultSet ids = preparedStatement.getGeneratedKeys();
        Integer id = null;
        while (ids.next()){
            id = ids.getInt(1);
        }
        Shipper newShipper = new Shipper(id, companyName, phone);
        return newShipper;
    }

    public Shipper get(Integer shipperId) throws SQLException {
        Connection connection = basicDataSource.getConnection();

        PreparedStatement query = connection.prepareStatement("select shipperId, companyName, phone from shippers WHERE shipperId = ?");

        query.setInt(1, shipperId);
        ResultSet result = query.executeQuery();
        Shipper shipper = null;
        while (result.next()){
            shipper = new Shipper(result.getInt(1), result.getString(2), result.getString(3));
        }

        return shipper;
    }

    public void delete(Shipper shipper) throws SQLException {

            Connection connection = basicDataSource.getConnection();

            PreparedStatement query = connection.prepareStatement("DELETE FROM shippers WHERE shipperId = ?");

            query.setInt(1,shipper.getShipperId());
            int deletedCount = query.executeUpdate();

            if (deletedCount == 0){
                throw new RuntimeException("Unable to delete");
            }
    }

    public void update(Shipper shipper) throws SQLException {
        Connection connection = basicDataSource.getConnection();

        PreparedStatement query = connection.prepareStatement("UPDATE shippers SET phone = ? WHERE shipperId = ?");

        query.setString(1, shipper.getPhone());
        query.setInt(2, shipper.getShipperId());

        query.executeUpdate();
    }

//    Prompt the user for new shipper data (name and phone) and then insert it
//    into the shippers table. Display the new shipper id when the insert is
//    complete.
//2. Run a query and display all shippers
//3. Prompt the user to change the phone number of a shipper. They should
//    enter the id and the phone number.
//4. Run a query and display all  shippers
//5. Prompt the user to delete a shipper. DO NOT ENTER SHIPPERS 1-3.
//    They have related data in other tables. Delete your new shipper.
//6. Run a query and display all shipper
}
