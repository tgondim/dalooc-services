package ca.dal.cs.dalooc.webservice.util;


import java.security.Security;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.smtp.SMTPTransport;

public class EmailSender {
	
	public static final String UNSENT = "UNSENT";
	public static final String SENDING = "SENDING";

	private List<String[]> emailsToSend;
	
	private Thread sendEmailThread;
	
	public EmailSender() {
		this.emailsToSend = new ArrayList<String[]>();
	}
	
	public void enqueueEmailToSend(String[] emailToSendData) {
		synchronized (emailsToSend) {
			this.emailsToSend.add(emailToSendData);
		}
		if (this.sendEmailThread == null) {
			this.sendEmailThread = new Thread(new SendEmailRunnable());
			this.sendEmailThread.start();
		}
	}
	
	/**
	 * Send email using GMail SMTP server.
	 *
	 * @param username GMail username
	 * @param password GMail password
	 * @param recipientEmail TO recipient
	 * @param ccEmail CC recipient. Can be empty if there is no CC recipient
	 * @param title title of the message
	 * @param message message to be sent
	 * @throws AddressException if the email address parse failed
	 * @throws MessagingException if the connection is dead or not in the connected state or if the message is not a MimeMessage
	 */
	public static boolean send(final String username, final String password, String recipientEmail, String ccEmail, String title, String message) throws AddressException, MessagingException {
	    Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

	    // Get a Properties object
	    Properties props = System.getProperties();
	    props.setProperty("mail.smtps.host", DalOOCProperties.getProperty("mailSmtpsHost"));
	    props.setProperty("mail.smtp.socketFactory.class", DalOOCProperties.getProperty("mailSmtpSocketFactoryClass"));
	    props.setProperty("mail.smtp.socketFactory.fallback", DalOOCProperties.getProperty("mailSmtpSocketFactoryFallback"));
	    props.setProperty("mail.smtp.port", DalOOCProperties.getProperty("mailSmtpPort"));
	    props.setProperty("mail.smtp.socketFactory.port", DalOOCProperties.getProperty("mailSmtpSocketFactoryPort"));
	    props.setProperty("mail.smtps.auth", DalOOCProperties.getProperty("mailSmtpsAuth"));
	    props.setProperty("mail.smtps.quitwait", DalOOCProperties.getProperty("mailSmtpsQuitwait"));

	    Session session = Session.getInstance(props, null);

	    // -- Create a new message --
	    final MimeMessage msg = new MimeMessage(session);

	    // -- Set the FROM and TO fields --
	    msg.setFrom(new InternetAddress(username + "@dalooc.com"));
	    msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail, false));

	    if (ccEmail.length() > 0) {
	        msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail, false));
	    }

	    msg.setSubject(title);
	    msg.setContent(message, "text/html");
	    msg.setSentDate(new Date());

	    SMTPTransport t = (SMTPTransport)session.getTransport("smtps");

	    t.connect(DalOOCProperties.getProperty("mailSmtpsHost"), username, password);
	    t.sendMessage(msg, msg.getAllRecipients());      
	    t.close();
	    
	    return true;
	}
	
	public class SendEmailRunnable implements Runnable {
		
		@Override
		public void run() {
			int emailToSendPosition;
			
			while (true) {
				emailToSendPosition = -1;
				synchronized (EmailSender.this.emailsToSend) {
					for (int i = 0; i < EmailSender.this.emailsToSend.size(); i++) {
						if (EmailSender.this.emailsToSend.get(i)[6].equals(UNSENT)) {
							EmailSender.this.emailsToSend.get(i)[6] = SENDING;
							emailToSendPosition = i;
							break;
						}
					}
					if (emailToSendPosition != -1) {
						try {
							if (send(EmailSender.this.emailsToSend.get(emailToSendPosition)[0], 
									EmailSender.this.emailsToSend.get(emailToSendPosition)[1],
									EmailSender.this.emailsToSend.get(emailToSendPosition)[2],
									EmailSender.this.emailsToSend.get(emailToSendPosition)[3],
									EmailSender.this.emailsToSend.get(emailToSendPosition)[4],
									EmailSender.this.emailsToSend.get(emailToSendPosition)[5])) {
								EmailSender.this.emailsToSend.remove(emailToSendPosition);
							}
						} catch (AddressException e) {
							e.printStackTrace();
						} catch (MessagingException e) {
							e.printStackTrace();
						}
					}
				}
				try {
					Thread.sleep(Integer.valueOf(DalOOCProperties.getProperty("SendEmailInterval")) * 1000);
				} catch (InterruptedException ignored) {}
			}
		}
	}
}
