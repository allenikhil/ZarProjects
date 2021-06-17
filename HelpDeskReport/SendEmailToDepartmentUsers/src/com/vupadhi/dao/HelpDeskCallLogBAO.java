package com.vupadhi.dao;

import java.sql.CallableStatement;
import java.text.ParseException;
import java.util.HashMap;


public class HelpDeskCallLogBAO {

	
	
	public static CallableStatement getDepartmentUsers() {
		HashMap<String, String> nvcEvaluation = new HashMap<String, String>();
		CallableStatement cstmt = HelpDeskCallLogDAO.BuildCommand("TDU", nvcEvaluation);
		return cstmt;
	}

}
