package ca.dal.cs.dalooc.webservice;

import ca.dal.cs.dalooc.model.User;
import ca.dal.cs.dalooc.persistence.ApplicationContext;
import ca.dal.cs.dalooc.webservice.util.DalOOCProperties;
import ca.dal.cs.dalooc.webservice.util.EmailSender;
import ca.dal.cs.dalooc.webservice.util.Parser;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;

public class UserRepository {

	private ca.dal.cs.dalooc.persistence.UserRepository userRepository = ApplicationContext.getInstance().getBean(ca.dal.cs.dalooc.persistence.UserRepository.class);
	private EmailSender emailSender = new EmailSender();
	
	public String validateUser(String email) {
		String[] names = { "email" };
		String[] values = { email };
		
		User user = this.userRepository.getObject(names, values);
		
		if (user != null) {
			return Parser.getUserDBObject(user).toString();
		}
		return "{}";
	}

	public String getUser(String userId) {
		String[] names = { "_id" };
		String[] values = { userId };
		
		User user = this.userRepository.getObject(names, values);
		
		if (user != null) {
			return Parser.getUserDBObject(user).toString();
		}
		return "{}";
	}
	
	public String registerUser(String userString) {
		BasicDBObject userDBObject = (BasicDBObject)JSON.parse(userString);
		
		String auxUserString = validateUser(userDBObject.getString("email"));
		
		if (!auxUserString.equals("{}")) {
			return "EMAIL_UNAVAILABLE";
		}

		User newUser = Parser.getUserObject(userDBObject);
		this.userRepository.saveObject(newUser);
		
		this.emailSender.enqueueEmailToSend(
				new String[] {DalOOCProperties.getProperty("EmailSender"), 
						DalOOCProperties.getProperty("EmailPass"), 
						newUser.getEmail(),
						"",
						DalOOCProperties.getProperty("EmailSubject"),
						buildHtmlMessage(newUser.getId()), 
						"UNSENT"});
		
		return "USER_CREATED";
	}
	
	private String buildHtmlMessage(String userId) {
		StringBuilder htmlMessage = new StringBuilder();
		
		String registerConfirmationLink = DalOOCProperties.getProperty("ConfirmationLinkUrl") + "?userId=" + userId;
		
		htmlMessage.append("<h1>Please click the link below to confirm your registration.<br/>");
		htmlMessage.append("If clicking on the link does not work, copy and paste it on your browser.</h1><br/>");
		htmlMessage.append("<br/>");
		htmlMessage.append("<a href=\"");
		htmlMessage.append(registerConfirmationLink);
		htmlMessage.append("\" >");
		htmlMessage.append(registerConfirmationLink);
		htmlMessage.append("</a>");
		htmlMessage.append("<br/>");
		htmlMessage.append("<br/>");
		htmlMessage.append("Cheers,<br/>");
		htmlMessage.append("<br/>");
		htmlMessage.append("DalOOC Administrator<br/>");
		htmlMessage.append("<br/>");
		
		return htmlMessage.toString();
	}
	
	public void saveUser(String userString) {
		BasicDBObject userDBObject = (BasicDBObject)JSON.parse(userString);
		this.userRepository.saveObject(Parser.getUserObject(userDBObject));
	}
}
