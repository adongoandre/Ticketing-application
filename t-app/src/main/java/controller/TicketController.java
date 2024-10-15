package controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import dao.TicketDao;
import model.Ticket;
import model.User;

@WebServlet("/ticket")
@MultipartConfig
public class TicketController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TicketDao ticketDao;

    public TicketController() {
        this.ticketDao = new TicketDao();
    }
    private static final long MIN = 0000000000L;
    private static final long MAX = 9999999999L;
    private static final Random random = new Random();
    private static final Set<Long> generatedNumbers = new HashSet<>();
    
    

    private long generateUniqueNumber() {
        long number;
        do {
            number = MIN + (long) (random.nextDouble() * (MAX - MIN));
        } while (generatedNumbers.contains(number));
        generatedNumbers.add(number);
        
        return number;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        try {
            switch (action) {
                case "view":
                    viewTicket(request, response);
                    break;
                case "list":
                    listTickets(request, response);
                    break;
                case "list1":
                    listTickets1(request, response);
                    break;
                case "list2":
                    listTickets2(request, response);
                    break;
                case "delete":
                    deleteTicket(request, response);
                    break;
                default:
                    listTickets(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException("Database error: " + e.getMessage(), e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "insert";
        }

        try {
            switch (action) {
                case "insert":
                    insertTicket(request, response);
                    break;
                case "update":
                    updateTicket(request, response);
                    break;
                case "admin":
                    adminUpdate(request, response);
                    break;
                case "satisfy":
                    satisfactionUpdate(request, response);
                    break;
                default:
                    listTickets(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException("Database error: " + e.getMessage(), e);
        }
    }

    private void listTickets(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
    	HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            // If no user is logged in, redirect to login page
            response.sendRedirect("login.jsp");
            return;
        }

        String role = user.getRole();

        if ("admin".equals(role)) {
            // Proceed with listing users if the user is an admin
        	List<Ticket> tickets = ticketDao.getAllTickets();            
        	request.setAttribute("tickets", tickets);
        	request.getRequestDispatcher("Admin-tickets.jsp").forward(request, response);
        } else {
            // If the user is not an admin, show an access denied page or error message
            response.sendRedirect("access-denied.jsp");
        }
    	
        
        
    }
    private void listTickets1(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user"); // Get the logged-in user
        
        if (user != null) {
            int userId = user.getNum(); // Get the user's ID
            List<Ticket> tickets = ticketDao.getTicketsByUserId(userId); // Filter tickets by user ID
            
            request.setAttribute("tickets", tickets);
            request.getRequestDispatcher("Ticket.jsp").forward(request, response);
        } else {
            response.sendRedirect("login.jsp"); // If no user is logged in, redirect to login page
        }
    }

    private void listTickets2(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user"); // Get the logged-in user
        
        if (user != null) {
            int userId = user.getNum(); // Get the user's ID
            String status = "closed"; // Only list completed tickets

            // Get all closed tickets without filtering by satisfaction
            List<Ticket> tickets = ticketDao.getTicketsByUserIdAndStatus(userId, status);

            // If you want to filter by satisfaction, ensure you pass the value:
            String satisfaction = request.getParameter("satisfaction"); // Or pass from somewhere else

            // Optionally filter by satisfaction if it's provided
            if (satisfaction != null && !satisfaction.isEmpty()) {
                tickets = ticketDao.getStatusandSatisfaction(userId, status, satisfaction);
            }

            request.setAttribute("tickets", tickets);
            request.getRequestDispatcher("Tickethistory.jsp").forward(request, response);
        } else {
            response.sendRedirect("login.jsp"); // If no user is logged in, redirect to login page
        }
    }



    private void viewTicket(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int ticketId = Integer.parseInt(request.getParameter("id"));
        Ticket ticket = ticketDao.selectTicket(ticketId);

        request.setAttribute("ticket", ticket);
        request.getRequestDispatcher("Ticket.jsp").forward(request, response);
    }

    private void insertTicket(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
    	HttpSession session = request.getSession();
    	User user =(User) session.getAttribute("user");
    	InputStream inputStream = null;
    	long ticketId = generateUniqueNumber();
    	String description = request.getParameter("description"); // Match form field name
        String priority = request.getParameter("priority");
        String status = "Open"; // Default status on ticket creation
        
        
        byte[] imagefile = null;
     // obtains the upload file part in this multipart request
        Part frontPhoto = request.getPart("image");
        if (frontPhoto != null && frontPhoto.getSize() > 0) {
            // debug messages
            System.out.println(frontPhoto.getName());
            System.out.println(frontPhoto.getSize());
            System.out.println(frontPhoto.getContentType());

            // obtains input stream of the upload file
            inputStream = frontPhoto.getInputStream();
            
            imagefile = toByteArray(inputStream);
                            
        }
        Timestamp created_at = new Timestamp(System.currentTimeMillis());
        String satisfaction = "Pending...";

        Ticket newTicket = new Ticket(ticketId, user.getNum(),description, imagefile, priority, status, created_at, satisfaction);
        String result = ticketDao.addTicket(newTicket);
        String referrer = request.getHeader("referer");
        
        if (result.equals("Data entered successfully")) {
        	if (referrer != null && referrer.endsWith("ticket?action=list")) {
                response.sendRedirect("ticket?action=list");
            } else {
                response.sendRedirect(referrer);
            }
        } else {
            response.getWriter().println("Failed to insert ticket: " + result);
        }
    }

    private void adminUpdate(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
    	int num = Integer.parseInt(request.getParameter("num"));
    	String priority = request.getParameter("priority");
    	String status = request.getParameter("status");
    	Ticket ticket = new Ticket(num, priority, status);
        ticketDao.adminUpdate(ticket);
        response.sendRedirect("ticket?action=list");
    }
    
    private void satisfactionUpdate(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
    	int num = Integer.parseInt(request.getParameter("num"));
    	String satisfaction = request.getParameter("satisfaction");
    	Ticket ticket = new Ticket(num, satisfaction);
        ticketDao.satisfactionUpdate(ticket);
        System.out.println("Updated ticket num: " + num + " with satisfaction: " + satisfaction);
        response.sendRedirect("ticket?action=list2");
    }
    	
    
    private void updateTicket(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        InputStream inputStream = null;

        // Retrieve the ticket ID and other parameters
        int num = Integer.parseInt(request.getParameter("num"));
        String description = request.getParameter("description");
        String priority = request.getParameter("priority");
        
        byte[] imagefile = null;
        // Obtain the upload file part in this multipart request
        Part frontPhoto = request.getPart("image");
        
        if (frontPhoto != null && frontPhoto.getSize() > 0) {
            // New image has been uploaded
            inputStream = frontPhoto.getInputStream();
            imagefile = toByteArray(inputStream);
        } else {
            // No new image uploaded, retain the existing image
            Ticket existingTicket = ticketDao.selectTicket(num);
            imagefile = existingTicket.getImage();
        }
        
        Timestamp updated_at = new Timestamp(System.currentTimeMillis());

        // Create a Ticket object with the new or existing image
        Ticket ticket = new Ticket(num, description, imagefile, priority, updated_at);
        ticketDao.updateTicket(ticket);
        
        // Redirect to the appropriate page after update
        response.sendRedirect("ticket?action=list1");
    }


    private void deleteTicket(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int num = Integer.parseInt(request.getParameter("id"));
        ticketDao.deleteTicket(num);
        
        String referrer = request.getHeader("referer");
        if (referrer != null && referrer.endsWith("ticket?action=list")) {
            response.sendRedirect("ticket?action=list");
        } else {
            response.sendRedirect(referrer);
        }
    }
    
    public static byte[] toByteArray(InputStream in) throws IOException
    {
        ByteArrayOutputStream os = new ByteArrayOutputStream();	 
        byte[] buffer = new byte[1024];
        int len;	 
        while ((len = in.read(buffer)) != -1)
        {
            os.write(buffer, 0, len);
        }	 
        return os.toByteArray();
    }
}
