package com.vupadhi.service;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.vupadhi.constants.MailAppConstants;
import com.vupadhi.dao.HelpDeskCallLogBAO;
import com.vupadhi.dao.HelpDeskCallLogDAO;

public class HDusersMailService {

	static Logger logger = LogManager.getLogger(HDusersMailService.class);

	public static String sendTimeDifferenceMailAutomatically(String ntpservertime,String servertime,String dbtime) throws Exception {

		final String username = MailAppConstants.EMAIL_USERNAME;
		final String password = MailAppConstants.EMAIL_PASSWORD;

		// toMails

		final StringBuilder tomailslistbuild = new StringBuilder();
		InternetAddress[] toEmail = null;

		List<String> tomailsList = HDusersMailService.getToMails();

		if (!tomailsList.isEmpty() && tomailsList != null) {
			for (int l1 = 0; l1 < tomailsList.size(); l1++) {
				tomailslistbuild.append(tomailsList.get(l1) + ",");

			}

			String recipient = tomailslistbuild.toString();
			String[] recipientList = recipient.split(",");
			toEmail = new InternetAddress[recipientList.length];
			int counter = 0;
			for (String s1 : recipientList) {
				toEmail[counter] = new InternetAddress(s1.trim());
				counter++;
			}
		}

		// ccMails

		final StringBuilder ccmailslistbuild = new StringBuilder();
		InternetAddress[] ccEmail = null;
		List<String> ccmailsList = HDusersMailService.getCCMails();

		if (!ccmailsList.isEmpty() && ccmailsList != null) {
			for (int l1 = 0; l1 < ccmailsList.size(); l1++) {
				ccmailslistbuild.append(ccmailsList.get(l1) + ",");
			}

			String recipient2 = ccmailslistbuild.toString();
			String[] recipientList2 = recipient2.split(",");
			ccEmail = new InternetAddress[recipientList2.length];
			int counter2 = 0;
			for (String s1 : recipientList2) {
				ccEmail[counter2] = new InternetAddress(s1.trim());
				counter2++;
			}
		}

	
		String res = "fail";
		Message message = null;
		String htmlText = null;
		String sUserName = "admin";
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String todaydate = dateFormat.format(new Date());

		String mailSubject = "Time Difference Between NTPServer,ApplicationServer,DBServer.";

		Properties props = new Properties();

		props.put("mail.transport.protocol", "smtp");
    	props.put("mail.smtp.port", MailAppConstants.MailPort); 
    	props.put("mail.smtp.starttls.enable", "true");
    	props.put("mail.smtp.auth", "true");
    	

		if (toEmail != null) {

			try {

				Authenticator auth = new Authenticator() {
					public PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				};
				Session session = Session.getInstance(props, auth);

				final StringBuilder sb = new StringBuilder();
				sb.append("<html>");
				sb.append("<style>");
				sb.append("th, td {");
				sb.append("	border: 1px solid black;");
				sb.append("	text-align: center;");
				sb.append("	padding: 8px");
				sb.append("}");
				sb.append("table {");
				sb.append("	font-family: Times New Roman, sans-serif;");
				sb.append("	border-collapse: collapse;");
				sb.append("	width: 100%;");
				sb.append("}");
				sb.append("</style>");
				sb.append("<body>");
				sb.append("	<br>");
				sb.append("	<table  class=\"oneComWebmail-MsoNormalTable\" style=\"width: 333.8pt;\" width=\"445\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">");
				sb.append("<tr>");
				sb.append("	<th colspan=\"2\" style=\"background-color: #FFFF99;\">NTPserver Time:</th>");
				sb.append("	<th colspan=\"4\" style=\"background-color: #FFFF99;\">"+ntpservertime+"</th>");
				sb.append("	</tr>");
				sb.append("<tr>");
				sb.append("	<th colspan=\"2\" style=\"background-color: #FFFF99;\">Applicationserver Time:</th>");
				sb.append("	<th colspan=\"4\" style=\"background-color: #FFFF99;\">"+servertime+"</th>");
				sb.append("	</tr>");
				sb.append("<tr>");
				sb.append("	<th colspan=\"2\" style=\"background-color: #FFFF99;\">DBserver Time:</th>");
				sb.append("	<th colspan=\"4\" style=\"background-color: #FFFF99;\">"+dbtime+"</th>");
				sb.append("	</tr>");
				sb.append("</table><br><br><br>");
				
				sb.append("<font face='MS Sans Serif' size=1>This is only an automated mail alert from system.");
				sb.append("</font>");
				sb.append("</body></html>");

				message = new MimeMessage(session);
				message.setFrom(new InternetAddress(MailAppConstants.EMAIL_USERNAME,MailAppConstants.FROMNAME));
				message.setRecipients(Message.RecipientType.TO, toEmail);
				message.setRecipients(Message.RecipientType.CC, ccEmail);
				message.setSubject(mailSubject);

				htmlText = sb.toString();

				MimeMultipart multipart = new MimeMultipart("related");


				// for mail html
				BodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setContent(htmlText, "text/html");
				multipart.addBodyPart(messageBodyPart);

				message.setContent(multipart);
				System.out.println("mailStarted");

//AWS Email Integrations Start
				// Create a transport.
			      Transport transport = session.getTransport();
			                  
			      // Send the message.
			      try
			      {
			          System.out.println("Sending...");
			          
			          // Connect to Amazon SES using the SMTP username and password you specified above.
			          transport.connect(MailAppConstants.MailHost, MailAppConstants.SMTP_USERNAME, MailAppConstants.SMTP_PASSWORD);
			      	
			          // Send the email.
			          transport.sendMessage(message, message.getAllRecipients());
			          System.out.println("Email sent!");
			          res="success";
			      }
			      catch (Exception ex) {
			      	
			          System.out.println("The email was not sent.");
			          System.out.println("Error message: " + ex.getMessage());
			          ex.printStackTrace();
			          HelpDeskCallLogDAO.deptErrorLog(
								ex.getMessage(),
								"error while sending AutoMatic Mail Reports :" + Arrays.asList(toEmail) + " supplier :  " + sUserName,
								"error while sending AutoMatic Mail Reports :" + Arrays.asList(toEmail) + " supplier :  " + sUserName,
								"HDusersMailService", HDusersMailService.class.getName(), "sendTimeDifferenceMailAutomatically");
			      }
			      finally
			      {
			          // Close and terminate the connection.
			          transport.close();
			      }
//AWS Email Integrations end			      

			} catch (Exception e) {
				HelpDeskCallLogDAO.deptErrorLog(
						e.getMessage(),
						"error while sending AutoMatic Mail Reports :" + Arrays.asList(toEmail) + " supplier :  " + sUserName,
						"error while sending AutoMatic Mail Reports :" + Arrays.asList(toEmail) + " supplier :  " + sUserName,
						"HDusersMailService", HDusersMailService.class.getName(), "sendTimeDifferenceMailAutomatically");
			}
			if (res.equals("success")) {
				logger.info("Email send to :" + Arrays.asList(toEmail));
				logger.info("Email Subject is  :" + message.getSubject());
				logger.info("Email Content is :" + htmlText);
			} else {
				logger.info("Sending an email to this " + Arrays.asList(toEmail) + " got failed with subject " + message.getSubject()
						+ "of supplier user name " + sUserName + " With Content : " + htmlText);
			}
		}

		return res;

	}



	public static List<String> getToMails() throws Exception {

		CallableStatement cstmt = null;
		ResultSet rs = null;
		List<String> tomails = new ArrayList<String>();
	

		try {
			cstmt = HelpDeskCallLogBAO.getToMailsFromDB();

			rs = cstmt.executeQuery();

			while (rs.next()) {

				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				System.out.println("To Mails Count "+columnCount);
				for (int l1 = 1; l1 < columnCount + 1; l1++) {
					if (!rs.getString(l1).equals("")) {
						tomails.add(rs.getString(l1));
					}
				}

			}

		}

		catch (SQLException e) {

			throw e;

		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException se) {

			}
			try {
				if (cstmt != null) {
					cstmt.close();
				}
			} catch (SQLException se) {

			}

		}

		return tomails;

	}

	public static List<String> getCCMails() throws Exception {

		CallableStatement cstmt = null;
		ResultSet rs = null;
		List<String> ccmails = new ArrayList<String>();
		;

		try {
			cstmt = HelpDeskCallLogBAO.getCCMailsFromDB();

			rs = cstmt.executeQuery();

			while (rs.next()) {
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				System.out.println("CC Mails Count "+columnCount);
				for (int l1 = 1; l1 < columnCount + 1; l1++) {
					if (!rs.getString(l1).equals("")) {
						ccmails.add(rs.getString(l1));
					}
				}

			}

		}

		catch (SQLException e) {

			throw e;

		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException se) {

			}
			try {
				if (cstmt != null) {
					cstmt.close();
				}
			} catch (SQLException se) {

			}

		}

		return ccmails;

	}
	
	public static String GetDBTime() throws Exception {
		
		
		CallableStatement cstmt = null; 
		ResultSet resultSet = null;
		String strTime="";
				try {
		
					cstmt = HelpDeskCallLogBAO.GetDBTime();
					resultSet = cstmt.executeQuery();
					while (resultSet.next()) {
				
						strTime = resultSet.getString(1);
						
					}
						
				} catch (SQLException e) {
					throw e;
				}
				finally
				{
					try{
						if(resultSet != null)
						{
							resultSet.close();	
							resultSet=null;
						}
					}catch(SQLException se)
					{
						
					}
					try{
						if(cstmt != null)
						{
							cstmt.close();	
							cstmt=null;
						}
					}catch(SQLException se)
					{
						
					}
					
				}

		return strTime;
	}

}
