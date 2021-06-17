package com.vupadhi.service;

import java.net.URLEncoder;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
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

import com.vupadhi.bean.DepartmentUsersBean;
import com.vupadhi.dao.HelpDeskCallLogBAO;

public class HDusersMailService {

	static Logger logger = LogManager.getLogger(HDusersMailService.class);
	// creating session object to get properties
	static Session session;	
	static String htmlHeader;
	
	public static String sendMailToDeptUsersAutomatically(final String Email,String UserName,String sUserId,String domainurl) throws Exception {


		String res = "fail";
		Message message = null;
		String htmlText = null;

		Properties props = new Properties();

		props.put("mail.transport.protocol", "smtp");
    	props.put("mail.smtp.port", "587"); 
    	props.put("mail.smtp.starttls.enable", "true");
    	props.put("mail.smtp.auth", "true");
    	

		if (Email != null) {
			
			final StringBuilder sb = new StringBuilder();
			String product = "CPWD e-Tender";
				String base64encodedString=null;
				base64encodedString=doEncryption(sUserId);
				base64encodedString =URLEncoder.encode( base64encodedString, "UTF-8" );  
				String passurl= domainurl+"/forgotchangepwdnew.html?"+"zxy123s1022="+base64encodedString+"";
				
			try {
				
				Authenticator auth = new Authenticator() {
					public PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("AKIA4SGPCTM6L2B25REU", "BGeUe2LOgpBYSDKzXKY+sJgebNIJHJBvtUBAAz+fjdWl");
					}
				};
				Session session = Session.getInstance(props, auth);
				
			sb.append("Dear <b>" +UserName+ ", </b><br/>");
			sb.append("Department User Created with User ID <b>" +sUserId+ "</b>");
			sb.append("<p><FONT face='MS Sans Serif' size=2><b>" + product
						+ " - For Changing Password you can click on link.<br><a target=\"_blank\" href='" + passurl + "'>" + passurl
						+ "</a> <br>");
			sb.append("Please contact support for further clarifications.<br/>");
			sb.append("<br/> ");
			sb.append("Regards <br/> ");
			sb.append("CPWD Team <br/>");

			sb.toString();

			StringBuffer footer=new StringBuffer();
			footer.append("<br /><br /><FONT face='MS Sans Serif' size=1> Mail Disclaimer:<br /><br />");
			footer.append("<center>This is only an automated mail alert notification from system and is not to be treated as a legal communication or confirmation. Please do not reply to this mail. If you have any queries  mail us at tendersupport@TechMahindra.com</center><br /><br />");
			message = new MimeMessage(session);
			message.setFrom(new InternetAddress("no-reply@cpwdcld.net"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(Email));
			message.setSubject("Department User Creation Credentials");


			htmlText = sb.toString()+footer.toString();


			MimeMultipart multipart = new MimeMultipart("related");
			BodyPart messageBodyPart = new MimeBodyPart();

				
				messageBodyPart.setContent(htmlText, "text/html");
				multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);
			// Create a transport.
			  Transport transport = session.getTransport();
			              
			  // Send the message.
			  try
			  {
				  logger.info("Sending...");
			      
			      // Connect to Amazon SES using the SMTP username and password you specified above.
			      transport.connect("email-smtp.us-east-1.amazonaws.com", "AKIA4SGPCTM6L2B25REU", "BGeUe2LOgpBYSDKzXKY+sJgebNIJHJBvtUBAAz+fjdWl");
			  	
			      // Send the email.
			      transport.sendMessage(message, message.getAllRecipients());
			      logger.info("Email sent! for Email:"+Email);
			      res="success";
			  }
			  catch (Exception ex) {
			  	
				  logger.info("The email was not sent with email Id:"+Email);
				  logger.info("Error message: " + ex.getMessage());
			      ex.printStackTrace();
					
			  }
			  finally
			  {
			      // Close and terminate the connection.
			      transport.close();
			  }
					 
			}catch (Exception e) {
				logger.info("The email was not sent with email Id:"+Email);
				logger.info("Error message: " + e.getMessage());
			}if(res.equals("success")){
				logger.info("Email send to :"+Email);
				logger.info("Email Subject is  :"+message.getSubject());
				logger.info("Email Content is :"+htmlText);
			}else{
				logger.info("The email was not sent with email Id:"+Email);
				logger.info("Sending an email to this "+Email+" got failed with subject "+message.getSubject()+"of user Id  "+sUserId+"With Content : "+htmlText);
			}
		}

		return res;

	}


public static String doEncryption(String InputText)  {
	//This salt varibale is used to generate encript and decript a
	 byte[] salt = { (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
			(byte) 0x56, (byte) 0x35, (byte) 0xE3, (byte) 0x03 };
	// Iteration count
	 int iterationCount = 19;
	  String saltkey="VuP%&^@k#P*l";
	  String password;
	byte[] outputBytes=null;
	try {
		
		 
		 KeySpec keySpec = new PBEKeySpec(saltkey.toCharArray(), salt,iterationCount);
		 SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
		 // Prepare the parameter to the ciphers
		 AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt,iterationCount);

		// Enc process
		Cipher ecipher = Cipher.getInstance(key.getAlgorithm());
		ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
		
		outputBytes = ecipher.doFinal(InputText.getBytes());
	
		outputBytes = org.apache.commons.codec.binary.Base64.encodeBase64(outputBytes);
	} catch (Exception e) {
		
	}
	return new String(outputBytes);
}
	
public static List<DepartmentUsersBean> getDepartmentUsers() throws SQLException{
	
	List<DepartmentUsersBean> list =new ArrayList<DepartmentUsersBean>();
	ResultSet rs = null;
	CallableStatement cstmt = null;

	try {
		cstmt = HelpDeskCallLogBAO.getDepartmentUsers();

		rs = cstmt.executeQuery();
			while (rs.next()) {
				DepartmentUsersBean departmentUsersBean = new DepartmentUsersBean();
				
				departmentUsersBean.setUserid(rs.getString("userid"));
				departmentUsersBean.setEmail(rs.getString("sEMail"));
				departmentUsersBean.setUserName(rs.getString("userName"));
			
				list.add(departmentUsersBean);
			}
	}catch(SQLException se)
	{
		
		throw se;
	}
	finally {
	       try {
				if (rs != null)
				{
				   rs.close(); 
				}
	            }catch(SQLException se) {
		        	
		        }
			 
		}
	
	return list;
}

}
