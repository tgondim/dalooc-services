package ca.dal.cs.dalooc.webservice.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import ca.dal.cs.dalooc.persistence.ApplicationContext;

import com.mongodb.WriteResult;

/**
 * Servlet implementation class ConfirmRegistrationServlet
 */
public class ConfirmRegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private ca.dal.cs.dalooc.persistence.UserRepository userRepository;

	private static Logger logger = Logger.getLogger(ConfirmRegistrationServlet.class);
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		System.out.println("Initialising log4j");
		String log4jLocation = config.getInitParameter("log4j-properties-location");

		ServletContext sc = config.getServletContext();

		if (log4jLocation == null) {
			System.out.println("No log4j properites...");
			BasicConfigurator.configure();
		} else {
			String webAppPath = sc.getRealPath("/");
			String log4jProp = webAppPath + log4jLocation;
			File output = new File(log4jProp);

			if (output.exists()) {
				System.out.println("Initialising log4j with: " + log4jProp);
				PropertyConfigurator.configure(log4jProp);
			} else {
				System.out.println("Find not found (" + log4jProp + ").");
				BasicConfigurator.configure();
			}
		}

		super.init(config);
	}
	
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
		String userId = (String)request.getParameter("userId");
		logger.info("Requesting confirmation to userId " + userId);
		if (userId != null) {
			
			Query query = new Query();
			query.addCriteria(Criteria.where("_id").is(userId));
			
			Update update = new Update();
			update.set("emailValid", true);
			
			WriteResult result = this.userRepository.updateObject(query, update);
			if (result != null) {
				logger.info("Email confirmed for userId " + userId);
				response.getWriter().append(getEmailConfirmedHtml());
			} else {
				logger.info("No user was found with userId " + userId);
				response.getWriter().append(getUserNotFoundHtml());
			}
		} else { 
			logger.error("Usage error. Servlet called with no parameters.");
			response.getWriter().append("Usage error. Servlet called with no parameters.");
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
	}

}
