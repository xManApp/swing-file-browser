package com.example.filelist;

import com.example.Defaults;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;

/**
 * @author innokenty
 */
class FtpClientFactory {

    public static FTPClient buildSimpleClient(
            String hostname, int port, boolean useFTPS, String username, String password, int fileType)
            throws Exception {

        FTPClient client = useFTPS ? new FTPSClient() : new FTPClient();
        client.setDataTimeout(Defaults.FTP_DATA_TIMEOUT);
        client.setControlKeepAliveTimeout(Defaults.FTP_CONTROL_IDLE);
        client.connect(hostname, port);
        client.enterLocalPassiveMode();
        client.setSoTimeout(Defaults.FTP_SO_TIMEOUT);
        int reply = client.getReplyCode();

        if (!FTPReply.isPositiveCompletion(reply)) {
            throw new FtpClientException("Unable to connect! Sorry... " +
                    "Response code is " + reply + ", that's all I know");
        }

        if (!client.login(username, password)) {
            throw new FtpClientException("Unable to login to the ftp server");
        }
        client.setFileType(fileType);

        client.setKeepAlive(true);
        return client;
    }
}
