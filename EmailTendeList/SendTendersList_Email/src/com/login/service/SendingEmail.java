package com.login.service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Properties;



public class SendingEmail {

	static Session session;
	private static final String EMAIL_FROM = "tendersupport@a2zprocure.com";
	private static final String EMAIL_PASSWORD = "^!67+o6HyuVE";
	private static final String EMAIL_TO = "nikhil.a@vupadhi.com";
	private static final String EMAIL_SUBJECT = "Testing Mail";
	private static final String HOST = "mail.a2zprocure.com";

	public static void main(String[] args) {

		Properties props = new Properties();

		props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.host", HOST);
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.smtp.port", "587");
	    props.put("mail.smtp.debug", "true");

		// creating session object to get properties
		session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(EMAIL_FROM, EMAIL_PASSWORD);
			}
		});
		try {
			sendEmail();
		} catch (ClassNotFoundException | SQLException | ParseException e) {
			e.printStackTrace();
		}
	}

	public static void sendEmail() throws SQLException, ClassNotFoundException, ParseException {


		try {
			Message msg = new MimeMessage(session);

			msg.setFrom(new InternetAddress(EMAIL_FROM));

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(EMAIL_TO));

			msg.setSubject(EMAIL_SUBJECT);
			msg.setText("This is simple program of sending email using JavaMail API");

			Transport.send(msg);
			System.out.println("Send");

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}