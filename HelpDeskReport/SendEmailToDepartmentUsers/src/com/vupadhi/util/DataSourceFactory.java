package com.vupadhi.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

public class DataSourceFactory {
	
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
	
	
	public static String getDriverClassName() {
		String filepath = null;
		filepath = getPropertyObj().getProperty("jdbc.driverClassName");
		return filepath;
	}
	public static String getDriverURL() {
		String filepath = null;
		filepath = getPropertyObj().getProperty("jdbc.url");
		return filepath;
	}
	public static String getJDBCUserName() {
		String filepath = null;
		filepath = getPropertyObj().getProperty("jdbc.username");
		return filepath;
	}
	
	public static String getJDBCPassWord() {
		String filepath = null;
		filepath = getPropertyObj().getProperty("jdbc.password");
		return filepath;
	}
	
	
	

	public static DataSource getDataSource() {
	//	Properties prop = new Properties();

	//	String propFileName = "config.properties";
		InputStream input = null;

		BasicDataSource ds = new BasicDataSource();

		try {

		//	input = Thread.currentThread().getContextClassLoader().getResourceAsStream(propFileName);
		//	prop.load(input);

			ds.setDriverClassName(getDriverClassName());
			ds.setUrl(getDriverURL());
			ds.setUsername(getJDBCUserName());
			ds.setPassword(getJDBCPassWord());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (input != null)
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		return ds;
	}
}
