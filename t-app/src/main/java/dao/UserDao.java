package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import model.User;
import utils.DatabaseConnection;

public class UserDao {

    // Method to add a new user
    public String addUser(User user) {
        String result = "Data entered successfully";
        String sql = "INSERT INTO users (username, password_hash, email, full_name, created_at, role) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword_hash());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getFull_name());
            ps.setTimestamp(5, user.getCreated_at());
            ps.setString(6, user.getRole());  // Set role here
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            result = "Data not entered";
        }
        return result;
    }

    // Method to add a new admin
    public String addAdmin(User user) {
        // Admins are added similarly to regular users
        return addUser(user); // Reuse addUser method
    }

    // Method to update an existing user
    public boolean updateUser(User user) throws SQLException {
        String edit = "UPDATE users SET username = ?, email = ?, full_name = ?, updated_at = ? WHERE num = ?";
        boolean rowUpdated;
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(edit)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getFull_name());
            ps.setTimestamp(4, user.getUpdated_at());
            ps.setInt(5, user.getNum());

            rowUpdated = ps.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    // Method to select a user by ID
    public User selectUser(int num) {
        User user = null;
        String select = "SELECT num, username, email, full_name, role FROM users WHERE num = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(select)) {
            ps.setInt(1, num);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String username = rs.getString("username");
                String email = rs.getString("email");
                String full_name = rs.getString("full_name");
                String role = rs.getString("role");
                user = new User(num, username, email, full_name, role); // Include role
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    // Method to get all users (role = 'user')
    public List<User> getAllUsers() {
        String selectAll = "SELECT * FROM users";
        List<User> users = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(selectAll)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int num = rs.getInt("num");
                String username = rs.getString("username");
                String password = rs.getString("password_hash");
                String email = rs.getString("email");
                String full_name = rs.getString("full_name");
                Timestamp created_at = rs.getTimestamp("created_at");
                Timestamp updated_at = rs.getTimestamp("updated_at");
                String role = rs.getString("role");

                User user = new User(num, username, password, email, full_name, created_at, updated_at, role);
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    // Method to delete a user by ID
    public void deleteUser(int num) throws SQLException {
        String delete = "DELETE FROM users WHERE num = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(delete)) {
            ps.setInt(1, num);
            ps.executeUpdate();
        }
    }

    // Method to validate user credentials
    public User validateUser(String username, String password) {
        User user = null;
        String query = "SELECT * FROM users WHERE username = ? AND password_hash = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int num = rs.getInt("num");
                String email = rs.getString("email");
                
                String role = rs.getString("role");
                Timestamp created_at = rs.getTimestamp("created_at");
                user = new User(num, username, password, email, created_at, role);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
    
    public boolean isEmailRegistered(String email) {
        String query = "SELECT COUNT(*) FROM users WHERE email = ?";
        boolean emailExists = false;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                emailExists = count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return emailExists;
    }
    
    public boolean updatePassword(User user) throws SQLException {
        String edit = "UPDATE users SET password_hash = ? WHERE email = ?";
        boolean rowUpdated;
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(edit)) {
            ps.setString(1, user.getPassword_hash());
            ps.setString(2, user.getEmail());

            rowUpdated = ps.executeUpdate() > 0;
        }
        return rowUpdated;
    }

}
