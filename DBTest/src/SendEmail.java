
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;




public class SendEmail {

	public static void main(String[] args) {

		try
		{
			  String userName = "Tms"; String password = "Tms#432"; String url =
			  "jdbc:sqlserver://10.96.102.141;databaseName=cpwdtms";
			 
			
			/*
			 * String userName = "dev_team"; String password = "pass@word1"; String url =
			 * "jdbc:sqlserver://192.168.0.10\\MSDB;databaseName=a2ztms-test";
			 */
			 
		    System.out.println("Connecting To DB");
		    Connection con = DriverManager.getConnection(url, userName, password);
		    String strTime="";
		    Statement stmt = con.createStatement();
	         ResultSet rs = stmt.executeQuery("Select getdate()");
	         // Extract data from result set
	         while (rs.next()) {
	            // Retrieve by column name
	        	 strTime = rs.getString(1);
	         }
		    System.out.println("Connecting To DB Succssfully");

		    try {
		    	strTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
						.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			System.out.println("DBserver Time::::"+strTime);
			
			
			byte[] address = { (byte) 169, (byte) 254, (byte) 169, (byte) 123 };

			NTPUDPClient timeClient = new NTPUDPClient();
			InetAddress inetAddress = InetAddress.getByAddress(address);
			TimeInfo timeInfo = timeClient.getTime(inetAddress);
			long returnTime = timeInfo.getMessage().getTransmitTimeStamp().getTime();

			Date ntptime = new Date(returnTime);
			 
			 
			 
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			//Date ntptime = new Date();
			String ntpservertime = formatter.format(ntptime);		
			System.err.println("NTP Time::"+ntpservertime);
			
			Date dtserver = new Date();
			String servertime = formatter.format(dtserver);			
			System.err.println("Application Time::"+servertime);
			
			String timeDifferece="5";
			
			if (findDifference(ntpservertime, servertime) > Long.parseLong(timeDifferece)
					|| findDifference(ntpservertime, strTime) > Long.parseLong(timeDifferece)
					|| findDifference(servertime, strTime) > Long.parseLong(timeDifferece)) {
				sendTimeDifferenceMailAutomatically(ntpservertime, servertime, strTime);
			}
			 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static long findDifference(String start_date,
			String end_date)
{

	// SimpleDateFormat converts the
	// string format to date object
	SimpleDateFormat sdf
		= new SimpleDateFormat(
			"dd/MM/yyyy HH:mm:ss");
	long difference_In_Seconds=0;
	// Try Block
	try {

		// parse method is used to parse
		// the text from a string to
		// produce the date
		Date d1 = sdf.parse(start_date);
		Date d2 = sdf.parse(end_date);

		// Calucalte time difference
		// in milliseconds
		long difference_In_Time
			= d2.getTime() - d1.getTime();

		// Calucalte time difference in
		// seconds, minutes, hours, years,
		// and days
		 difference_In_Seconds
			= (difference_In_Time
			/ 1000)
			% 60;
		 
		 long difference_In_Minutes
			= (difference_In_Time
			/ (1000 * 60))
			% 60;

		long difference_In_Hours
			= (difference_In_Time
			/ (1000 * 60 * 60))
			% 24;

		long difference_In_Years
			= (difference_In_Time
			/ (1000l * 60 * 60 * 24 * 365));

		long difference_In_Days
			= (difference_In_Time
			/ (1000 * 60 * 60 * 24))
			% 365;

		
		System.out.print(
				"Difference "
				+ "between two dates is: ");

			System.out.println(
				difference_In_Years
				+ " years, "
				+ difference_In_Days
				+ " days, "
				+ difference_In_Hours
				+ " hours, "
				+ difference_In_Minutes
				+ " minutes, "
				+ difference_In_Seconds
				+ " seconds");
			
			difference_In_Seconds
			= (difference_In_Time
					/ 1000);
				
	}

	// Catch the Exception
	catch (ParseException e) {
		e.printStackTrace();
	}
	return  Math.abs(difference_In_Seconds);
}
	
	public static void sendTimeDifferenceMailAutomatically(String ntpservertime,String servertime,String dbtime) throws Exception {
		
		Properties props = new Properties();

		props.put("mail.transport.protocol", "smtp");
    	props.put("mail.smtp.port", "587"); 
    	props.put("mail.smtp.starttls.enable", "true");
    	props.put("mail.smtp.auth", "true");
    	
    	Message message = null;
		String htmlText = null;
		String mailSubject = "Time Difference Between NTPServer,ApplicationServer,DBServer.";
		InternetAddress[] toEmail = new InternetAddress[3];
    			toEmail[0]=new InternetAddress("udayabhaskar@vupadhi.com");
    			toEmail[1]=new InternetAddress("kiran.kumar@vupadhi.com");
    			toEmail[2]=new InternetAddress("srinivas.sagi@vupadhi.com");
    	
    	try {

			Session session = Session.getDefaultInstance(props);

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
			message.setFrom(new InternetAddress("no-reply@cpwdcld.net","CPWD"));
			message.setRecipients(Message.RecipientType.TO, toEmail);
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
		          transport.connect("email-smtp.us-east-1.amazonaws.com", "AKIA4SGPCTM6L2B25REU", "BGeUe2LOgpBYSDKzXKY+sJgebNIJHJBvtUBAAz+fjdWl");
		      	
		          // Send the email.
		          transport.sendMessage(message, message.getAllRecipients());
		          System.out.println("Email sent!");
		      }
		      catch (Exception ex) {
		      	
		          System.out.println("The email was not sent.");
		          System.out.println("Error message: " + ex.getMessage());
		          ex.printStackTrace();
		         
		      }
		      finally
		      {
		          // Close and terminate the connection.
		          transport.close();
		      }
//AWS Email Integrations end			      

		} catch (Exception e) {
			System.out.println("The email was not sent.");
	          System.out.println("Error message: " + e.getMessage());
			e.printStackTrace();
			}
	}
}