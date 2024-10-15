<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="vendor/bootstrap/css/bootstrap.min.css">
    <link href="css/styles.css" rel="stylesheet"/>
	<title>Change Password</title>
</head>
<body>
	<nav class="navbar navbar-expand-sm navbar-light mt-0 p-2 pb-0">
        <div class="container ms-3">
            <a class="navbar-brand me-5 logo-top" href="#">.LOGO</a>
        </div>
    </nav>
    <div class="d-flex align-items-center justify-content-center min-vh-100">
        <div class="container-sm d-flex flex-column flex-md-row align-items-center justify-content-center p-4 login-div">
            
            <div class="container-sm d-flex flex-column align-items-center justify-content-center col-12 col-md-6 p-4 login-box">
                <div class="top mb-4 text-white">
                    <h3 class="mb-0 login-text-top">Change Password</h3>
                </div>
                <form action="reset-password?action=update" method="post" class="d-flex flex-column input mb-2 align-items-center justify-content-center w-100">
                    <input type="hidden" name="email" id="" value="${user.email}">
                    <input type="password" class="form-control mb-4" name="newPassword" placeholder="Enter new password" required>
                    <input type="password" class="form-control mb-4" name="confirmPassword" placeholder="Confirm new password" required>
                    <button type="submit" class="btn login-btn align-self-center justify-content-center">Submit</button>
                </form>
                <%
                    String errorMessage = (String) request.getAttribute("errorMessage");
                    if (errorMessage != null) {
                %>
                    <div class="alert alert-danger mt-3" role="alert">
                        <%= errorMessage %>
                    </div>
                <%
                    }
                %>
            </div>
        </div>
    </div>
</body>
</html>
