
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.ChannelSftp.LsEntry;

public class DownloadMultipleFilesFromSftpServer {
	static Properties props;
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws FileNotFoundException, IOException {
		props = new Properties();
		props.load(new FileInputStream("properties/common.properties"));
		String SFTPHOST = props.getProperty("serverAddress").trim();
		int SFTPPORT = 22;
		String SFTPUSER = props.getProperty("userId").trim();
		String SFTPPASS = props.getProperty("password").trim();
		String SFTPWORKINGDIR = props.getProperty("remoteDirectory").trim();

		Session session = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;

		try {
			JSch jsch = new JSch();
			session = jsch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
			session.setPassword(SFTPPASS);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			channel = session.openChannel("sftp");
			channel.connect();
			channelSftp = (ChannelSftp) channel;
			channelSftp.cd(SFTPWORKINGDIR);

			Vector filelist = channelSftp.ls(SFTPWORKINGDIR);
			for (int i = 0; i < filelist.size(); i++) {
				LsEntry entry = (LsEntry) filelist.get(i);
				// System.out.println(entry.getFilename());

				byte[] buffer = new byte[1024];
				BufferedInputStream bis = new BufferedInputStream(channelSftp.get(entry.getFilename()));
				File newFile = new File("D:/SftpFiles/" + entry.getFilename());
				OutputStream os = new FileOutputStream(newFile);
				BufferedOutputStream bos = new BufferedOutputStream(os);
				int readCount;
				while ((readCount = bis.read(buffer)) > 0) {
					System.out.println("Writing: ");
					bos.write(buffer, 0, readCount);
				}
				bis.close();
				bos.close();
			}
		}

		catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("Download completed and Saved in D:/SftpFiles");
	}

}
