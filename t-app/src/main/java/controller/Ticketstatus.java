package controller;

import dao.TicketDao;
import model.Ticket;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/ticketstatus")
public class Ticketstatus extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(Ticketstatus.class.getName());

    public Ticketstatus() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the current user's ID from session
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp"); // Redirect to login if user_id is not found
            return;
        }
        int userId = user.getNum();
        TicketDao ticketDao = new TicketDao();
        List<Ticket> tickets = null;
        List<Ticket> admintickets = null;
        
        
        try {
            tickets = ticketDao.getTicketsByUserId(userId);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving tickets", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving tickets");
            return;
        }
        String userName = user.getUsername();
        admintickets = ticketDao.getAllTickets();
        int allTickets = admintickets.size();
        int totalTickets = tickets.size();
        
        System.out.println("Total Tickets: " + totalTickets); // Debugging line
        
        if (totalTickets == 0) {
            totalTickets = 1; // Avoid division by zero
        }

        // Initialize status counts
        int openedCount = 0, reopenedCount = 0, onHoldCount = 0, closedCount = 0, inProgressCount = 0, cancelledCount = 0,
        		highCount = 0, mediumCount = 0, lowCount = 0, verySatisfied = 0, Satisfied = 0, neutral = 0, dissatisfied = 0,
        		veryDissatisfied = 0, allhigh = 0, allmedium = 0, allLow = 0, opened = 0, reopened = 0, onHold = 0, 
        		closed = 0, inProgress = 0, cancelled = 0, allverySatisfied = 0, allSatisfied = 0, allneutral = 0, alldissatisfied = 0,
                allveryDissatisfied = 0;

        // Retrieve and log status-specific ticket counts
        try {
            openedCount = ticketDao.getTicketsByUserIdAndStatus(userId, "Open").size();
            reopenedCount = ticketDao.getTicketsByUserIdAndStatus(userId, "Reopened").size();
            onHoldCount = ticketDao.getTicketsByUserIdAndStatus(userId, "On Hold").size();
            closedCount = ticketDao.getTicketsByUserIdAndStatus(userId, "Closed").size();
            inProgressCount = ticketDao.getTicketsByUserIdAndStatus(userId, "In Progress").size();
            cancelledCount = ticketDao.getTicketsByUserIdAndStatus(userId, "Cancelled").size();
            highCount = ticketDao.getPriority(userId, "High").size();
            mediumCount = ticketDao.getPriority(userId, "Medium").size();
            lowCount = ticketDao.getPriority(userId, "Low").size();
            verySatisfied = ticketDao.getSatisfaction(userId, "Very Satisfied").size();
            Satisfied = ticketDao.getSatisfaction(userId, "Satisfied").size();
            neutral = ticketDao.getSatisfaction(userId, "neutral").size();
            dissatisfied = ticketDao.getSatisfaction(userId, "dissatisfied").size();
            veryDissatisfied = ticketDao.getSatisfaction(userId, "Very Dissatisfied").size();
            opened = ticketDao.getTicketsBystatus("Open").size();
            reopened = ticketDao.getTicketsBystatus("Reopened").size();
            onHold = ticketDao.getTicketsBystatus("On Hold").size();
            closed = ticketDao.getTicketsBystatus("Closed").size();
            inProgress = ticketDao.getTicketsBystatus("In Progress").size();
            cancelled = ticketDao.getTicketsBystatus("Cancelled").size();
            allhigh = ticketDao.getTicketsBypriority("High").size();
            allmedium = ticketDao.getTicketsBypriority("Medium").size();
            allLow = ticketDao.getTicketsBypriority("Low").size();
            allverySatisfied = ticketDao.getTicketsBysatisfaction("Very Satisfied").size();
            allSatisfied = ticketDao.getTicketsBysatisfaction("Satisfied").size();
            allneutral = ticketDao.getTicketsBysatisfaction("neutral").size();
            alldissatisfied = ticketDao.getTicketsBysatisfaction("dissatisfied").size();
            allveryDissatisfied = ticketDao.getTicketsBysatisfaction("Very Dissatisfied").size();
            
            System.out.println("tickets: " + allTickets);
            System.out.println("Opened Count: " + openedCount);
            System.out.println("Reopened Count: " + reopenedCount);
            System.out.println("On Hold Count: " + onHoldCount);
            System.out.println("Closed Count: " + closedCount);
            System.out.println("In Progress Count: " + inProgressCount);
            System.out.println("Cancelled Count: " + cancelledCount);
            System.out.println("High Count: " + highCount);
            System.out.println("Medium Count: " + mediumCount);
            System.out.println("Low Count: " + lowCount);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving tickets by status", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving tickets by status");
            return;
        }

        // Calculate percentages
        double openedPercentage = Math.round((openedCount * 100.0) / totalTickets * 10.0) / 10.0;
        double reopenedPercentage = Math.round((reopenedCount * 100.0) / totalTickets * 10.0) / 10.0;
        double onHoldPercentage = Math.round((onHoldCount * 100.0) / totalTickets * 10.0) / 10.0;
        double closedPercentage = Math.round((closedCount * 100.0) / totalTickets * 10.0) / 10.0;
        double inProgressPercentage = Math.round((inProgressCount * 100.0) / totalTickets * 10.0) / 10.0;
        double cancelledPercentage = Math.round((cancelledCount * 100.0) / totalTickets * 10.0) / 10.0;
        double allopened = Math.round((opened * 100.0) / allTickets * 10.0) / 10.0;
        double allreopened = Math.round((reopened * 100.0) / allTickets * 10.0) / 10.0;
        double allonHold = Math.round((onHold * 100.0) / allTickets * 10.0) / 10.0;
        double allclosed = Math.round((closed * 100.0) / allTickets * 10.0) / 10.0;
        double allinProgress = Math.round((inProgress * 100.0) / allTickets * 10.0) / 10.0;
        double allcancelled = Math.round((cancelled * 100.0) / allTickets * 10.0) / 10.0;

        int satisfied = verySatisfied + Satisfied;
        int dissatisfaction = dissatisfied + veryDissatisfied;
        int allsatisfied = allverySatisfied + allSatisfied;
        int alldissatisfaction = alldissatisfied + allveryDissatisfied;
        // Log the percentages for debugging
        System.out.println("Opened Percentage: " + openedPercentage);
        System.out.println("Reopened Percentage: " + reopenedPercentage);
        System.out.println("On Hold Percentage: " + onHoldPercentage);
        System.out.println("Closed Percentage: " + closedPercentage);
        System.out.println("In Progress Percentage: " + inProgressPercentage);
        System.out.println("Cancelled Percentage: " + cancelledPercentage);

        // Set attributes to be used in the JSP
        request.setAttribute("userName", userName);
        request.setAttribute("totalTickets", totalTickets);
        request.setAttribute("allTickets", allTickets);
        request.setAttribute("openedPercentage", openedPercentage);
        request.setAttribute("reopenedPercentage", reopenedPercentage);
        request.setAttribute("onHoldPercentage", onHoldPercentage);
        request.setAttribute("closedPercentage", closedPercentage);
        request.setAttribute("inProgressPercentage", inProgressPercentage);
        request.setAttribute("cancelledPercentage", cancelledPercentage);
        request.setAttribute("highCount", highCount);
        request.setAttribute("mediumCount", mediumCount);
        request.setAttribute("lowCount", lowCount);
        request.setAttribute("satisfied", satisfied);
        request.setAttribute("neutral", neutral);
        request.setAttribute("dissatisfaction", dissatisfaction);
        request.setAttribute("allopened", allopened);
        request.setAttribute("allreopened", allreopened);
        request.setAttribute("allonHold", allonHold);
        request.setAttribute("allclosed", allclosed);
        request.setAttribute("allinProgress", allinProgress);
        request.setAttribute("allcancelled", allcancelled);
        request.setAttribute("allhigh", allhigh);
        request.setAttribute("allmedium", allmedium);
        request.setAttribute("allLow", allLow);
        request.setAttribute("allsatisfied", allsatisfied);
        request.setAttribute("allneutral", allneutral);
        request.setAttribute("alldissatisfaction", alldissatisfaction);
 
        // Forward to JSP to display progress bars
        request.getRequestDispatcher("Dashboard.jsp").forward(request, response);
    }
}
