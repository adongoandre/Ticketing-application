<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
// Set cache control headers to ensure the page is not cached
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", 0);
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Login</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <link rel="stylesheet" href="vendor/bootstrap/css/bootstrap.min.css">
    <link href="css/styles.css" rel="stylesheet"/>
</head>
<body class="text-black">
    <nav class="navbar navbar-expand-sm navbar-light mt-0 p-2 pb-0">
        <div class="container ms-3">
            <a class="navbar-brand me-5 logo-top" href="#">.LOGO</a>
        </div>
    </nav>
    <div class="d-flex align-items-center justify-content-center min-vh-100">
        <div class="container-sm d-flex flex-column flex-md-row align-items-center justify-content-center p-4 login-div">
            <div class="container-sm text-center mb-4 mb-md-0">
                <h1 class="m-2"><i>Welcome</i></h1>
                <h6><i>back</i></h6>
            </div>
            <div class="container-sm d-flex flex-column align-items-center justify-content-center col-12 col-md-6 p-4 order-1 order-md-0 login-box">
                <div class="top mb-3 text-white">
                    <h3 class="mb-0 login-text-top">Login</h3>
                </div>
                <form action="<%= request.getContextPath() %>/user?action=login" method="post" class="d-flex flex-column input m-5 mb-2 align-items-center justify-content-center w-100">
                    <input type="text" class="form-control mb-3" name="username" placeholder="Username" required>
                    <input type="password" class="form-control mb-3" name="password" placeholder="Password" required >
                    <div class="d-flex w-100">
                        <a class="note" href="index.jsp">Create an account.</a>
                        <a class="ms-auto note" href="forgot-password.jsp">Forgot password?</a>
                    </div>
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
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
</body>
</html>
