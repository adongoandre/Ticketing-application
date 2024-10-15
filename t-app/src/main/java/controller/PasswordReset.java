package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDao;
import model.User;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/reset-password")
public class PasswordReset extends HttpServlet {
    private UserDao userDao;

    public PasswordReset() {
        this.userDao = new UserDao();
    } 
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "emailCheck";
        }

        try {
            switch (action) {
                case "emailCheck":
                    checkEmail(request, response);
                    break;
                case "update":
                    updatePassword(request, response);
                    break;
                default:
                    checkEmail(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void checkEmail(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        String email = request.getParameter("email");
        boolean emailExists = userDao.isEmailRegistered(email);

        if (emailExists) {
            HttpSession session = request.getSession();
            session.setAttribute("resetEmail", email);
            request.setAttribute("emailExists", true);
            request.getRequestDispatcher("reset-password.jsp").forward(request, response);
        } else {
        	request.setAttribute("errorMessage", "Unknown Email address");
        	request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
        }
    }

    private void updatePassword(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("resetEmail");
        String newPassword = request.getParameter("newPassword");

        // Hash the password (you should add a proper hashing mechanism)
        String hashedPassword = newPassword; // Placeholder for the actual hashing logic

        User user = new User();
        user.setEmail(email);
        user.setPassword_hash(hashedPassword);

        boolean success = userDao.updatePassword(user);

        if (success) {
            session.removeAttribute("resetEmail");
            request.setAttribute("passwordUpdated", true);
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            request.setAttribute("passwordUpdated", false);
            request.getRequestDispatcher("reset-password.jsp").forward(request, response);
        }
    }
}
