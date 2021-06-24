package com.vupadhi.constants;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MailAppConstants {

	public static Properties prop = getPropertyObj();
	
	public static Properties getPropertyObj() {

		if (prop == null) {
			prop = new Properties();
		}

		InputStream input = null;
		String propFileName = "config.properties";

		input = Thread.currentThread().getContextClassLoader().getResourceAsStream(propFileName);
		try {
			prop.load(input);
		}
		catch (IOException e) {

		}
		finally {
			if (input != null) try {
				input.close();
			}
			catch (IOException e) {
				// TODO Auto-generated catch block

			}
		}

		return prop;
	}
	
	public static Properties getExtPropertyObj() {
		if (prop == null) {
			prop = new Properties();
		}
		InputStream input = null;
		try {
			input = new FileInputStream(getPropertyObj().getProperty("file.extenPath"));
			prop.load(input);
		}
		catch (IOException e) {

		}
		finally {
			if (input != null) try {
				input.close();
			}
			catch (Exception e) {

			}
		}

		return prop;
	}
	
	
	public static String getsEmailUserName() {
		String filepath = null;
		filepath = getExtPropertyObj().getProperty("file.email.username");
		return filepath;
	}
	public static String getsEmailPassword() {
		String filepath = null;
		filepath = getExtPropertyObj().getProperty("file.email.password");
		return filepath;
	}
	
	public static String getsEmailHost() {
		String filepath = null;
		filepath = getExtPropertyObj().getProperty("file.email.host");
		return filepath;
	}
	
	public static String getsEmailPort() {
		String filepath = null;
		filepath = getExtPropertyObj().getProperty("file.email.port");
		return filepath;
	}
	

	
	public static final String EMAIL_USERNAME = getsEmailUserName();
	public static final String	FROMNAME= prop.getProperty("file.email.fromname");
	public static final String EMAIL_PASSWORD = getsEmailPassword();
	public static final String Excel_Sheet_FilePath = getPropertyObj().getProperty("excel.filepath");
	public static final String MailHost =getsEmailHost();
	public static final String MailPort = getsEmailPort();
	public static final String	SMTP_USERNAME= prop.getProperty("file.smtp.username");
	public static final String	SMTP_PASSWORD= prop.getProperty("file.smtp.password");
	

}
