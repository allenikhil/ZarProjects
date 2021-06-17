import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
 
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.Selectors;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;
public class GetMyFiles {
	static Properties props;
	 //https://www.mysamplecode.com/2013/06/sftp-apache-commons-file-download.html
	 public static void main(String[] args) {
	 
	  GetMyFiles getMyFiles = new GetMyFiles();
//	  if (args.length < 1)
//	  {
//	   System.err.println("Usage: java " + getMyFiles.getClass().getName()+
//	   " Properties_filename File_To_Download ");
//	   System.exit(1);
//	  }
	 
//	  String propertiesFilename = args[0].trim();
//	  String fileToDownload = args[1].trim();
	  String propertiesFilename = "common.properties";
	 // String fileToDownload = "manual_en.pdf";
	  String fileToDownload = "*";
	  
	  getMyFiles.startFTP(propertiesFilename, fileToDownload);
	    
	 }
	 
	 public boolean startFTP(String propertiesFilename, String fileToDownload){
	 
	  props = new Properties();
	  StandardFileSystemManager standardFileSystemManager = new StandardFileSystemManager();
	 
	  try {
	 
	   props.load(new FileInputStream("properties/" + propertiesFilename));
	   String serverAddress = props.getProperty("serverAddress").trim();
	   String userId = props.getProperty("userId").trim();
	   String password = props.getProperty("password").trim();
	   String remoteDirectory = props.getProperty("remoteDirectory").trim();
	   String localDirectory = props.getProperty("localDirectory").trim();
	 
	    
	   //Initializes the file manager
	   standardFileSystemManager.init();
	    
	   //Setup our SFTP configuration
	   FileSystemOptions fileSystemOptions = new FileSystemOptions();
	   SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(fileSystemOptions, "no");
	   SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(fileSystemOptions, true);
	   SftpFileSystemConfigBuilder.getInstance().setTimeout(fileSystemOptions, 10000);
	    
	   //Create the SFTP URI using the host name, userid, password,  remote path and file name
	   String sftpUri = "sftp://" + userId + ":" + password +  "@" + serverAddress + "/" +  remoteDirectory + fileToDownload;
	    System.out.println(sftpUri);
	    
	   // Create local file object
	   String filepath = localDirectory +"/"+ fileToDownload;
	   File file = new File(filepath);
	   FileObject localFile = standardFileSystemManager.resolveFile(file.getAbsolutePath());
	 
	   // Create remote file object
	   FileObject remoteFile = standardFileSystemManager.resolveFile(sftpUri, fileSystemOptions);
	 
	   // Copy local file to sftp server
	   System.out.println("File is downloading...");
	   localFile.copyFrom(remoteFile, Selectors.SELECT_SELF);
	   System.out.println("File download successful");
	 
	  }
	  catch (Exception ex) {
	   ex.printStackTrace();
	   return false;
	  }
	  finally {
	   standardFileSystemManager.close();
	  }
	 
	  return true;
	 }
}
