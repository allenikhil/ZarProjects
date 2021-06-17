package com.vupadhi.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import javax.sql.DataSource;
/*import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Repository;*/
import com.vupadhi.util.DataSourceFactory;



//@Repository
public class HelpDeskCallLogDAO {
	
	static CallableStatement objCommand = null;
	
	public static  String TBL_USER = "{call dbo.SP_VTMS_User(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	
	public static final CallableStatement BuildCommand(final String strParamName, final HashMap<String, String> nvcParams) {
	

		DataSource ds = DataSourceFactory.getDataSource();
		Connection connection = null;
				try {
					
					connection = ds.getConnection();
					objCommand = connection.prepareCall(TBL_USER);
					objCommand.setString(1, strParamName);						
					objCommand.setString(2,nvcParams.get("nUserID"));
					objCommand.setString(3,nvcParams.get("nDepartmentID"));
					objCommand.setString(4,nvcParams.get("sName"));
					objCommand.setString(5,nvcParams.get("sAddress1"));
					objCommand.setString(6,nvcParams.get("sAddress2"));
					objCommand.setString(7,nvcParams.get("sPhone"));
					objCommand.setString(8,nvcParams.get("sFax"));
					objCommand.setString(9,nvcParams.get("sEMail"));
					objCommand.setString(10,nvcParams.get("sDesignation"));
					objCommand.setString(11,nvcParams.get("nRoleID"));
					objCommand.setString(12,nvcParams.get("sCertPath"));
					objCommand.setString(13,nvcParams.get("sPublicKey"));
					objCommand.setString(14,nvcParams.get("sSubjectDN"));
					objCommand.setString(15,nvcParams.get("sCertSerialNo"));
					objCommand.setString(16,nvcParams.get("sCertIssuer"));
					objCommand.setString(17,nvcParams.get("dtCertExpiry"));
					objCommand.setString(18,nvcParams.get("sThumbPrint"));
					objCommand.setString(19,nvcParams.get("nActionUserID"));
					objCommand.setString(20,nvcParams.get("sImagePath"));
					objCommand.setString(21,nvcParams.get("nProcurementID"));
					objCommand.setString(22,nvcParams.get("sMessage"));
					objCommand.setString(23,nvcParams.get("nOfficeID"));
					objCommand.setString(24,nvcParams.get("nLevelID"));
					objCommand.setString(25,nvcParams.get("nParentDepartmentID"));
					objCommand.setString(26,nvcParams.get("sResult"));
					objCommand.setString(27,nvcParams.get("dtDateofCharge"));
					objCommand.setString(28,nvcParams.get("sOTPValue"));
					objCommand.setString(29,nvcParams.get("nStatusID"));
					
					objCommand.setString(30,nvcParams.get("loginUserId"));
					objCommand.setString(31,nvcParams.get("password"));
					objCommand.setString(32,nvcParams.get("sUserName"));
					objCommand.setString(33,nvcParams.get("pimsId"));
					objCommand.setString(34,nvcParams.get("sOwnerName"));
					objCommand.setString(35,nvcParams.get("sContactNumber"));
					objCommand.setString(36,nvcParams.get("sPhone"));
				

				} catch (Exception e) {
					e.printStackTrace();

				
			}

		return objCommand;
	
	}




	








}



