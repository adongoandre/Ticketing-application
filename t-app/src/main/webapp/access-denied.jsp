<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    // Set cache control headers to ensure the page is not cached
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    // Check if the user is logged in
    if (session.getAttribute("user") == null) {
        response.sendRedirect("login.jsp"); // Redirect to login page if not logged in
        return;
    }
%> 
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Access Denied</title>
	<link rel="stylesheet" href="vendor/bootstrap/css/bootstrap.min.css">
    <link href="css/styles.css" rel="stylesheet"/>
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
    <style>
        .main-container {
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            text-align: center;
        }
        .icon {
            font-size: 5rem;
            color: #dc3545;
        }
    </style>
</head>
<body>
    <div class="container main-container">
        <div>
            <div class="icon">
                <i class="bi bi-x-circle"></i>
            </div>
            <h1 class="display-4">Access Denied</h1>
            <p class="lead">Sorry, you don't have permission to view this page.</p>
            <a href="ticketstatus" class="btn btn-primary">Go to Dashboard</a>
            <a href="user?action=logout" class="btn btn-secondary">Login</a>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
	<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="js/scripts.js"></script></body>
</html>
