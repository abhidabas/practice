package com.sfb.schedulerWeblogicDemo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;
import java.util.TimerTask;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

	public class MyTimerTask extends TimerTask{
	private String name;
	// GetConfigPropertyValues getConfigPropertyValues = new
	// GetConfigPropertyValues();

	public MyTimerTask(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	// method containing actual business logic
	@Override
	public void run() {
		//System.out.println("Task executed at " + new Date());
// CONTRACTS LOGIC ////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		/*Properties properties = new Properties();
		ConfigProperties configProperties = new ConfigProperties();
		Connection connection = null;
		String query = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Statement statement = null;
		Statement statement1 = null;
		Statement statement2 = null;
		String to = null;
		String pkRegistrationId = null;
		
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", configProperties.host);
		properties.put("mail.smtp.auth", configProperties.auth);
		properties.put("user", configProperties.user);
		properties.put("password", configProperties.password);
		
		try {
			connection = ConnectionFactory.getConnection();
			query = "UPDATE shaw_contract SET fk_statusid = (SELECT pk_lookupid FROM shaw_lookup WHERE UPPER(lookupvalue) = 'EXPIRED' AND UPPER(lookuptype) = 'SHAW_CONTRACT_STATUS') WHERE expirationdate = TRUNC(SYSDATE)";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.executeUpdate();	
			
			//System.out.println("Ajay: contract expired today");
			
			statement = connection.createStatement();
			statement2 = connection.createStatement();
			resultSet = statement.executeQuery("SELECT author, pk_registrationid FROM shaw_contract WHERE expirationdate = TRUNC(SYSDATE)");
			
			//System.out.println("Ajay: get email data");
			to = null;
			pkRegistrationId = null;
			while (resultSet.next()) {
				to = resultSet.getString("author");
				pkRegistrationId = resultSet.getString("pk_registrationid");
				
				Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(properties.getProperty("user"), properties.getProperty("password"));
					}
				});

				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(properties.getProperty("user")));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
				message.setSubject("Contract expired - " + pkRegistrationId);
				message.setContent("Contract - " + pkRegistrationId + " expired on date " + new Date(), "text/html");

				// send the message
				Transport.send(message);
				//System.out.println("Ajay: email sent");
				
				statement1 = connection.createStatement();
				// Insert into EMAIL_HISTORY table if Email sent successfully
				statement1.executeUpdate("INSERT INTO email_history (send_to, subject, body, created_date, status) VALUES ('" + to + "', 'Contract expired - " + pkRegistrationId + "', 'Contract - " + pkRegistrationId + " expired on date " + new Date() + "', SYSDATE, 'Success')");
				//System.out.println("Ajay: insert in email log");
				
				to = null;
				pkRegistrationId = null;
			}
		} catch (SQLException e) {
			//System.out.println("ABHI: SQLException occured 0:" + e);
		}catch (Exception e) {
			try {
				// Insert into EMAIL_HISTORY table if Email sending failed
				statement2.executeUpdate("INSERT INTO email_history (send_to, subject, body, created_date, status) VALUES ('" + to + "', 'Contract expired - " + pkRegistrationId + "', 'Contract - " + pkRegistrationId + " expired on date " + new Date() + "', SYSDATE, 'Error')");
			} catch (SQLException e1) {
				//System.out.println("ABHI: SQLException occured 1:" + e1);
			}
			//System.out.println("ABHI: Exception occured 2:" + e);
		} finally { 
			try {
				resultSet.close();
				statement.close();
				statement1.close();
				statement2.close();
				connection.close();
			} catch (SQLException e) {
				//System.out.println("ABHI: Exception close con 3:" + e);
				e.printStackTrace();
			}*/
		}
		
// SHAW LOGIC /////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
//		Connection con = null;
//		Statement statement = null;
//		Statement statement1 = null;
//		Statement statement2 = null;
//		ResultSet resultSet = null;
//		String msg1 = "";
//
//		try {
//			
//				System.out.println("inside try");
//				con = ConnectionFactory.getConnection();
//				statement = con.createStatement();
//				statement.execute("alter session set container = pdb1");
//				statement1 = con.createStatement();
//
//				// statement getting required data
//				resultSet = statement1.executeQuery("select CRITICALDATE," + "  FK_CRITICALDATETYPE," + "  email," + " scd.docname," + " scd.file_number," + " look.lookupvalue," + " description"
//						+ "  FROM SHAW_CONTRACT_CRITICAL_DATES ccd  LEFT JOIN Shaw_Contract_document scd on ccd.fk_regid = scd.pk_docregid"
//						+ "  LEFT JOIN shaw_lookup look on ccd.FK_CRITICALDATETYPE = look.PK_LOOKUPID"
//						+ "  WHERE ccd.oldflag = 'N' and FK_CRITICALDATETYPE in (129,130)"
//						+ "  and CRITICALDATE = trunc(sysdate)");
//
//				// send email for each record found
//				while (resultSet.next()) {
//					System.out.println("inside resultSet");
//					System.out.println("resultSet.getString(email):" + resultSet.getString("email"));
//					String to = resultSet.getString("email");
//					String description = resultSet.getString("description");
//					/*if(description.equals("null") || (description == "null") ){
//						description = "-";
//					}*/
//					String docid = resultSet.getString("file_number");
//					String notification = resultSet.getString("lookupvalue");
//					String docname = resultSet.getString("docname");
//
//					// Create SOAP Connection
//					SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
//					SOAPConnection soapConnection = soapConnectionFactory.createConnection();
//
//					// Send SOAP Message to SOAP Server
//					String url = "https://process-sofbangsub.process.us2.oraclecloud.com/soa-infra/services/default/ShawNotificationServiceApp!1.0*soa_b05af51f-bc9b-4da0-8e7a-f1d5d6eb3bac/ShawNotificationProcess.service?WSDL";
//					SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(to,docid,description,notification,docname), url);
//
//					// Process the SOAP Response
//					// printSOAPResponse(soapResponse);
//					soapConnection.close();
//				}
//				
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			try {
//				// con = ConnectionFactory.getConnection();
//				statement2 = con.createStatement();
//				System.out.println("inserting into table");
//				String sql = "INSERT INTO EMAIL_ERROR_LOG VALUES ('" + e + "')";
//				statement2.executeUpdate(sql);
//			} catch (Exception ex) {
//				System.out.println("ex " + ex);
//			}
//		} finally { 
//			if (resultSet != null) {
//				try {
//					resultSet.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//			if (statement != null) {
//				try {
//					statement.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//			if (statement1 != null) {
//				try {
//					statement1.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//			if (statement2 != null) {
//				try {
//					statement2.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//		}
	}

//	private static SOAPMessage createSOAPRequest(String to, String docid, String description, String notification, String docname) throws Exception {
//		Date currentDate1 = new Date();
//		String convertedDate1 = currentDate1.toString();
//
//		System.out.println("inside createSOAPRequest to" + to);
//		MessageFactory messageFactory = MessageFactory.newInstance();
//		SOAPMessage soapMessage = messageFactory.createMessage();
//		SOAPPart soapPart = soapMessage.getSOAPPart();
//		String serverURI = "http://xmlns.oracle.com/bpmn/bpmnCloudProcess/ShawNotificationServiceApp/ShawNotificationProcess/";
//
//		// SOAP Envelope
//		SOAPEnvelope envelope = soapPart.getEnvelope();
//		// envelope.setAttribute("Authorization", "Basic
//		// cGNzdXNlcjpTb2ZiYW5nIzQwNDU=");
//		SOAPHeader header = envelope.getHeader();
//		envelope.addNamespaceDeclaration("SofbangSub",
//				"http://xmlns.oracle.com/bpmn/bpmnCloudProcess/ShawNotificationServiceApp/ShawNotificationProcess");
//				 
//
//		// SOAP Body
//		SOAPBody soapBody = envelope.getBody();
//		SOAPElement soapBodyElem = soapBody.addChildElement("SendEmail", "SofbangSub");
//		SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("To");
//		soapBodyElem1.addTextNode(to);
//		SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("Subject");
//		soapBodyElem2.addTextNode("LDMS Notification: "+notification+"- "+docid);
//		SOAPElement soapBodyElem3 = soapBodyElem.addChildElement("Body");
//		soapBodyElem3.addTextNode("<b><h3>Notification Details:</h3></b><br> ");
//		soapBodyElem3.addTextNode("  <b>Document Name:</b> "+docname+"<br><br>");
//		soapBodyElem3.addTextNode("  <b>Document ID:</b> "+docid+"<br><br>");
//		if(!description.equals("null")){
//		soapBodyElem3.addTextNode("  <b>Description:</b> "+description);
//		} 
//		
//		
//
//		MimeHeaders headers = soapMessage.getMimeHeaders();
//		String loginPassword = "ajay.bisht@sofbang.com:!1Qaszxcv";
//		headers.addHeader("Authorization", "Basic " + new String(Base64.getEncoder().encode(loginPassword.getBytes())));
//
//		// System.out.println("SOAPAction = "+serverURI+"SendEmail");
//		// headers.addHeader("SOAPAction", serverURI+"SendEmail");
//		soapMessage.saveChanges();
//
//		// Check the input
//		System.out.println("Request SOAP Message = ");
//		soapMessage.writeTo(System.out);
//		System.out.println();
//		return soapMessage;
//	}
//}
