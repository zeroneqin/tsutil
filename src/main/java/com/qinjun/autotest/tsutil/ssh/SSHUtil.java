/* Copyright(c), Qin Jun, All right serverd. */
package com.qinjun.autotest.tsutil.ssh;


import com.jcraft.jsch.*;
import com.qinjun.autotest.tsutil.exception.ExceptionUtil;
import com.qinjun.autotest.tsutil.exception.TSUtilException;
import com.qinjun.autotest.tsutil.sleep.SleepUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SSHUtil {
    private final static Logger logger = LoggerFactory.getLogger(SSHUtil.class);

    static {
        Properties properties = new Properties();
        properties.put("StrictHostKeyChecking","no");
        JSch.setConfig(properties);
    }

    public static int executeSSHCmd(String host, String user, String pass, String cmd, StringBuilder stdout, StringBuilder stderr) throws TSUtilException {
        return executeSSHCmd( host,22,user,pass,cmd,stdout,stderr);
    }

    public static int executeSSHCmd(String host,int port, String user, String pass,String cmd,StringBuilder stdout, StringBuilder stderr)  throws TSUtilException {
        int result =-1;
        try {
            Session session = createSession(host,port,user, pass);
            Channel  channel = session.openChannel("exec");
            ChannelExec channelExec = (ChannelExec)channel;
            channelExec.setCommand(cmd);
            InputStream inputStreamStdout = channelExec.getInputStream();
            InputStream inputStreamStderr = channelExec.getErrStream();
            channel.connect();

            copy(channel,inputStreamStdout,stdout);
            copy(channel,inputStreamStderr,stderr);

            result = channel.getExitStatus();

            channel.disconnect();
            session.disconnect();
        }
        catch (Exception e) {
            String exceptionStackTrace = ExceptionUtil.getExceptionStackTrace(e);
            logger.error("Get exception:"+exceptionStackTrace);
            throw new TSUtilException(exceptionStackTrace);
        }

        return result;
    }

    private static Session createSession(String host, int port, String user, String pass) throws JSchException {
        JSch jsch = new JSch();
        Session session = jsch.getSession(user,host,port);
        session.setConfig("PreferredAuthentications","password,gssapi-with-mic,publickey,hostbased,keyboard-interactive");
        session.setConfig("StrictHostKeyChecking","no");
        session.setPassword(pass);
        session.connect();

        logger.debug("Create session to:"+host+", username:"+user+",password:"+pass);
        return session;
    }

    private static void copy(Channel channel, InputStream inputStream,StringBuilder sb) throws IOException {
        if((inputStream!=null) && (sb!=null)) {
            do {
                byte[] tmp = new byte[1024];
                while(inputStream.available()>0) {
                    int i= inputStream.read(tmp,0,1024);
                    if (i<0) {
                        break;
                    }
                    sb.append(new String(tmp,0,i));
                }
                if (channel.isClosed()) {
                    break;
                }
                SleepUtil.sleep(100);
            } while(true);
        }
    }

}
