package controller;
 
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TicketDao;
import model.Ticket;



@WebServlet("/ticket-image")
public class TicketImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	 private TicketDao ticketDao;

     public TicketImage() {
         this.ticketDao = new TicketDao();
     }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
    	try {
        	 
         String ticketNum = request.getParameter("num");
         
         Ticket tickets = ticketDao.selectTicket(Integer.parseInt(ticketNum));
        
         byte[] decodedString = tickets.getImage();
         
         
         System.out.println("Image byte array length: " + decodedString.length);

         
         ByteArrayInputStream iStream = new ByteArrayInputStream(decodedString);       
         int length = decodedString.length;         
         response.setContentType("image/jpeg, image/jpg, image/png");
         response.setContentLength(length);         
         ServletOutputStream oStream = response.getOutputStream();
         byte [] buffer = new byte[1024];
         int len;
         while ((len = iStream.read(buffer)) != -1) {
            oStream.write(buffer, 0, len);
         }
         iStream.close();        
         oStream.flush();
         oStream.close();         
    }catch(NullPointerException e) {
    	}
    }
 
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
}
