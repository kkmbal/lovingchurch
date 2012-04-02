package lc.common.util;

import com.sshtools.j2ssh.SftpClient;
import com.sshtools.j2ssh.SshClient;
import com.sshtools.j2ssh.authentication.AuthenticationProtocolState;
import com.sshtools.j2ssh.authentication.PasswordAuthenticationClient;

public class FileUtil {
	
    public static final String SFTP_HOST             = "1.234.40.17"; 
    public static final String SFTP_ID             = "webserver"; 
    public static final String SFTP_PW             = "web123;";   
    public static final int   SFTP_TIMEOUT        = 5 * 60 * 1000;
    
	public static boolean uploadSFTP(String sDir, String userID, String uploadFile)  throws Exception {
		boolean bRet = true;
		
		try {            
			//System.out.println(">>>>>>>>>>>>>>>> uploadSFTP start");
			SshClient client = new SshClient();
			client.setSocketTimeout(SFTP_TIMEOUT);
			client.connect(SFTP_HOST);
			PasswordAuthenticationClient auth = new PasswordAuthenticationClient(); 
			auth.setUsername(SFTP_ID);            
			auth.setPassword(SFTP_PW); 
			int result = client.authenticate(auth);            
			if (result != AuthenticationProtocolState.COMPLETE) {                 
				throw new Exception("Login to " + SFTP_HOST + ":22" + SFTP_ID + "/" + SFTP_PW + " failed");            
			}            
			SftpClient sftp = client.openSftpClient();  
			
			sftp.cd(sDir);
			try {            
				sftp.cd(userID);    
			} 
			catch(Exception e) {  
				sftp.mkdir(userID);
				sftp.cd(userID);
			}

			sftp.put(uploadFile);
			
			sftp.quit();
			client.disconnect();
			//System.out.println(">>>>>>>>>>>>>>>> uploadSFTP end");
		} 
		catch (Exception e) {  
			//System.out.println( e.getMessage());
			bRet = false;
			//e.printStackTrace();
			throw e;
			
		} 
		return bRet;
	}
}
