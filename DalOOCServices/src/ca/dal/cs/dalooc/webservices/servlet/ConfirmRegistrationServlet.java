package ca.dal.cs.dalooc.webservices.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.dal.cs.dalooc.persistence.ApplicationContext;

import com.mongodb.WriteResult;

/**
 * Servlet implementation class ConfirmRegistrationServlet
 */
public class ConfirmRegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private ca.dal.cs.dalooc.persistence.UserRepository userRepository;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConfirmRegistrationServlet() {
        super();
        this.userRepository = ApplicationContext.getInstance().getBean(ca.dal.cs.dalooc.persistence.UserRepository.class);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		super.doGet(request, response);
		String userId = (String)request.getParameter("userId");
		if (userId != null) {
			WriteResult result = this.userRepository.updateObject(userId, new Object[]{ "emailValid", true});
			if (result != null) {
				response.getWriter().append(getEmailConfirmedHtml());
			} else {
				response.getWriter().append(getUserNotFoundHtml());
			}
		} else { 
			response.getWriter().append("usage error.");
		}
	}

	private String getEmailConfirmedHtml() {
		StringBuffer emailConfimedHtml = new StringBuffer();
		
		emailConfimedHtml.append("<h1>Thank you for confirming your email address.</h1><br/>");
		emailConfimedHtml.append("<br/>");
		emailConfimedHtml.append("You may now login with DalOOC app on your device.</h1><br/>");
		emailConfimedHtml.append("<br/>");
		emailConfimedHtml.append("<h1>Thank you.</h1>");
		return emailConfimedHtml.toString();
	}

	private String getUserNotFoundHtml() {
		StringBuffer userNotFoundHtml = new StringBuffer();
		
		userNotFoundHtml.append("<h1>The user you're trying to confirm registration is invalid.</h1><br/>");
		userNotFoundHtml.append("<br/>");
		userNotFoundHtml.append("Please contact the system administrator. </h1>");
		userNotFoundHtml.append("<a href=\"mailto:dalooc2013@gmail.com?Subject=Problem when confirming email address\" target=\"_top\">Send Mail</a><br/>");
		userNotFoundHtml.append("<br/>");
		userNotFoundHtml.append("<h1>Thank you.</h1>");
		return userNotFoundHtml.toString();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doPost(request, response);
	}

}
