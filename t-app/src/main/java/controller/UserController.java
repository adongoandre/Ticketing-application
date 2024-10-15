package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDao;
import model.User;

@WebServlet("/user")
public class UserController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDao userDao;

    public UserController() {
        this.userDao = new UserDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        try {
            switch (action) {
                case "view":
                    if (checkSession(request, response)) {
                        viewUser(request, response);
                    }
                    break;
                case "list":
                    if (checkSession(request, response)) {
                        listUsers(request, response);
                    }
                    break;
                case "delete":
                    if (checkSession(request, response)) {
                        deleteUser(request, response);
                    }
                    break;
                case "logout":
                    logoutUser(request, response);
                    break;
                default:
                    if (checkSession(request, response)) {
                        listUsers(request, response);
                    }
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "insert";
        }

        try {
            switch (action) {
                case "login":
                    loginUser(request, response);
                    break;
                case "insert":
                    insertUser(request, response);
                    break;
                case "adminInsert":
                    insertAdmin(request, response);
                    break;
                case "update":
                    if (checkSession(request, response)) {
                        updateUser(request, response);
                    }
                    break;
                default:
                    if (checkSession(request, response)) {
                        listUsers(request, response);
                    }
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void listUsers(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
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
            List<User> users = userDao.getAllUsers(); // Get users with role 'user'
            request.setAttribute("users", users);
            request.getRequestDispatcher("Admin-users.jsp").forward(request, response);
        } else {
            // If the user is not an admin, show an access denied page or error message
            response.sendRedirect("access-denied.jsp");
        }
    }

    private void viewUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("id"));
        User user = userDao.selectUser(userId);

        // Split full name into first name and last name
        String[] nameParts = user.getFull_name().split(" ", 2);
        String firstName = nameParts.length > 0 ? nameParts[0] : "";
        String lastName = nameParts.length > 1 ? nameParts[1] : "";

        request.setAttribute("user", user);
        request.setAttribute("first_name", firstName);
        request.setAttribute("last_name", lastName);

        request.getRequestDispatcher("Profile.jsp").forward(request, response);
    }

    private void insertUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String first_name = request.getParameter("first_name");
        String last_name = request.getParameter("last_name");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password_hash = request.getParameter("password");
        String full_name = first_name + " " + last_name;
        Timestamp created_at = new Timestamp(System.currentTimeMillis());
        String role = "user";

        User newUser = new User(username, password_hash, email, full_name, created_at, role);
        userDao.addUser(newUser);

        String referrer = request.getHeader("referer");
        if (referrer != null && referrer.endsWith("index.jsp")) {
            response.sendRedirect("ticketstatus");
        } else {
            response.sendRedirect(referrer);
        }
    }
    
    private void insertAdmin(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String first_name = request.getParameter("first_name");
        String last_name = request.getParameter("last_name");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password_hash = request.getParameter("password");
        String full_name = first_name + " " + last_name;
        Timestamp created_at = new Timestamp(System.currentTimeMillis());
        String role = "admin";

        User newUser = new User(username, password_hash, email, full_name, created_at, role);
        userDao.addUser(newUser);

        String referrer = request.getHeader("referer");
        if (referrer != null && referrer.endsWith("index.jsp")) {
            response.sendRedirect("ticketstatus");
        } else {
            response.sendRedirect(referrer);
        }
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int num = Integer.parseInt(request.getParameter("num"));
        String full_name = request.getParameter("first_name") + " " + request.getParameter("last_name");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        Timestamp updated_at = new Timestamp(System.currentTimeMillis());

        User user = new User(num, username, email, full_name, updated_at);
        userDao.updateUser(user);
        String referrer = request.getHeader("referer");
        if (referrer != null && referrer.endsWith("user?action=list")) {
            response.sendRedirect("user?action=list");
        } else {
            response.sendRedirect(referrer);
        }
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int num = Integer.parseInt(request.getParameter("id"));
        userDao.deleteUser(num);
        String referrer = request.getHeader("referer");
        if (referrer != null && referrer.endsWith("user?action=list")) {
            response.sendRedirect("user?action=list");
        } else {
        	HttpSession session = request.getSession(false); // Fetch session if it exists
            if (session != null) {
                session.invalidate(); // Invalidate session
            }

            // Set headers to prevent caching
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
            response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
            response.setDateHeader("Expires", 0); // Proxies.

            response.sendRedirect("login.jsp"); // Redirect to login page
        }
    }

    private void loginUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = userDao.validateUser(username, password);

        if (user != null) {
            request.getSession().setAttribute("user", user);
            response.sendRedirect("ticketstatus"); // Redirect to a welcome or dashboard page
        } else {
            request.setAttribute("errorMessage", "Invalid username or password");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    private void logoutUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Fetch session if it exists
        if (session != null) {
            session.invalidate(); // Invalidate session
        }

        // Set headers to prevent caching
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setDateHeader("Expires", 0); // Proxies.

        response.sendRedirect("login.jsp"); // Redirect to login page
    }

    private boolean checkSession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return false;
        }
        return true;
    }
}
