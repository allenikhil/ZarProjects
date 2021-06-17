package com.vupadhi;

import com.vupadhi.dao.HelpDeskCallLogBAO;
import com.vupadhi.service.HDusersMailService;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;

public class AutomaticMails {
	
	public static void main(String[] args) throws Exception {
		
		
		String TIME_SERVER = "time-a.nist.gov";   

		//byte[] address = {(byte)169, (byte)254, (byte) 169, (byte) 123};

		//NTPUDPClient timeClient = new NTPUDPClient();
		//InetAddress inetAddress = InetAddress.getByName("169.254.169.123");
		//InetAddress inetAddress = InetAddress.getByAddress(address);
		//TimeInfo timeInfo = timeClient.getTime(inetAddress);
		//long returnTime = timeInfo.getMessage().getTransmitTimeStamp().getTime();
		Date time = new Date();
		
		System.err.println(time);
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		Date dtNow = new Date();
		String servertime = formatter.format(dtNow);
		
		String ntpservertime = formatter.format(time);		

		String dbtime = HDusersMailService.GetDBTime();

		try {
			dbtime = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
					.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dbtime));
		} catch (ParseException e) {

		}
		//ntpservertime="08/06/2021 04:56:00";
		//servertime="08/06/2021 04:03:47";
		//dbtime="08/06/2021 04:03:47";
		System.out.println("NTPserver Time::::"+ntpservertime);
		System.out.println("Applicationserver Time::::"+servertime);
		System.out.println("DBserver Time::::"+dbtime);
		String timeDifferece=AutomaticMails.getTimeDiffrenceGap();
		if(timeDifferece==null)
		{
			timeDifferece="30";
		}
		
		System.out.println("Time Difference Test::::"+timeDifferece); 
		//System.out.println("Time Difference between NTPServer and Applicationserver::::"+findDifference(ntpservertime,servertime));
		//System.out.println("Time Difference between NTPServer and DBServer::::"+findDifference(ntpservertime,dbtime));
		//System.out.println("Time Difference between ApplicationServer and DBServer::::"+findDifference(servertime,dbtime));

		if(findDifference(ntpservertime,servertime)>Long.parseLong(timeDifferece) ||
				findDifference(ntpservertime,dbtime)>Long.parseLong(timeDifferece) || 
				findDifference(servertime,dbtime)>Long.parseLong(timeDifferece))
		{
			HDusersMailService.sendTimeDifferenceMailAutomatically(ntpservertime,servertime,dbtime);
		}
			//HDusersMailService.sendReportsMailAutomatically(null, null);
	}
	
	// Function to print difference in
		// time start_date and end_date
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
					if(difference_In_Years!=0)
					{
						difference_In_Seconds=difference_In_Seconds+difference_In_Years*60*60*365;
						if(difference_In_Seconds<0)
						{
							difference_In_Seconds =difference_In_Seconds*-1;
						}
						return difference_In_Seconds;
					}
					if(difference_In_Days!=0)
					{
						difference_In_Seconds=difference_In_Seconds+difference_In_Days*60*60*24;
						if(difference_In_Seconds<0)
						{
							difference_In_Seconds =difference_In_Seconds*-1;
						}
						return difference_In_Seconds;
					}
					if(difference_In_Hours!=0)
					{
						difference_In_Seconds=difference_In_Seconds+difference_In_Hours*60*60;
						if(difference_In_Seconds<0)
						{
							difference_In_Seconds =difference_In_Seconds*-1;
						}
						return difference_In_Seconds;
					}
					if(difference_In_Minutes!=0)
					{
						difference_In_Seconds=difference_In_Seconds+difference_In_Minutes*60;
						if(difference_In_Seconds<0)
						{
							difference_In_Seconds =difference_In_Seconds*-1;
						}
						return difference_In_Seconds;
					}
				
			}

			// Catch the Exception
			catch (ParseException e) {
				e.printStackTrace();
			}
			return difference_In_Seconds;
		}

		public static String getTimeDiffrenceGap() throws Exception {
			String flagRequired;
			CallableStatement cstmt = null;
			ResultSet rs = null;
			flagRequired = "";
			try {
				cstmt = HelpDeskCallLogBAO.getTimeDiffrenceGap();
				rs = cstmt.executeQuery();
				while (rs.next()) {
					flagRequired = rs.getString("timeDiffrence");
				}
			} finally {
				try {
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException sQLException) {
				}
				try {
					if (cstmt != null) {
						cstmt.close();
					}
				} catch (SQLException sQLException) {
				}
			}
			return flagRequired;
		}
}