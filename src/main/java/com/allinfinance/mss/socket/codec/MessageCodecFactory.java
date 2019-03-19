package com.allinfinance.mss.socket.codec;

import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageEncoder;

public class MessageCodecFactory extends DemuxingProtocolCodecFactory {
    private MessageDecoder decoder;
    private MessageEncoder encoder;

    public MessageCodecFactory(MessageDecoder decoder,
                               MessageEncoder encoder) {
        this.decoder = decoder;
        this.encoder = encoder;
        addMessageDecoder(this.decoder);
        addMessageEncoder(Object.class, this.encoder);
    }
}
