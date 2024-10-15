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
<!DOCTYPE html>
<html lang="en">

<head>
    <title>Title</title>
    <!-- Required meta tags -->
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <link rel="stylesheet" href="vendor/bootstrap/css/bootstrap.min.css">
    <link href="css/styles.css" rel="stylesheet"/>
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
</head>

<body id="page-top" class="bg-dark">
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
                            <a class="nav-link" href="ticketstatus" aria-current="page">Dashboard</a>
                        </li>
                        <c:if test="${user.role == 'user'}">
	                        <li class="nav-item dropdown">
	                            <a class="nav-link dropdown-toggle active1" href="#" id="dropdownId"
	                                data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">My Tickets</a>
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
        <div class="container-fluid mt-4 p-3 pb-0">
            <div class="d-flex align-items-center justify-content-between ">
                <h1 class="h3 mb-0 text-white-700">Tickets History</h1>
            </div>
        </div>
        
        
    </main>
    <div class="container mt-4">
    <p>Here is a list of all your resolved tickets. Please be sure to leave a remark <br>
    on your level of satisfaction on the resolution of your tickets. <br>
    Rate based on time taken to resolve your ticket and if the ticket resolved meets your requirement.</p>
    <br>
        <div class="table-responsive-xxl bg-light ">
            <table class="table table-bordered " id="ticketTable">
                <thead class="border-c">
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Description</th>
                        <th scope="col">Image</th>
                        <th scope="col">Priority</th>
                        <th scope="col">Status</th>
                        <th scope="col">Created_At</th>
                        <th scope="col">Updated_At</th>
                        <th scope="col">Are you Satisfied</th>
                    </tr>
                </thead>
                <tbody class="border-dark" id="userTableBody">
				    <c:forEach var="ticket" items="${tickets}">
				        <tr>
				            <td><c:out value="${ticket.ticket_id}"/></td>
				            <td><c:out value="${ticket.description_}"/></td>
				            <td>
				            	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							    <img src="${pageContext.request.contextPath}/ticket-image?num=${ticket.num}" 
							         alt="No ticket image" 
							         class="thumb img-thumbnail" 
							         style="width: 150px; height: auto; cursor: pointer;" 
							         data-bs-toggle="modal" 
							         data-bs-target="#imageModal" 
							         data-image-src="${pageContext.request.contextPath}/ticket-image?num=${ticket.num}" 
							         onclick="showImageInModal(this.getAttribute('data-image-src'))"/>
							</td>

				            <td><c:out value="${ticket.priority}"/></td>
				            <td><c:out value="${ticket.status}"/></td>
				            <td><c:out value="${ticket.created_at}"/></td>
				            <td><c:out value="${ticket.updated_at}"/></td>
				       		<td>
				       			<button type="button" class=" btn btn-sm b-color p-2 shadow-sm dropdown-toggle"
		                            data-bs-toggle="dropdown" aria-expanded="false" id="dropdownMenuLink"
		                            data-bs-auto-close="outside">
		                            Level of Satisfaction
		                        </button>
		                        <form action="ticket?action=satisfy" method="post" class="dropdown-menu px-4 py-3 " id="ticketForm">
								    <input type="hidden" name="num" id="ticketId" value="${ticket.num}">	
								    <div class="mt-4">
								        <label for="satisfaction">Satisfaction Level:</label>
								        <select name="satisfaction" id="satisfaction" class="form-control">
										    <option value="Very Satisfied" ${ticket.satisfaction == 'Very Satisfied' ? 'selected' : ''}>Very Satisfied</option>
										    <option value="Satisfied" ${ticket.satisfaction == 'Satisfied' ? 'selected' : ''}>Satisfied</option>
										    <option value="Neutral" ${ticket.satisfaction == 'Neutral' ? 'selected' : ''}>Neutral</option>
										    <option value="Dissatisfied" ${ticket.satisfaction == 'Dissatisfied' ? 'selected' : ''}>Dissatisfied</option>
										    <option value="Very Dissatisfied" ${ticket.satisfaction == 'Very Dissatisfied' ? 'selected' : ''}>Very Dissatisfied</option>
										</select>

								    </div>
								
								    <div class="mt-4 d-flex flex-column align-items-center justify-content-center">
								        <button type="submit" class="btn b-color">Submit</button>
								    </div>
								</form>
				       		</td>
				        </tr>
				    </c:forEach>
				</tbody>


            </table>
        </div>
    </div>
    <br>
    <br>


    <!-- Image Modal -->
    <div class="modal fade" id="imageModal" tabindex="-1" aria-labelledby="imageModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-body text-center">
                    <img id="imageModalContent" src="" alt="Ticket Image" class="img-fluid">
                </div>
            </div>
        </div>
    </div>
    <!-- Logout Modal-->
    <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
        aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content bg-dark text-light">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
                    <button class="close ms-auto bg-dark" type="button" data-bs-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">X</span>
                    </button>
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
