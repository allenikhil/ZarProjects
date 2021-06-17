package com.vupadhi.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateFormatingStyle {
	public static final String DATE_DDMMYYYYHM = "dd/MM/yyyy hh:mm a";
	

	public static String formatToddMMyyyyhhmma(String date) {
		if (date != null && !"".equals(date) && date.length() != 0) {
			String formattedDate = "";
			try {
				formattedDate = new SimpleDateFormat(DATE_DDMMYYYYHM).format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date));
			} catch (ParseException e) {
			}
			return formattedDate;
		}
		return null;
	}
}
