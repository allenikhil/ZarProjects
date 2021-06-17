package com.vupadhi.dao;

import java.sql.CallableStatement;
import java.text.ParseException;
import java.util.HashMap;


public class HelpDeskCallLogBAO {

	public static CallableStatement getCCMailsFromDB()
			throws ParseException {

		HashMap<String, String> nvcEvaluation = new HashMap<>();

		CallableStatement cstmt = HelpDeskCallLogDAO.BuildCommand("CMT",
				nvcEvaluation);
		return cstmt;
	}
	public static CallableStatement getToMailsFromDB()
			throws ParseException {

		HashMap<String, String> nvcEvaluation = new HashMap<>();

		CallableStatement cstmt = HelpDeskCallLogDAO.BuildCommand("TMT",
				nvcEvaluation);
		return cstmt;
	}
	
	public static CallableStatement GetDBTime() {
		HashMap<String, String> nvcEvaluation = new HashMap<String, String>();
		CallableStatement cstmt = HelpDeskCallLogDAO.BuildCommand("DT", nvcEvaluation);
		return cstmt;
	}
	
	public static CallableStatement getTimeDiffrenceGap() {
		HashMap<String, String> nvcEvaluation = new HashMap<String, String>();
		CallableStatement cstmt = HelpDeskCallLogDAO.BuildCommand("TDG", nvcEvaluation);
		return cstmt;
	}

}
