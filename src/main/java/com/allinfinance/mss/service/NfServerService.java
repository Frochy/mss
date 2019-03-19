package com.allinfinance.mss.service;

import com.allinfinance.mss.config.NfServerConfig;
import com.allinfinance.mss.socket.codec.MessageCodecFactory;
import com.allinfinance.mss.socket.codec.NfMessageDecoder;
import com.allinfinance.mss.socket.codec.NfMessageEncoder;
import com.allinfinance.mss.socket.server.NfServerIoHandler;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

@Configuration
public class NfServerService {
    private static final Logger logger = LoggerFactory.getLogger(NfServerService.class);

    @Autowired
    private NfServerConfig nfServerConfig;

    @Autowired
    private MssBizSevice mssBizSevice;

    @Bean
    public boolean shortServer() {
        try {
            logger.info("nfConfig:" + nfServerConfig);

            IoAcceptor acceptor = new NioSocketAcceptor(nfServerConfig.getProcessorCount());
            acceptor.getFilterChain().addLast("shortServerMsgCodec",
                    new ProtocolCodecFilter(new MessageCodecFactory(
                            new NfMessageDecoder(nfServerConfig.getMsgLengthSize(), nfServerConfig.getEncode()), new NfMessageEncoder(nfServerConfig.getMsgLengthSize(), nfServerConfig.getEncode()))));
            acceptor.getFilterChain().addLast("shortServerThreadPool", new ExecutorFilter(Executors.newCachedThreadPool()));
            acceptor.setHandler(new NfServerIoHandler(nfServerConfig.getOrgIds(), nfServerConfig.getServiceIds(), nfServerConfig.getEncode(), mssBizSevice));
            acceptor.getSessionConfig().setReadBufferSize(nfServerConfig.getBufferSize());
            acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, nfServerConfig.getTimeout());
            acceptor.setCloseOnDeactivation(true);
            acceptor.bind(new InetSocketAddress(nfServerConfig.getPort()));
        } catch (Exception e) {
            logger.error("shortServer exception!", e);
            return false;
        }

        logger.info("shortServer start success! port:" + nfServerConfig.getPort());
        return true;
    }
}
