<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
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
                            <a class="nav-link active1" href="ticketstatus" aria-current="page">Dashboard</a>
                        </li>
                        <c:if test="${user.role == 'user'}">    
	                        <li class="nav-item dropdown">
	                            <a class="nav-link dropdown-toggle" href="#" id="dropdownId" data-bs-toggle="dropdown"
	                                aria-haspopup="true" aria-expanded="false">My Tickets</a>
	                            <div class="dropdown-menu dropdown-menu-dark shadow animated--grow-in" aria-labelledby="dropdownId">
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
                            <div class="dropdown-menu dropdown-menu-end dropdown-menu-dark shadow animated--grow-in" aria-labelledby="dropdownId">
                                <a class="dropdown-item" href="user?action=view&id=<c:out value='${user.num}'/>">Edit Profile</a>
                                <hr class="dropdown-divider">
                                <a class="dropdown-item" href="#" data-bs-toggle="modal" data-bs-target="#logoutModal">
                                    Logout
                                </a>
                            </div>
                        </li>
                    	<li class="nav-item">
                    		<button class="btn b-color nav-link"><c:out value="${user.username}"/></button>
						</li>
                    </ul>
                    
                </div>
            </div>
        </nav>   
    </header>
   
    <main>
        <div class="container-fluid mt-4 p-3">
            <div class="d-sm-flex align-items-center justify-content-between mb-5">
                <h1 class="h3 mb-0 text-white-700">Dashboard</h1>
                <div>
                    <div class="dropdown d-none d-sm-inline-block">
                    	<c:if test="${user.role == 'user'}"> 
                        <button type="button" class=" btn btn-sm b-color p-2 shadow-sm dropdown-toggle"
                            data-bs-toggle="dropdown" aria-expanded="false" id="dropdownMenuLink"
                            data-bs-auto-close="outside">
                            + Add Ticket
                        </button>
                        </c:if>
                        <form action="ticket?action=insert" method="post" enctype="multipart/form-data" class="dropdown-menu px-4 py-3 add-ticket" id="ticketForm">
						    <div class="mt-2 d-flex flex-column align-items-center justify-content-center">
						        <textarea class="form-control length text-black" name="description" id="ticket" placeholder="...."></textarea>
						    </div>
						    <div class="mt-4">
						        <div class="form-check">
						            <label>
						                <input type="checkbox" class="priority-checkbox" name="priority" value="High"> High Priority
						            </label>
						            <label>
						                <input type="checkbox" class="priority-checkbox" name="priority" value="Medium"> Medium Priority
						            </label>
						            <label>
						                <input type="checkbox" class="priority-checkbox" name="priority" value="Low"> Low Priority
						            </label>
						        </div>
						    </div>
						    <div class="mt-4 d-flex flex-column align-items-center justify-content-center">
						        <input type="file" name="image" class="form-control-file" id="image">
						    </div>
						    <div class="mt-4 d-flex flex-column align-items-center justify-content-center">
						        <button type="submit" class="btn b-color">Submit</button>
						    </div>
						</form>

				

                    </div>
                    <a href="#" class="d-none d-sm-inline-block btn btn-sm btn-secondary p-2 shadow-sm">Generate Report</a>
                </div>
            </div>
        </div>
        <!-- Ticket Priority -->
        <div class="row m-4 pt-0">
            <div class="col-xl-3 col-md-6 mb-4">
                <div class="card h-100 border-c py-2">
                    <div class="card-body">
                        <div class="row no-gutters align-items-center">
                            <div class="col ms-2">
                                <div class="font-weight-bold text-black text-uppercase mb-1">
                                    High Priority</div>
                                <c:if test="${user.role == 'user'}"><div class="h5 mb-0 font-weight-bold text-danger" id="medium">${highCount}</div></c:if>
                                <c:if test="${user.role == 'admin'}"><div class="h5 mb-0 font-weight-bold text-danger" id="medium">${allhigh}</div></c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-xl-3 col-md-6 mb-4">
                <div class="card border-c h-100 py-2">
                    <div class="card-body">
                        <div class="row no-gutters align-items-center">
                            <div class="col mr-2">
                                <div class="text-lg font-weight-bold text-black text-uppercase mb-1">
                                    Medium Priority</div>
                                <c:if test="${user.role == 'user'}"><div class="h5 mb-0 font-weight-bold text-warning">${mediumCount}</div></c:if>
                                <c:if test="${user.role == 'admin'}"><div class="h5 mb-0 font-weight-bold text-warning">${allmedium}</div></c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-xl-3 col-md-6 mb-4">
                <div class="card border-c h-100 py-2">
                    <div class="card-body">
                        <div class="row no-gutters align-items-center">
                            <div class="col mr-2">
                                <div class="text-lg font-weight-bold text-black text-uppercase mb-1">
                                    Low Priority</div>
                                <c:if test="${user.role == 'user'}"><div class="h5 mb-0 font-weight-bold text-success">${lowCount}</div></c:if>
                                <c:if test="${user.role == 'admin'}"><div class="h5 mb-0 font-weight-bold text-success">${allLow}</div></c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-xl-3 col-md-6 mb-4">
                <div class="card border-c h-100 py-2">
                    <div class="card-body d-flex">
                        <div class="row no-gutters align-items-center">
                            <div class="col mr-2">
                                <div class="text-lg font-weight-bold text-black text-uppercase mb-1">
                                    Satisfied Tickets</div>
                                <c:if test="${user.role == 'user'}"><div class="h5 mb-0 font-weight-bold text-success">${satisfied}</div></c:if>
                                <c:if test="${user.role == 'admin'}"><div class="h5 mb-0 font-weight-bold text-success">${allsatisfied}</div></c:if>
                            </div>
                        </div>
                        <div class="row no-gutters align-items-center ms-auto">
                            <div class="col mr-2">
                                <div class="text-lg font-weight-bold text-black text-uppercase mb-1">
                                    Dissatisfied Tickets</div>
                                <c:if test="${user.role == 'user'}"><div class="h5 mb-0 font-weight-bold text-danger float-right">${dissatisfaction}</div></c:if>
                                <c:if test="${user.role == 'admin'}"><div class="h5 mb-0 font-weight-bold text-danger float-right">${alldissatisfaction}</div></c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--Ticket charts-->
        <div class="row m-1 pt-1">
            <!-- Ticket Status -->
            <div class="col-lg-6 mt-3 mb-4">
		        <div class="card shadow mb-4 border-dark">
		            <div class="card-header bg-black border-0 py-3">
		            	<a class="nav-link d-flex" href="ticket?action=list1" >
		                	<h6 class="m-0 font-weight-bold text-white">Ticket Status Progress</h6>
		                	<c:if test="${user.role == 'user'}"><div class="m-0 font-weight-bold white ms-auto">Total Tickets: ${totalTickets}</div></c:if>
		                	<c:if test="${user.role == 'admin'}"><div class="m-0 font-weight-bold white ms-auto">Total Tickets: ${allTickets}</div></c:if>
		                </a>
		            </div>
		            <c:if test="${user.role == 'user'}">
		            <div class="card-body">
		                <h4 class="small font-weight-bold ">Opened Tickets <span class="float-right">${openedPercentage}%</span></h4>
		                <div class="progress mb-4">
		                    <div class="progress-bar pie-1" role="progressbar" style="width: ${openedPercentage}%" aria-valuenow="${openedPercentage}" aria-valuemin="0" aria-valuemax="100"></div>
		                </div>
		                <h4 class="small font-weight-bold">Reopened Tickets <span class="float-right">${reopenedPercentage}%</span></h4>
		                <div class="progress mb-4">
		                    <div class="progress-bar pie-2" role="progressbar" style="width: ${reopenedPercentage}%" aria-valuenow="${reopenedPercentage}" aria-valuemin="0" aria-valuemax="100"></div>
		                </div>
		                <h4 class="small font-weight-bold">On Hold <span class="float-right">${onHoldPercentage}%</span></h4>
		                <div class="progress mb-4">
		                    <div class="progress-bar pie-3" role="progressbar" style="width: ${onHoldPercentage}%" aria-valuenow="${onHoldPercentage}" aria-valuemin="0" aria-valuemax="100"></div>
		                </div>
		                <h4 class="small font-weight-bold">Closed <span class="float-right">${closedPercentage}%</span></h4>
		                <div class="progress mb-4">
		                    <div class="progress-bar pie-4" role="progressbar" style="width: ${closedPercentage}%" aria-valuenow="${closedPercentage}" aria-valuemin="0" aria-valuemax="100"></div>
		                </div>
		                <h4 class="small font-weight-bold">In Progress <span class="float-right">${inProgressPercentage}%</span></h4>
		                <div class="progress mb-4">
		                    <div class="progress-bar pie-5" role="progressbar" style="width: ${inProgressPercentage}%" aria-valuenow="${inProgressPercentage}" aria-valuemin="0" aria-valuemax="100"></div>
		                </div>
		                <h4 class="small font-weight-bold">Cancelled <span class="float-right">${cancelledPercentage}%</span></h4>
		                <div class="progress">
		                    <div class="progress-bar pie-6" role="progressbar" style="width: ${cancelledPercentage}%" aria-valuenow="${cancelledPercentage}" aria-valuemin="0" aria-valuemax="100"></div>
		                </div>
		            </div>
		            </c:if>
		            <c:if test="${user.role == 'admin'}">
		            <div class="card-body">
		                <h4 class="small font-weight-bold ">Opened Tickets <span class="float-right">${allopened}%</span></h4>
		                <div class="progress mb-4">
		                    <div class="progress-bar pie-1" role="progressbar" style="width: ${allopened}%" aria-valuenow="${allopened}" aria-valuemin="0" aria-valuemax="100"></div>
		                </div>
		                <h4 class="small font-weight-bold">Reopened Tickets <span class="float-right">${allreopened}%</span></h4>
		                <div class="progress mb-4">
		                    <div class="progress-bar pie-2" role="progressbar" style="width: ${allreopened}%" aria-valuenow="${allreopened}" aria-valuemin="0" aria-valuemax="100"></div>
		                </div>
		                <h4 class="small font-weight-bold">On Hold <span class="float-right">${allonHold}%</span></h4>
		                <div class="progress mb-4">
		                    <div class="progress-bar pie-3" role="progressbar" style="width: ${allonHold}%" aria-valuenow="${allonHold}" aria-valuemin="0" aria-valuemax="100"></div>
		                </div>
		                <h4 class="small font-weight-bold">Closed <span class="float-right">${allclosed}%</span></h4>
		                <div class="progress mb-4">
		                    <div class="progress-bar pie-4" role="progressbar" style="width: ${allclosed}%" aria-valuenow="${allclosed}" aria-valuemin="0" aria-valuemax="100"></div>
		                </div>
		                <h4 class="small font-weight-bold">In Progress <span class="float-right">${allinProgress}%</span></h4>
		                <div class="progress mb-4">
		                    <div class="progress-bar pie-5" role="progressbar" style="width: ${allinProgress}%" aria-valuenow="${allinProgress}" aria-valuemin="0" aria-valuemax="100"></div>
		                </div>
		                <h4 class="small font-weight-bold">Cancelled <span class="float-right">${allcancelled}%</span></h4>
		                <div class="progress">
		                    <div class="progress-bar pie-6" role="progressbar" style="width: ${allcancelled}%" aria-valuenow="${allcancelled}" aria-valuemin="0" aria-valuemax="100"></div>
		                </div>
		            </div>
		            </c:if>
		        </div>
		    </div>

            <!-- Pie Chart -->
            <div class="col-lg-6 mt-3">
                <div class="card shadow mb-4 border-dark">
                    <!-- Card Header - Dropdown -->
                    <div class="card-header py-3 d-flex flex-row bg-black align-items-center justify-content-between">
                        <h6 class="m-0 font-weight-bold text-white">Priority Percentage</h6>
                        <div class="dropdown">
                            <a class="dropdown-toggle" href="#" role="button" id="dropdownMenuLink"
                                data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <i class='bx bx-dots-vertical-rounded pie'></i>
                            </a>
                            <div class="dropdown-menu dropdown-menu-right shadow animated--fade-in"
                                aria-labelledby="dropdownMenuLink">
                                <div class="dropdown-header">Dropdown Header:</div>
                                <a class="dropdown-item" href="#">Action</a>
                                <a class="dropdown-item" href="#">Another action</a>
                                <div class="dropdown-divider"></div>
                                <a class="dropdown-item" href="#">Something else here</a>
                            </div>
                        </div>
                    </div>
                    <!-- Card Body -->
                    <div class="card-body">
                    	<c:if test="${user.role == 'user'}">
	                    	<div class="hidden" 
	                    		 id="chart-data"
							     data-high="${highCount}"
							     data-medium="${mediumCount}"
							     data-low="${lowCount}">
							</div>
						</c:if>
						<c:if test="${user.role == 'admin'}">
	                    	<div class="hidden" 
	                    		 id="chart-data"
							     data-high="${allhigh}"
							     data-medium="${allmedium}"
							     data-low="${allLow}">
							</div>
						</c:if>
                        <div class="chart-pie pt-4 pb-2">
                            <canvas id="myPieChart"></canvas>
                        </div>
                        <div class="mt-4 text-center small d-flex flex-column flex-sm-row align-items-center justify-content-center">
                            <span class="mb-2 mb-sm-0 me-sm-2">
                                <i class='bx bxs-circle pie'></i> High Priority
                            </span>
                            <span class="mb-2 mb-sm-0 me-sm-2">
                                <i class='bx bxs-circle text-secondary'></i> Medium Priority
                            </span>
                            <span class="mb-2 mb-sm-0">
                                <i class='bx bxs-circle text-black'></i> Low Priority
                            </span>
                        </div>
                    </div>
                    
                </div>
            </div>
    
        </div>
    </main>
   
   
    <footer>
        <!-- place footer here -->
    </footer>
    <!-- Scroll to Top Button-->
    

    <!-- Logout Modal-->
    <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="logoutModalLabel" aria-hidden="true">
	    <div class="modal-dialog" role="document">
	        <div class="modal-content bg-dark text-light">
	            <div class="modal-header">
	                <h5 class="modal-title" id="logoutModalLabel">Ready to Leave?</h5>
	                <button class="btn-close ms-auto bg-dark" type="button" data-bs-dismiss="modal" aria-label="Close"></button>
	            </div>
	            <div class="modal-body">Select "Logout" below if you are ready leave.</div>
	            <div class="modal-footer">
	                <button class="btn btn-secondary" type="button" data-bs-dismiss="modal">Cancel</button>
	                <a class="btn logout" href="user?action=logout">Logout</a>
	            </div>
	        </div>
	    </div>
	</div>
    
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>

    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    <script src="vendor/bootstrap/js/jquery.easing.min.js"></script>


    <script src="js/scripts.js"></script>
    <script src="js/Chart.min.js"></script>
    <script src="js/chart-pie.js"></script>
	

</body>

</html>




