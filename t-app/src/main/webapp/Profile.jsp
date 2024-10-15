<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
 
<html lang="en">

<head>
    <title>Title</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <link rel="stylesheet" href="vendor/bootstrap/css/bootstrap.min.css">
    <link href="css/styles.css" rel="stylesheet" />
</head>

<body class=" bg-dark text-black">
    <header class="text-white">
        <nav class="navbar navbar-expand-sm navbar-light bg-black p-4">
            <div class="container">
                <a class="navbar-brand logo-top me-5 " href="ticketstatus">.LOGO</a>
                <button class="navbar-toggler d-lg-none" type="button" data-bs-toggle="collapse"
                    data-bs-target="#collapsibleNavId" aria-controls="collapsibleNavId" aria-expanded="false"
                    aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="collapsibleNavId">
                    <ul class="navbar-nav ms-auto me-3 mt-2 mt-lg-0 gap-4">
                        <li class="nav-item">
                            <a class="nav-link " href="ticketstatus" aria-current="page">Dashboard</a>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="dropdownId" data-bs-toggle="dropdown"
                                aria-haspopup="true" aria-expanded="false">My Tickets</a>
                            <div class="dropdown-menu dropdown-menu-dark shadow animated--grow-in"
                                aria-labelledby="dropdownId">
                                <a class="dropdown-item" href="ticket?action=list1">Tickets</a>
                                <a class="dropdown-item" href="ticket?action=list2">Ticket History</a>
                            </div>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="ticket?action=list">Tickets</a>
                        </li>
                        <li class="nav-item">
						    <a class="nav-link" href="user?action=list">Users</a>
						</li>
                        <li class="nav-item dropdown active1">
                            <a class="nav-link dropdown-toggle" href="#" id="dropdownId" data-bs-toggle="dropdown"
                                aria-haspopup="true" aria-expanded="false">Profile</a>
                            <div class="dropdown-menu dropdown-menu-end dropdown-menu-dark shadow animated--grow-in"
                                aria-labelledby="dropdownId">
                                <a class="dropdown-item" href="user?action=view&id=<c:out value='${user.num}'/>">Edit Profile</a>
                                <hr class="dropdown-divider">
                                <a class="dropdown-item" href="#" data-bs-toggle="modal" data-bs-target="#logoutModal">
                                    Logout
                                </a>
                            </div>
                        </li>
                        <li class="nav-item">
                    		<button class="btn b-color"><c:out value="${user.username}"/></button>
						</li>
                    </ul>

                </div>
            </div>
        </nav>
    </header>
    <div class="mt-3 d-flex align-items-center justify-content-center min-vh-100">
        <div
            class="container-sm d-flex flex-column flex-md-row align-items-center justify-content-center p-4 login-div">
            <div class="container-sm text-center col mb-4 mb-md-0">
                <h1 class="m-2"><i>Update Your Profile</i></h1>
            </div>
            <div class="container-sm d-flex flex-column align-items-center justify-content-center col-12 col-md-7 p-4 order-1 order-md-0 login-box">
	            <form action="user?action=update" method="post" class="mt-2 mb-2 d-flex flex-column align-items-center justify-content-center box1">
				    <input type="hidden" name="num" value="<c:out value='${user.num}'/>">
				    
				    <div class="row align-items-center mb-4 box1">
				        <div class="col-12 col-sm-auto">
				            <i class="h4 text-white">Change Firstname</i>
				        </div>
				        <div class="col-12 col-sm">
				            <input type="text" name="first_name" value='${first_name}' class="form-control mb-3" required>
				        </div>
				    </div>
				    
				    <div class="row align-items-center mb-4 box1">
				        <div class="col-12 col-sm-auto">
				            <i class="h4 text-white">Change Lastname</i>
				        </div>
				        <div class="col-12 col-sm">
				            <input type="text" name="last_name" value='${last_name}' class="form-control mb-3" required>
				        </div>
				    </div>
				    
				    <div class="row align-items-center mb-4 box1">
				        <div class="col-12 col-sm-auto">
				            <i class="h4 text-white">Change Username</i>
				        </div>
				        <div class="col-12 col-sm">
				            <input type="text" name="username" value='${user.username}' class="form-control mb-3" required>
				        </div>
				    </div>
				    
				    <div class="row align-items-center mb-4 box1">
				        <div class="col-12 col-sm-auto">
				            <i class="h4 text-white">Change Email Address</i>
				        </div>
				        <div class="col-12 col-sm">
				            <input type="text" name="email" value='${user.email}' class="form-control mb-3" required>
				        </div>
				    </div>
				    
				    <div class="d-sm-flex align-items-center justify-content-between">
					    <button type="submit" class="btn login-btn align-self-center justify-content-center">Save</button>
					    <h3 class="text-white"> / </h3>
					    <a href="user?action=delete&id=<c:out value='${user.num}'/>" class="btn btn-dark" onclick="return confirm('Are you sure you want to delete your account?');">Delete</a>
				    </div>
				    
				</form>

            </div>
        </div>
    </div>


    <!-- Logout Modal-->
    <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="logoutModalLabel" aria-hidden="true">
	    <div class="modal-dialog" role="document">
	        <div class="modal-content bg-dark text-light">
	            <div class="modal-header">
	                <h5 class="modal-title" id="logoutModalLabel">Ready to Leave?</h5>
	                <button class="btn-close ms-auto bg-dark" type="button" data-bs-dismiss="modal" aria-label="Close"></button>
	            </div>
	            <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
	            <div class="modal-footer">
	                <button class="btn btn-secondary" type="button" data-bs-dismiss="modal">Cancel</button>
	                <a class="btn logout" href="user?action=logout">Logout</a>
	            </div>
	        </div>
	    </div>
	</div>


    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>

    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    <script src="js/scripts.js"></script>
</body>

</html>