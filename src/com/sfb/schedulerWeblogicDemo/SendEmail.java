package com.sfb.schedulerWeblogicDemo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/sendEmail")
public class SendEmail {

	@POST
	// @Path("/sendTo/{emailId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String sendEmail(Email email) {
		String returnText = null;
		
		Properties properties = new Properties();
		ConfigProperties configProperties = new ConfigProperties();

		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", configProperties.host);
		properties.put("mail.smtp.auth", configProperties.auth);
		properties.put("user", configProperties.user);
		properties.put("password", configProperties.password);

		try {
			
			Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(properties.getProperty("user"),
							properties.getProperty("password"));
				}
			});

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(properties.getProperty("user")));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.to));
			message.setSubject(email.subject);
			message.setContent(email.body, "text/html");

			// send the message
			Transport.send(message);
			
			returnText = "Email sent successfully.";
		}  catch (Exception e1) {
			returnText = "Error in sending Email!";
			// Insert into EMAIL_HISTORY table if Email sending failed
			e1.printStackTrace();
		}		
		return returnText;
	}
}
