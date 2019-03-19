package com.allinfinance.mss.socket.client;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class ClientIoHandler extends IoHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(ClientIoHandler.class);

    public ClientIoHandler() {
        super();
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        logger.info("连接创建@" + session);
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        String clientIP = ((InetSocketAddress) session.getRemoteAddress()).getAddress().getHostAddress();
        int port = ((InetSocketAddress) session.getRemoteAddress()).getPort();
        logger.info("连接打开@" + session + ", 远端IP:" + clientIP + ", 远端PORT:" + port);
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        logger.info("连接关闭@" + session);
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        logger.error("连接超时@" + session);
        session.closeNow();
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        logger.error("连接异常@" + session, cause);
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        logger.debug("报文格式的应答信息：" + message);
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        // TODO Auto-generated method stub
    }
}
