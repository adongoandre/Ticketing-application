<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    // Set cache control headers to ensure the page is not cached
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    // Check if the user is logged in
    
%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Title</title>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
        <link rel="stylesheet" href="vendor/bootstrap/css/bootstrap.min.css">
        <link href="css/styles.css" rel="stylesheet"/>
    </head>

    <body class="text-black">
    
        <nav class="navbar navbar-expand-sm navbar-light mt-0  p-2 pb-0">
            <div class="container ms-3 ">
                <a class="navbar-brand me-5 logo-top" href="#">.LOGO</a>
            </div>
        </nav>
        <div class="d-flex align-items-center justify-content-center min-vh-100">
            <div class="container-sm d-flex flex-column flex-md-row align-items-center justify-content-center p-4 login-div">
                <div class="container-sm text-center  mb-4 mb-md-0">
                    <h1 class="m-2"><i>Hey There</i></h1>
                    <h4><i>Welcome in new user</i></h4>
                </div>
                <div class="container-sm d-flex flex-column align-items-center justify-content-center col-12 col-md-6 p-4 order-1 order-md-0 login-box">
                    <div class="top mb-3 text-white"><h3 class="mb-0 login-text-top">Register</h3></div>
                        <form action="user?action=insert" method="post" class="input mt-5 mb-2 d-flex flex-column align-items-center justify-content-center w-100">
						    <div class="row">
						        <div class="col-12 col-md-6 mb-3">
						            <input type="text" name="first_name" class="form-control" placeholder="Firstname" required>
						        </div>
						        <div class="col-12 col-md-6 mb-3">
						            <input type="text" name="last_name" class="form-control" placeholder="Lastname" required>
						        </div>
						    </div>
						    <input type="text" name="username" class="form-control mb-3" placeholder="Username" required>
						    <input type="email" name="email" class="form-control mb-3" placeholder="Email" required>
						    <input type="password" name="password" class="form-control mb-3" placeholder="Password" required >
						    <input type="password" name="confirm_password" class="form-control mb-3" placeholder="Confirm Password" required >
						    <div class="note d-flex w-100 mb-2">
						        <a class="note" href="login.jsp">Login to account</a>
						    </div>
						    <input type="hidden" name="created_at" id="created_at">
    						
						    <button type="submit" class="btn login-btn align-self-center justify-content-center">Submit</button>
						</form>

                    
                </div>
            </div>
        </div>
        
    



        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>

        <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
        <script type="js/scripts.js"></script>
    </body>
</html>


