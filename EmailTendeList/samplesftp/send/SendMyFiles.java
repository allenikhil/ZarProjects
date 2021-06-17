package samplesftp.send;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.Selectors;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;
public class SendMyFiles {
	
	
	static Properties props;
	
	public static void main(String[] args) {
	 
	  SendMyFiles sendMyFiles = new SendMyFiles();
	  String propertiesFile = "common.properties";
	  String content = "This is the content to write into a file";
	   File file = new File("D:\\appdata\\AXISBankRefundFiles\\17102019_57147.txt");
	   FileWriter fw = null;
	try {
		fw = new FileWriter(file.getAbsoluteFile());
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   BufferedWriter bw = new BufferedWriter(fw);
	   try {
		bw.write(content);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   try {
		bw.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
	  
	  
	  sendMyFiles.startFTP(propertiesFile, file.getName(),file.getAbsolutePath());
	 
	 }
	 
	 @SuppressWarnings("deprecation")
	public boolean startFTP(String propertiesFilename, String fileToFTP,String filepath){
	 
	  props = new Properties();
	  StandardFileSystemManager manager = new StandardFileSystemManager();
	 
	  try {
	 
	   props.load(new FileInputStream("properties/" + propertiesFilename));
	   String serverAddress = props.getProperty("serverAddress").trim();
	   String userId = props.getProperty("userId").trim();
	   String password = props.getProperty("password").trim();
	   String remoteDirectory = props.getProperty("remoteDirectory").trim();
	   
	   
	   
	   
	
	  manager.init();
	    
	   //Setup our SFTP configuration
	   FileSystemOptions opts = new FileSystemOptions();
	   SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(
	     opts, "no");
	   SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(opts, true);
	   SftpFileSystemConfigBuilder.getInstance().setTimeout(opts, 10000);
	    
	   //Create the SFTP URI using the host name, userid, password,  remote path and file name
	   String sftpUri = "sftp://" + userId + ":" + password +  "@" + serverAddress + "/" + 
	     remoteDirectory + fileToFTP;
	   System.out.println(sftpUri);
	  
	  FileObject localFile = manager.resolveFile(filepath);
	 
	   // Create remote file object
	 FileObject remoteFile = manager.resolveFile(sftpUri, opts);
	 
	   // Copy local file to sftp server
	  remoteFile.copyFrom(localFile, Selectors.SELECT_SELF);
	   
	   System.out.println("File upload successful");
	 
	  }
	  catch (Exception ex) {
	   ex.printStackTrace();
	   return false;
	  }
	  finally {
	   manager.close();
	  }
	 
	  return true;
	 }
	 
}
