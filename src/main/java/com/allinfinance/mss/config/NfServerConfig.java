package com.allinfinance.mss.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "mss.socket.server")
public class NfServerConfig {
    private int port;
    private int timeout;
    private int processorCount;
    private int bufferSize;
    private int msgLengthSize;
    private String encode;
    private String orgIds;
    private String serviceIds;

    @Override
    public String toString() {
        return "NfServerConfig{" +
                "port=" + port +
                ", timeout=" + timeout +
                ", processorCount=" + processorCount +
                ", bufferSize=" + bufferSize +
                ", msgLengthSize=" + msgLengthSize +
                ", encode='" + encode + '\'' +
                ", orgIds='" + orgIds + '\'' +
                ", serviceIds='" + serviceIds + '\'' +
                '}';
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getProcessorCount() {
        return processorCount;
    }

    public void setProcessorCount(int processorCount) {
        this.processorCount = processorCount;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public int getMsgLengthSize() {
        return msgLengthSize;
    }

    public void setMsgLengthSize(int msgLengthSize) {
        this.msgLengthSize = msgLengthSize;
    }

    public String getEncode() {
        return encode;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }

    public String getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(String orgIds) {
        this.orgIds = orgIds;
    }

    public String getServiceIds() {
        return serviceIds;
    }

    public void setServiceIds(String serviceIds) {
        this.serviceIds = serviceIds;
    }
}
