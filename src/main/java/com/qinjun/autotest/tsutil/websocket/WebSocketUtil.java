/* Copyright(c), Qin Jun, All right serverd. */
package com.qinjun.autotest.tsutil.websocket;

import com.qinjun.autotest.tsutil.exception.ExceptionUtil;
import com.qinjun.autotest.tsutil.exception.TSUtilException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import java.net.URI;

public class WebSocketUtil {
    private final static Logger logger = LoggerFactory.getLogger(WebSocketUtil.class);
    private Session session;
    private MessageHandler messageHandler;

    public WebSocketUtil(URI uri) throws TSUtilException {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this,uri);
        }
        catch (Exception e) {
            String exceptionStackTrace = ExceptionUtil.getExceptionStackTrace(e);
            logger.error("Get exception:"+exceptionStackTrace);
            throw new TSUtilException(exceptionStackTrace);
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        this.session=null;
    }

    @OnMessage
    public void onMessage(String msg) {
        if (messageHandler!=null) {
            messageHandler.handleMessage(msg);
        }
    }

    public static interface MessageHandler {
        public void handleMessage(String msg);
    }

    public void addMessageHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }


    public void sendMessage(String msg) {
        session.getAsyncRemote().sendText(msg);
    }
}
