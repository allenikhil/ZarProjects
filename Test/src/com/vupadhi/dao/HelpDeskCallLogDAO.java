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
	
	//private final String CLASS_NAME = this.getClass().getCanonicalName();
	static CallableStatement objCommand = null;
	
	public static String TBL_helpdeskCrm = "{call dbo.SP_VTMS_AutoMaticEmails(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static String vtmsEmailScheduler = "{call dbo.SP_VTMS_AutoMaticEmailsLog(?,?,?,?,?,?,?)}";
	
	public static final CallableStatement BuildCommand(final String strParamName, final HashMap<String, String> nvcParams) {
	
	//	final String METHOD_NAME = "HelpDeskCallLogDAO";

		DataSource ds = DataSourceFactory.getDataSource();
		Connection connection = null;
				try {
					
					connection = ds.getConnection();

					
					objCommand = connection.prepareCall(TBL_helpdeskCrm);
					objCommand.setString(1, strParamName);
					objCommand.setString(2,nvcParams.get("sUserName"));
					objCommand.setString(3,nvcParams.get("panCard"));
					objCommand.setString(4,nvcParams.get("nUserID"));
					objCommand.setString(5,nvcParams.get("sCompanyName"));
					objCommand.setString(6,nvcParams.get("sContactNumber"));
					objCommand.setString(7,nvcParams.get("sEMail"));
					objCommand.setString(8,nvcParams.get("sTicketNumber"));
					objCommand.setString(9,nvcParams.get("nHelpDeskID"));
					objCommand.setString(10,nvcParams.get("nUserType"));
					objCommand.setString(11,nvcParams.get("dtStartDatetime"));
					objCommand.setString(12,nvcParams.get("sCategory"));
					objCommand.setString(13,nvcParams.get("nTenderID"));
					objCommand.setString(14,nvcParams.get("nPaymentID"));
					objCommand.setString(15,nvcParams.get("sRemarks"));
					objCommand.setString(16,nvcParams.get("bIssue"));
					objCommand.setString(17,nvcParams.get("bRemoteDesk"));
					objCommand.setString(18,nvcParams.get("bCallMade"));
					objCommand.setString(19,nvcParams.get("nAssignedTo"));
					objCommand.setString(20,nvcParams.get("dtEndDateTime"));
					objCommand.setString(21,nvcParams.get("dtTrainingDate"));
					objCommand.setString(22,nvcParams.get("sParticipateName"));
					objCommand.setString(23,nvcParams.get("sDescription"));
					objCommand.setString(24,nvcParams.get("nMembersCount"));
					objCommand.setString(25,nvcParams.get("sTrainingGivenBy"));
					objCommand.setString(26,nvcParams.get("sFileName"));
					objCommand.setString(27,nvcParams.get("sFilePath"));
					objCommand.setString(28,nvcParams.get("nCallLogIDn"));
					objCommand.setString(29,nvcParams.get("nServiceType"));
					objCommand.setString(30,nvcParams.get("nDepartmentID"));
					objCommand.setString(31,nvcParams.get("ParentnDepartmentID"));
					objCommand.setString(32,nvcParams.get("sReceivedFrom"));
					objCommand.setString(33,nvcParams.get("sSubject"));
					objCommand.setString(34,nvcParams.get("dtReceivedDate"));
					objCommand.setString(35,nvcParams.get("nEmailLogID"));
					objCommand.setString(36,nvcParams.get("dtCustomDate"));
					objCommand.setString(37,nvcParams.get("dtCurrentDate"));
					objCommand.setString(38,nvcParams.get("sParentDepartment"));
					objCommand.setString(39,nvcParams.get("sPANNo"));
					objCommand.setString(40,nvcParams.get("updatedStatus"));
					objCommand.setString(41,nvcParams.get("nHDUserType"));
					objCommand.setString(42,nvcParams.get("sName"));
					objCommand.setString(43,nvcParams.get("nStatus"));
					objCommand.setString(44,nvcParams.get("nProjectType"));
					
					
					//poorna
					
					objCommand.setString(45,nvcParams.get("dtActivated"));
					objCommand.setString(46,nvcParams.get("bBidderType"));
					objCommand.setString(47,nvcParams.get("bBidderTypeVal"));
					objCommand.setString(48,nvcParams.get("nNewUser"));
					objCommand.setString(49,nvcParams.get("nCreatedUserID"));
					objCommand.setString(50,nvcParams.get("sRegFileName"));
					objCommand.setString(51,nvcParams.get("sGSTFileName"));
					objCommand.setString(52,nvcParams.get("sPANFileName"));
					objCommand.setString(53,nvcParams.get("sCivilORSSNFileName"));
					objCommand.setString(54,nvcParams.get("sShareholderFileName"));
					objCommand.setString(55,nvcParams.get("sTenancyFileName"));
				

				} catch (Exception e) {
					e.printStackTrace();

				
			}

		return objCommand;
	
	}




	public static int deptErrorLog(String sErrorMessage, String sErrorType, String sErrorDescription, String sScreenName, String sClassName, String sMethodName) throws SQLException {
		int updated = 0;
		DataSource ds = DataSourceFactory.getDataSource();
		CallableStatement cstmt = null;
		Connection conn = null;
		try {
			conn = ds.getConnection();
			cstmt = conn.prepareCall(vtmsEmailScheduler);
			cstmt.setString(1, "SV");
			cstmt.setString(2, sErrorMessage);
			cstmt.setString(3, sErrorType);
			cstmt.setString(4, sErrorDescription);
			cstmt.setString(5, sScreenName);
			cstmt.setString(6, sClassName);
			cstmt.setString(7, sMethodName);
			updated = cstmt.executeUpdate();
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				if (cstmt != null) {
					cstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception fe) {
				deptErrorLog(fe.getMessage(), fe.toString(), "Error Log Failed", "deptErrorLog", HelpDeskCallLogDAO.class.getName(), "deptErrorLog");
			}
		}
		return updated;
	}








}



