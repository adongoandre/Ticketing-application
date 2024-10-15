package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.Ticket;
import utils.DatabaseConnection;

public class TicketDao {

    // Add a ticket
    public String addTicket(Ticket ticket) throws IOException {
        String result = "Data entered successfully";
        String sql = "INSERT INTO tickets (ticket_id, user_id, description_, image_description, priority, status_, created_at, satisfaction) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, ticket.getTicket_id());
            ps.setInt(2, ticket.getUser_id());
            ps.setString(3, ticket.getDescription_());
            ps.setBytes(4, ticket.getImage());
            ps.setString(5, ticket.getPriority());
            ps.setString(6, ticket.getStatus());
            ps.setTimestamp(7, ticket.getCreated_at());
            ps.setString(8, ticket.getSatisfaction());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            result = "Data not entered";
        }
        return result;
    }

    // Update ticket priority and status
    public boolean adminUpdate(Ticket ticket) throws SQLException, IOException {
        String edit = "UPDATE tickets SET priority = ?, status_ = ? WHERE num = ?";
        boolean rowUpdated;
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(edit)) {
            ps.setString(1, ticket.getPriority());
            ps.setString(2, ticket.getStatus());
            ps.setInt(3, ticket.getNum());

            rowUpdated = ps.executeUpdate() > 0;
        }
        return rowUpdated;
    }
    
    public boolean satisfactionUpdate(Ticket ticket) throws SQLException, IOException {
        String edit = "UPDATE tickets SET satisfaction = ? WHERE num = ?";
        boolean rowUpdated;
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(edit)) {
            ps.setString(1, ticket.getSatisfaction());
            ps.setInt(2, ticket.getNum());

            rowUpdated = ps.executeUpdate() > 0;
        }
        return rowUpdated;
    }
    
    // Update ticket details
    public boolean updateTicket(Ticket ticket) throws SQLException, IOException {
        String edit = "UPDATE tickets SET description_ = ?, image_description = ?, priority = ?, updated_at = ? WHERE num = ?";
        boolean rowUpdated;
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(edit)) {
            ps.setString(1, ticket.getDescription_());
            ps.setBytes(2, ticket.getImage());
            ps.setString(3, ticket.getPriority());
            ps.setTimestamp(4, ticket.getUpdated_at());
            ps.setInt(5, ticket.getNum());

            rowUpdated = ps.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    // Select a specific ticket
    public Ticket selectTicket(int num) {
        Ticket ticket = null;
        String select = "SELECT * FROM tickets WHERE num = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(select)) {
            ps.setInt(1, num);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                long ticket_id = rs.getLong("ticket_id");
                String description = rs.getString("description_");
                byte[] imageBytes = rs.getBytes("image_description");
                String priority = rs.getString("priority");
                String status = rs.getString("status_");
                Timestamp created_at = rs.getTimestamp("created_at");
                Timestamp updated_at = rs.getTimestamp("updated_at");

                ticket = new Ticket(num, ticket_id, rs.getInt("user_id"), description, imageBytes, priority, status, created_at, updated_at);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ticket;
    }

    // Retrieve all tickets
    public List<Ticket> getAllTickets() {
        String selectAll = "SELECT * FROM tickets";
        List<Ticket> tickets = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(selectAll);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                tickets.add(mapResultSetToTicket(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    // Retrieve tickets by user ID
    public List<Ticket> getTicketsByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM tickets WHERE user_id = ?";
        List<Ticket> tickets = new ArrayList<>();
        
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                tickets.add(mapResultSetToTicket(rs));
            }
        }
        return tickets;
    }
    public List<Ticket> getTicketsBypriority(String priority) throws SQLException {
        String sql = "SELECT * FROM tickets WHERE priority = ?";
        List<Ticket> tickets = new ArrayList<>();
        
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, priority);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                tickets.add(mapResultSetToTicket(rs));
            }
        }
        return tickets;
    }
    public List<Ticket> getTicketsBystatus(String status) throws SQLException {
        String sql = "SELECT * FROM tickets WHERE status_ = ?";
        List<Ticket> tickets = new ArrayList<>();
        
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                tickets.add(mapResultSetToTicket(rs));
            }
        }
        return tickets;
    }
    public List<Ticket> getTicketsBysatisfaction(String satisfaction) throws SQLException {
        String sql = "SELECT * FROM tickets WHERE satisfaction = ?";
        List<Ticket> tickets = new ArrayList<>();
        
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, satisfaction);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                tickets.add(mapResultSetToTicket(rs));
            }
        }
        return tickets;
    }

    // Retrieve tickets by user ID and status
    public List<Ticket> getTicketsByUserIdAndStatus(int userId, String status) throws SQLException {
        String sql = "SELECT * FROM tickets WHERE user_id = ? AND status_ = ?";
        List<Ticket> tickets = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setString(2, status);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                tickets.add(mapResultSetToTicket(rs));
            }
        }
        return tickets;
    }
    
    public List<Ticket> getStatusandSatisfaction(int userId, String status, String satisfaction) throws SQLException {
        String sql = "SELECT * FROM tickets WHERE user_id = ? AND status_ = ? AND satisfaction = ?";
        List<Ticket> tickets = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setString(2, status);
            ps.setString(3, satisfaction);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                tickets.add(mapResultSetToTicket(rs));
            }
        }
        return tickets;
    }
    
    public List<Ticket> getPriority(int userId, String priority) throws SQLException {
        String sql = "SELECT * FROM tickets WHERE user_id = ? AND priority = ?";
        List<Ticket> tickets = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setString(2, priority);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                tickets.add(mapResultSetToTicket(rs));
            }
        }
        return tickets;
    }
    
    public List<Ticket> getSatisfaction(int userId, String satisfaction) throws SQLException {
        String sql = "SELECT * FROM tickets WHERE user_id = ? AND satisfaction = ?";
        List<Ticket> tickets = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setString(2, satisfaction);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                tickets.add(mapResultSetToTicket(rs));
            }
        }
        return tickets;
    }
    

    // Delete a ticket
    public boolean deleteTicket(int num) throws SQLException {
        String delete = "DELETE FROM tickets WHERE num = ?";
        boolean rowDeleted;
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(delete)) {
            ps.setInt(1, num);
            rowDeleted = ps.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    // Map ResultSet to Ticket
    private Ticket mapResultSetToTicket(ResultSet rs) throws SQLException {
        int num = rs.getInt("num");
        long ticket_id = rs.getLong("ticket_id");
        int userId = rs.getInt("user_id");
        String description = rs.getString("description_");
        byte[] imageBytes = rs.getBytes("image_description");
        String priority = rs.getString("priority");
        String status = rs.getString("status_");
        Timestamp created_at = rs.getTimestamp("created_at");
        Timestamp updated_at = rs.getTimestamp("updated_at");
        String satisfaction = rs.getString("satisfaction");

        return new Ticket(num, ticket_id, userId, description, imageBytes, priority, status, created_at, updated_at, satisfaction);
    }
}
