package org.example.lab7.Repository.Database;

import org.example.lab7.Domain.User;
import org.example.lab7.Domain.UserDTO;
import org.example.lab7.Repository.AbstractRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class RepositoryUsersDatabase extends AbstractRepository<Integer, User> {

    private final List<User> users = new ArrayList<>();
    private Connection connection;
    private Statement statement;

    public RepositoryUsersDatabase(Connection connection) {
        try {
            this.connection = connection;
            this.statement = connection.createStatement();
            this.read();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<User> create(User user) throws SQLException {
        users.add(user);
        this.write(user,"add");
        return Optional.of(user);
    }

    @Override
    public Optional<User> update(User user) throws SQLException {
        User oldUser = findOne(user.getId()).get();
        oldUser.setFirstName(user.getFirstName());
        oldUser.setLastName(user.getLastName());
        oldUser.setEmail(user.getEmail());
        oldUser.setPassword(user.getPassword());
        this.write(user, "update");
        return Optional.of(oldUser);
    }

    @Override
    public Optional<User> delete(User user) throws SQLException {
        users.remove(user);
        this.write(user,"delete");
        return Optional.empty();
    }

    @Override
    public Optional<User> findOne(Integer id) {
        return users.stream()
                .filter(user -> Objects.equals(user.getId(), id))
                .findFirst();
    }

    public Optional<User> findByIdCredentials(Integer id_credentials) {
        return users.stream()
                .filter(user -> Objects.equals(user.get_idCredentials(), id_credentials))
                .findFirst();
    }

    @Override
    public Iterable<User> findAll() {
        return users;
    }

    public List<UserDTO> findAlLNames(Integer id){
        // SQL query with a placeholder for the user ID
        try {
            String query = "SELECT u.id_user, u.firstname, u.lastname " +
                    "FROM users u " +
                    "WHERE u.id_user != ? " + // Use a placeholder for the dynamic ID
                    "AND (" +
                    "    NOT EXISTS (" +
                    "        SELECT 1 " +
                    "        FROM friendships f " +
                    "        WHERE (f.id_user1 = u.id_user AND f.id_user2 = ?) " +
                    "        OR (f.id_user1 = ? AND f.id_user2 = u.id_user) " +
                    "    ) " +
                    "    OR EXISTS (" +
                    "        SELECT 1 " +
                    "        FROM friendship_status fs " +
                    "        WHERE (fs.id_user_request = u.id_user AND fs.id_user_receive = ?) " +
                    "        AND fs.status = 'rejected' " +
                    "    ) " +
                    "    AND NOT EXISTS (" +
                    "        SELECT 1 " +
                    "        FROM friendship_status fs " +
                    "        WHERE (fs.id_user_request = u.id_user AND fs.id_user_receive = ?) " +
                    "        AND fs.status = 'pending' " +
                    "    )" +
                    ");";

            // Prepare the statement
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Set the dynamic parameters
            preparedStatement.setInt(1, id);  // Set the user ID (for != 1)
            preparedStatement.setInt(2, id);  // Set the user ID (for id_user1 = ?)
            preparedStatement.setInt(3, id);  // Set the user ID (for id_user2 = ?)
            preparedStatement.setInt(4, id);  // Set the user ID (for id_user_request = ?)
            preparedStatement.setInt(5, id);  // Set the user ID (for id_user_receive = ?)

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            List<UserDTO> list = new ArrayList<>();
            // Process the results
            while (resultSet.next()) {
                int userId = resultSet.getInt("id_user");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");

                UserDTO user = new UserDTO(userId, firstname + " " + lastname);
                list.add(user);
            }
            return list;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public Integer findCredentialId(String email, String password)  {
        try {
            String query = "select id_credentials from credentials where email = ? and password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id_credentials");
            } else
                return -1;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    //verifica daca exista inregistrari cu emailul dat
    public boolean findEmail(String email) {
        try {
            String query = "select id_credentials from credentials where email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            } else
                return false;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //verifica daca exista inregistrari cu emailul dat facand exceptie de la utilizatorul cu id-ul dat
    public boolean findEmailExceptId(String email, Integer id) {
        try {
            String query = "select id_credentials from credentials where email = ? and id_credentials != ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setInt(2, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            } else
                return false;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean findPassword(String password) {
        try {
            String query = "select id_credentials from credentials where password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            } else
                return false;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean findPasswordExceptId(String password, Integer id) {
        try {
            String query = "select id_credentials from credentials where password = ? and id_credentials != ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, password);
            preparedStatement.setInt(2, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            } else
                return false;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void read() throws SQLException {
        String query = """
    SELECT u.id_user, u.firstname, u.lastname, u.bot, c.email, c.password, c.id_credentials
    FROM users u
    LEFT JOIN credentials c ON u.id_credentials = c.id_credentials
    """;

        try (ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id_user");
                System.out.println(id);
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");
                String bot = resultSet.getString("bot");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                Integer id_credentials = resultSet.getInt("id_credentials");

                // Create a new User object
                User user = new User(firstName, lastName, email, password);
                if (bot!=null)
                    user.set_bot(bot);
                user.setId(id);  // Set the user's ID
                user.set_idCredentials(id_credentials);

                // Add user to the collection
                users.add(user);
            }
        }
    }

    @Override
    public void write(User user,  String operationType) throws SQLException {
        switch (operationType) {
            case "add":
                add_database(user);
                break;
            case "delete":
                delete_database(user);
                break;
            case "update":
                update_database(user);
                break;
        }
    }

   private void add_database(User user) throws SQLException {
        String query_add_credentials = "INSERT INTO credentials(email,password) values(?,?)";
       PreparedStatement preparedStatement_credentials = connection.prepareStatement(query_add_credentials);
       preparedStatement_credentials.setString(1, user.getEmail());
       preparedStatement_credentials.setString(2, user.getPassword());
       preparedStatement_credentials.execute();
       preparedStatement_credentials.close();

       String query_select_id1 = "SELECT Id_credentials FROM credentials where email = ? and password = ?";
       PreparedStatement preparedStatement_select_id1 = connection.prepareStatement(query_select_id1);
       preparedStatement_select_id1.setString(1, user.getEmail());
       preparedStatement_select_id1.setString(2, user.getPassword());
       ResultSet resultSet_select_id1 = preparedStatement_select_id1.executeQuery();
       if (resultSet_select_id1.next()) {
           user.set_idCredentials(resultSet_select_id1.getInt("Id_credentials"));
       }
       preparedStatement_select_id1.close();

       String query_add_user = "INSERT INTO users(firstname, lastname, id_credentials, bot) VALUES (?, ?, ?, ?)";
       PreparedStatement preparedStatement_user = connection.prepareStatement(query_add_user);
       preparedStatement_user.setString(1, user.getFirstName());
       preparedStatement_user.setString(2, user.getLastName());
       preparedStatement_user.setInt(3, user.get_idCredentials());
       preparedStatement_user.setString(4, user.get_bot());
       preparedStatement_user.execute();
       preparedStatement_user.close();


       String query_select_id2 = "SELECT id_user FROM users where id_credentials = ?";
       PreparedStatement preparedStatement_select_id2 = connection.prepareStatement(query_select_id2);
       preparedStatement_select_id2.setInt(1, user.get_idCredentials());
       ResultSet resultSet_select_id2 = preparedStatement_select_id2.executeQuery();
       if (resultSet_select_id2.next()) {
           user.setId(resultSet_select_id2.getInt("id_user"));
       }
       preparedStatement_select_id2.close();

   }

    private void delete_database(User user) throws SQLException {
        String query = "DELETE FROM credentials WHERE id_credentials = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, user.get_idCredentials());
        preparedStatement.execute();
        preparedStatement.close();
    }

    private void update_database(User user) throws SQLException {
        String query1 = "UPDATE users SET firstname = ?, lastname = ? WHERE id_user = ?";
        PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
        preparedStatement1.setString(1, user.getFirstName());
        preparedStatement1.setString(2, user.getLastName());
        preparedStatement1.setInt(3, user.getId());
        preparedStatement1.execute();
        preparedStatement1.close();

        String query2 = "UPDATE credentials SET email = ?, password = ? WHERE id_credentials = ?";
        PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
        preparedStatement2.setString(1, user.getEmail());
        preparedStatement2.setString(2, user.getPassword());
        preparedStatement2.setInt(3, user.get_idCredentials());
        preparedStatement2.execute();
        preparedStatement2.close();
    }
}
