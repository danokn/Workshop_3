package pl.coderslab.entity;

import pl.coderslab.DbUtil;

import java.sql.*;
import java.util.Arrays;

public class UserDao {

    private static final String CREATE_USER_QUERY =
            "INSERT INTO users(username, email, password) VALUES (?, ?, ?);";

    public static final String FIND_USER_BY_ID =
            "SELECT * FROM users where id = ?;";

    public static final String UPDATE_USER =
            "UPDATE users SET username = ?, email = ?, password = ? WHERE id = ?;";

    public static final String DELETE_USER =
            "DELETE from users where id = ?;";

    public static final String FIND_ALL_USERS =
            "SELECT * FROM users";

//    public static void main() {
//        try (Connection conn = DbUtil.connectWorkshop2()) {
//            PreparedStatement preStmt =
//                    conn.prepareStatement(CREATE_USER_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
//            ResultSet rs = preStmt.getGeneratedKeys();
//            if (rs.next()) {
//                long id = rs.getLong(1);
//                System.out.println("Inserted ID: " + id);
//            }
//        }catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    public  String hashPassword(String password) {
        return org.mindrot.jbcrypt.BCrypt.hashpw(password, org.mindrot.jbcrypt.BCrypt.gensalt());
    }

    public  User create(User user) {
        try (Connection conn = DbUtil.connectWorkshop2()) {
            PreparedStatement statement =
                    conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.executeUpdate();
            //Pobieramy wstawiony do bazy identyfikator, a następnie ustawiamy id obiektu user.
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User read(int userId) {
        try (Connection conn = DbUtil.connectWorkshop2()) {
            PreparedStatement prStmt = conn.prepareStatement(FIND_USER_BY_ID);
            prStmt.setInt(1, userId);
            ResultSet rs = prStmt.executeQuery();
            if(rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.toString();

                return user;
            }
        }catch ( SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(User user) {
        try(Connection conn = DbUtil.connectWorkshop2()) {

            PreparedStatement prStmt = conn.prepareStatement(UPDATE_USER);
            prStmt.setString(1, user.getUserName());
            prStmt.setString(2, user.getEmail());
            prStmt.setString(3, hashPassword(user.getPassword()));
            prStmt.setInt(4, user.getId());
            prStmt.executeUpdate();

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int userId) {

        try(Connection conn = DbUtil.connectWorkshop2()) {

            PreparedStatement prStmt = conn.prepareStatement(DELETE_USER);
            prStmt.setInt(1, userId);
            prStmt.executeUpdate();

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User[] findAllUsers() {

        try(Connection conn = DbUtil.connectWorkshop2()) {

            PreparedStatement prStmt = conn.prepareStatement(FIND_ALL_USERS);
            ResultSet rs = prStmt.executeQuery();
            User[] users = new User[0];
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                users = addToArray(user, users);
            }
            return users;

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private User[] addToArray(User u, User[] users) {
        User[] tmpUsers = Arrays.copyOf(users, users.length + 1); // Tworzymy kopię tablicy powiększoną o 1.
        tmpUsers[users.length] = u; // Dodajemy obiekt na ostatniej pozycji.
        return tmpUsers; // Zwracamy nową tablicę.
    }









}
