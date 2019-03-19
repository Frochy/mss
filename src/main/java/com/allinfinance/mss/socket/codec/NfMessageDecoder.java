package com.allinfinance.mss.socket.codec;

import com.allinfinance.mss.util.ConvertUtil;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class NfMessageDecoder implements MessageDecoder {
    private static final Logger logger = LoggerFactory.getLogger(NfMessageDecoder.class);

    //报文长度字段的长度
    private int msgLengthSize;
    //报文编码格式
    private String msgEncode;

    public NfMessageDecoder(int msgLengthSize, String msgEncode) {
        this.msgLengthSize = msgLengthSize;
        this.msgEncode = msgEncode;
    }

    @Override
    public MessageDecoderResult decodable(IoSession session, IoBuffer in) {
        if (this.getMsgLengthSize() != 0) {
            if (in.remaining() < this.getMsgLengthSize()) {
                logger.debug("报文长度未到齐:" + in.remaining());
                return MessageDecoderResult.NEED_DATA;
            }

            byte[] temp = new byte[this.getMsgLengthSize()];
            in.get(temp, 0, this.getMsgLengthSize());
            int len = 0;
            try {
                len = Integer.parseInt(new String(temp));
            } catch (NumberFormatException ex) {
                // 长度头异常时会引发粘包问题，直接丢弃当前连接，等待新建连接
                logger.debug("报文长度含有非数字内容,关闭连接:" + Arrays.toString(temp));
                session.closeNow();
            }

            if (in.remaining() < len) {
                logger.error("报文数据未到齐:" + in.remaining() + ":" + len);
                return MessageDecoderResult.NEED_DATA;
            }
        }

        return MessageDecoderResult.OK;
    }

    @Override
    public MessageDecoderResult decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
        int len = 0;
        if (this.getMsgLengthSize() != 0) {
            byte[] bLen = new byte[this.getMsgLengthSize()];
            in.get(bLen, 0, this.getMsgLengthSize());
            len = Integer.parseInt(new String(bLen));
        }

        logger.debug("解码消息:length=" + len + ", content[" + in.toString() + "]");
        if (len == 0 && this.getMsgLengthSize() != 0) {
            if (this.getMsgLengthSize() != 0) {
                out.write(String.format("%0" + this.getMsgLengthSize() + "d", 0));
            }
            return MessageDecoderResult.OK;
        }

        int blen = in.remaining();
        byte[] bBody = new byte[blen];
        in.get(bBody);

        StringBuilder buf = new StringBuilder();
        buf.append(ConvertUtil.getFixedBytesString(bBody, 0, -1, this.getMsgEncode()));

        logger.info("解码完成:length=" + buf.length() + ", content[" + buf.toString() + "]");
        out.write(buf.toString());

        return MessageDecoderResult.OK;
    }

    @Override
    public void finishDecode(IoSession session, ProtocolDecoderOutput out) throws Exception {
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
