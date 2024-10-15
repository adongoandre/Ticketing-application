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
    <title>Admin Users Management</title>
    <!-- Required meta tags -->
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <link rel="stylesheet" href="vendor/bootstrap/css/bootstrap.min.css">
    <link href="css/styles.css" rel="stylesheet" />
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
</head>

<body id="page-top" class="bg-dark">
    <header class="text-white">
        <nav class="navbar navbar-expand-lg navbar-light bg-black p-4">
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
                            <a class="nav-link" href="ticketstatus" aria-current="page">Dashboard</a>
                        </li>
                        <c:if test="${user.role == 'user'}">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="dropdownId" data-bs-toggle="dropdown"
                                aria-haspopup="true" aria-expanded="false">My Tickets</a>
                            <div class="dropdown-menu dropdown-menu-dark shadow animated--grow-in"
                                aria-labelledby="dropdownId">
                                <a class="dropdown-item" href="ticket?action=list1">Tickets</a>
                                <a class="dropdown-item" href="ticket?action=list2">Ticket History</a>
                            </div>
                        </li>
                        </c:if>
                        <c:if test="${user.role == 'admin'}">
	                        <li class="nav-item">
	                            <a class="nav-link" href="ticket?action=list">Tickets</a>
	                        </li>
	                        <li class="nav-item">
							    <a class="nav-link" href="user?action=list">Users</a>
							</li>
						</c:if>
                        <li class="nav-item dropdown">
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
    <main>
        <div class="container-fluid mt-4 p-3 pb-3">
            <div class="d-flex align-items-center justify-content-between">
                <h1 class="h3 mb-0 text-white-700">Admin Users Management</h1>
                <div>
                    <button type="button" class="btn btn-sm b-color p-2 shadow-sm" data-bs-toggle="modal" data-bs-target="#add-user">
                        Add User
                    </button>
                    <c:if test="${user.num == '1'}">
	                    <button type="button" class="btn btn-sm b-color p-2 shadow-sm" data-bs-toggle="modal" data-bs-target="#add-admin">
	                        Add Admin
	                    </button>
                    </c:if>
                </div>
            </div>
        </div>
    </main>
    <div class="container mt-4">
        <div class="table-responsive-xxl bg-light">
            <table class="table table-bordered" id="userTable">
                <thead class="border-c">
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Role</th>
                        <th scope="col">Username</th>
                        <th scope="col">Email</th>
                        <th scope="col">Full_Name</th>
                        <th scope="col">Created_At</th>
                        <th scope="col">Updated_At</th>
                        <th scope="col">Actions</th>
                    </tr>
                </thead>
                <tbody class="border-dark" id="userTableBody">
                    <c:forEach var="user" items="${users}">
                        <tr>
                            <td><c:out value="${user.num}"/></td>
                            <td><c:out value="${user.role}"/></td>
                            <td><c:out value="${user.username}"/></td>
                            <td><c:out value="${user.email}"/></td>
                            <td><c:out value="${user.full_name}"/></td>
                            <td><c:out value="${user.created_at}"/></td>
                            <td><c:out value="${user.updated_at}"/></td>
                            <td>
                            	&nbsp;&nbsp;&nbsp;&nbsp;
                                <a href="user?action=view&id=<c:out value='${user.num}'/>" class=" btn b-color">Edit</a>
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                <c:if test="${sessionScope.user.role == 'admin' && user.num != 1}">
		                            <a href="user?action=delete&id=<c:out value='${user.num}'/>" 
		                               class="btn btn-dark" 
		                               onclick="return confirm('Are you sure you want to delete this user?');">Delete</a>
		                        </c:if>

                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <!-- Add User Modal -->
    <div class="modal fade" id="add-user" tabindex="-1" role="dialog" aria-labelledby="addUserModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content bg-dark text-light">
                <div class="modal-header d-flex flex-sm-column align-items-center justify-content-center">
                    <h5 class="modal-title" id="addUserModalLabel">Add User</h5>
                </div>
                <div class="modal-body mb-2 d-flex flex-column align-items-center justify-content-center w-100">
                    <form action="user?action=insert" method="post" class="py-3 input" id="userForm">
                        <div class="row">
                            <div class="col-12 col-md-6 mb-3">
                                <input type="text" class="form-control" name="first_name" placeholder="First Name" required>
                            </div>
                            <div class="col-12 col-md-6 mb-3">
                                <input type="text" class="form-control" name="last_name" placeholder="Last Name" required>
                            </div>
                        </div>
                        <input type="text" class="form-control mb-3" name="username" placeholder="Username" required>
                        <input type="text" class="form-control mb-3" name="email" placeholder="Email" required>
                        <input type="password" class="form-control mb-3" name="password" placeholder="Password" required>
                        
                        <div class="mt-4 d-flex flex-row align-items-center justify-content-center gap-3">
                            <button type="submit" class="btn b-color">Add</button>
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <!-- Add Admin Modal -->
    <div class="modal fade" id="add-admin" tabindex="-1" role="dialog" aria-labelledby="addUserModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content bg-dark text-light">
                <div class="modal-header d-flex flex-sm-column align-items-center justify-content-center">
                    <h5 class="modal-title" id="addUserModalLabel">Add Admin</h5>
                </div>
                <div class="modal-body mb-2 d-flex flex-column align-items-center justify-content-center w-100">
                    <form action="user?action=adminInsert" method="post" class="py-3 input" id="userForm">
                        <div class="row">
                            <div class="col-12 col-md-6 mb-3">
                                <input type="text" class="form-control" name="first_name" placeholder="First Name" required>
                            </div>
                            <div class="col-12 col-md-6 mb-3">
                                <input type="text" class="form-control" name="last_name" placeholder="Last Name" required>
                            </div>
                        </div>
                        <input type="text" class="form-control mb-3" name="username" placeholder="Username" required>
                        <input type="text" class="form-control mb-3" name="email" placeholder="Email" required>
                        <input type="password" class="form-control mb-3" name="password" placeholder="Password" required>
                        
                        <div class="mt-4 d-flex flex-row align-items-center justify-content-center gap-3">
                            <button type="submit" class="btn b-color">Add</button>
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    
    <!-- Logout Modal -->
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
    <script src="js/1scripts.js"></script>
</body>

</html>
