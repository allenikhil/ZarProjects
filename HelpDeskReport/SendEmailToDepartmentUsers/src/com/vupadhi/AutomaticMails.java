package com.vupadhi;

import com.vupadhi.dao.HelpDeskCallLogBAO;
import com.vupadhi.service.HDusersMailService;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.text.ParseException;
import com.vupadhi.bean.DepartmentUsersBean;

public class AutomaticMails {
	static Logger logger = LogManager.getLogger(AutomaticMails.class);
	public static void main(String[] args) throws Exception {
		
		Date time = new Date();
		
		System.err.println(time);
		logger.info(time);

		List<DepartmentUsersBean> list=HDusersMailService.getDepartmentUsers();
		String domainurl="https://etender.cpwdcld.net";
		Integer sendEmailsCount=0;
		logger.info("Total List of Users:::"+list.size());
		for(DepartmentUsersBean bean:list)
		{
			HDusersMailService.sendMailToDeptUsersAutomatically(bean.getEmail(),bean.getUserName(),bean.getUserid(),domainurl);
			sendEmailsCount++;
			logger.info("Sending "+sendEmailsCount+" of "+list.size());
		}
		logger.info("Totals Emails Send Count:::"+sendEmailsCount);
			//HDusersMailService.sendReportsMailAutomatically(null, null);
	}
	
	

		
}