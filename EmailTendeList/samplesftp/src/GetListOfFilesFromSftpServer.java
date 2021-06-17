
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Vector;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class GetListOfFilesFromSftpServer {
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
		com.jcraft.jsch.Channel channel = null;
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
				// System.out.println(filelist.get(i).toString());
				LsEntry entry = (LsEntry) filelist.get(i);
				System.out.println(entry.getFilename());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
