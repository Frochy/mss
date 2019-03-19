package com.allinfinance.mss.socket.codec;

import org.apache.commons.lang3.StringUtils;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NfMessageEncoder implements MessageEncoder<String> {
    private static final Logger logger = LoggerFactory.getLogger(NfMessageEncoder.class);
    //报文长度字段的长度
    private int msgLengthSize;
    //报文编码格式
    private String msgEncode;

    public NfMessageEncoder(int msgLengthSize, String msgEncode) {
        this.msgLengthSize = msgLengthSize;
        this.msgEncode = msgEncode;
    }

    public void encode(IoSession session, String message, ProtocolEncoderOutput out) throws Exception {
        if (StringUtils.isEmpty(message)) {
            if (this.getMsgLengthSize() != 0) {
                IoBuffer buf = IoBuffer.allocate(this.getMsgLengthSize());
                buf.put(String.format("%0" + this.getMsgLengthSize() + "d", 0).getBytes());
                buf.flip();
                out.write(buf);
            }
            return;
        }
        logger.debug("编码前消息:length=" + message.length() + ", content[" + message + "]");
        byte[] body = message.getBytes(this.getMsgEncode());
        int body_len;
        body_len = body.length;
        IoBuffer buf = IoBuffer.allocate(body_len + this.getMsgLengthSize()).setAutoExpand(true);
        if (this.getMsgLengthSize() != 0)
            buf.put(String.format("%0" + this.getMsgLengthSize() + "d", body_len).getBytes());
        buf.put(body);
        buf.flip();
        logger.debug("编码完成:length=" + buf.limit() + ", content[" + buf.toString() + "]");
        session.write(buf);
    }

    public int getMsgLengthSize() {
        return msgLengthSize;
    }

    public void setMsgLengthSize(int msgLengthSize) {
        this.msgLengthSize = msgLengthSize;
    }

    public String getMsgEncode() {
        return msgEncode;
    }

    public void setMsgEncode(String msgEncode) {
        this.msgEncode = msgEncode;
    }
}
