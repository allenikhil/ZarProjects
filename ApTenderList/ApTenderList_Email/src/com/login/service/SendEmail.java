package com.login.service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import com.vupadhi.Bean.TenderBean;

public class SendEmail {

	static Session session;
	private static final String EMAIL_FROM = "webmaster-eproc@ap.gov.in";
	private static final String EMAIL_CC = "infra.vts@vupadhi.com,srinivas.sagi@vupadhi.com,kalyanreddy@vupadhi.com,coo@vupadhi.com";
	private static final String EMAIL_TO = "suryateja.n@vupadhi.com,pavankumar.t@vupadhi.com,udayabhaskar@vupadhi.com,ashokkumar.p@vupadhi.com,ravindranathguptha.p@vupadhi.com";
	private static final String EMAIL_SUBJECT = "Relevant tenders for business opportunity in Andhra Pradesh";

	public static void main(String[] args) {

		Properties props = new Properties();

		props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.host", "relay.emailgov.in");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.smtp.port", "465");
	    props.put("mail.smtp.debug", "true");

		// creating session object to get properties
		session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("webmaster-eproc@ap.gov.in", "Raj@Vupadhi99");
			}
		});
		try {
			sendEmail();
		} catch (ClassNotFoundException | SQLException | ParseException e) {
			e.printStackTrace();
		}
	}

	public static void sendEmail() throws SQLException, ClassNotFoundException, ParseException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

		    
		  String  userName = "eprouser";
		    String password = "G00le*App@vupadh1!";
		    String url = "jdbc:sqlserver://10.10.15.59;databaseName=APTMS";
		Connection con = DriverManager.getConnection(url, userName, password);
		Statement s1 = con.createStatement();

		//LocalDate date = LocalDate.now();
		//System.out.println("one day before current date is " + date.minusDays(1).toString());

		//String publishedDate = date.minusDays(1L).toString();

		ResultSet results = s1.executeQuery("SELECT [nTenderID],[sTenderNo],[sNameOfWork],[sDepartment],[sCircleDivisionName],CONVERT(varchar(10),[dtPreBidOpeningDate],103) + right(CONVERT(varchar(32),[dtPreBidOpeningDate],100),8) AS [dtPreBidOpeningDate],CONVERT(varchar(10),[dtTenderOpeningDate],103) + right(convert(varchar(32),[dtTenderOpeningDate],100),8) AS dtTenderOpeningDate, CONVERT(varchar(10),[dtBidSubmissionClosingDate],103) + right(convert(varchar(32),[dtBidSubmissionClosingDate],100),8) AS [dtBidSubmissionClosingDate],mEstimatedCost,case ntendercategory when 101 then 'Works' when 102 then 'Products' when 104 then 'Services' end as  ntendercategory FROM tbltender t JOIN dbo.Split('E-procurement,Software,Website Development,SI,Design & Development,System Integrator,Application service provider,EOI,IT,operation & maintenance,O&M,Implementation,migration,Integration,Digital,Mobile,call center,support,service provider,Agency',',') s ON (t.sNameOfWork LIKE N'%'+s.Items+'%' OR [t].[sTypeOfWork] LIKE N'%'+s.Items+'%' OR [t].[sTenderSubject] LIKE N'%'+s.Items+'%')\r\nwhere dtPublishedDate >=getdate() and nStatus=105");
		
		
		Map<String, TenderBean> list = new LinkedHashMap<String, TenderBean>();

		if (results != null) {
			while (results.next()) {
				TenderBean bean = new TenderBean();
		        bean.setnTenderID(results.getString("nTenderID"));
		        bean.setsTenderNo(results.getString("sTenderNo"));
		        bean.setsNameOfWork(results.getString("sNameOfWork"));
		        bean.setsDepartment(results.getString("sDepartment"));
		        bean.setsCircleDivisionName(results.getString("sCircleDivisionName"));
		        if (results.getString("dtPreBidOpeningDate") == null) {
		          bean.setDtPreBidOpeningDate("N/A");
		        } else {
		          bean.setDtPreBidOpeningDate(results.getString("dtPreBidOpeningDate"));
		        } 
		        bean.setBidSubmissionStartDate(results.getString("dtTenderOpeningDate"));
		        bean.setDtBidSubmissionClosingDate(results.getString("dtBidSubmissionClosingDate"));
		        bean.setmECV(results.getString("mEstimatedCost"));
		        bean.setnTenderCategory(results.getString("nTenderCategory"));
		        list.put(results.getString("nTenderID"), bean);
			}
		}

		try {
			Message msg = new MimeMessage(session);

			msg.setFrom(new InternetAddress(EMAIL_FROM));

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(EMAIL_TO));
			msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(EMAIL_CC));

			msg.setSubject(EMAIL_SUBJECT);

			final StringBuilder sb = new StringBuilder();
			sb.append(
					"<h3 align='center'><FONT face='Arial'> <strong><u>RELEVANT TENDERS FOR BUSINESS OPPORTUNITY IN ANDHRA PRADESH </u></strong></FONT></h3><br/>");
			sb.append("<table cellpadding='3' cellspacing='1' width='100%' border='1'>");
			sb.append("<FONT face='MS Sans Serif' size=3>");
			sb.append("<tr>");
			sb.append("<th>S.No&nbsp</th>");
		      sb.append("<th>Tender ID</th>");
		      sb.append("<th>Tender No</th>");
		      sb.append("<th>Name of Work</th>");
		      sb.append("<th>Department</th>");
		      sb.append("<th>Circle</th>");
		      sb.append("<th>Tender Category</th>");
		      sb.append("<th>ECV</th>");
		      sb.append("<th>Pre-bid Meeting Date</th>");
		      sb.append("<th>Bid Submission Start Date</th>");
		      sb.append("<th>Bid Submission Closing Date</th>");
			

			int size = 1;
			for (Map.Entry<String, TenderBean> entry : list.entrySet()) {
				sb.append("<tr>");
				sb.append("<td>" + size + " </td>");
		        sb.append("<td>" + ((TenderBean)entry.getValue()).getnTenderID() + " </td>");
		        sb.append("<td>" + ((TenderBean)entry.getValue()).getsTenderNo() + " </td>");
		        sb.append("<td>" + ((TenderBean)entry.getValue()).getsNameOfWork() + " </td>");
		        sb.append("<td>" + ((TenderBean)entry.getValue()).getsDepartment() + " </td>");
		        sb.append("<td>" + ((TenderBean)entry.getValue()).getsCircleDivisionName() + " </td>");
		        sb.append("<td>" + ((TenderBean)entry.getValue()).getnTenderCategory() + "</td>");
		        sb.append("<td>" + ((TenderBean)entry.getValue()).getmECV() + "</td>");
		        sb.append("<td>" + ((TenderBean)entry.getValue()).getDtPreBidOpeningDate() + "</td>");
		        sb.append("<td>" + ((TenderBean)entry.getValue()).getBidSubmissionStartDate() + "</td>");
		        sb.append("<td>" + ((TenderBean)entry.getValue()).getDtBidSubmissionClosingDate() + "</td>");
				size++;
			}
			sb.append("</FONT>");
			sb.append("</table>");
			sb.append("* Quried based on keywords only");
			sb.append("<br/><br/>");
			sb.append("<strong>From VTS-BD Team</strong>");
			sb.append("<br/><br/>");
			sb.toString();

			String htmlText = sb.toString();
			MimeMultipart multipart = new MimeMultipart("related");
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(htmlText, "text/html");
			multipart.addBodyPart(messageBodyPart);

			msg.setContent(multipart);
			Transport.send(msg);
			System.out.println("Send");

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}