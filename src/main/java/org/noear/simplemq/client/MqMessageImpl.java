package org.noear.simplemq.client;


import org.noear.simplemq.MqConstants;
import org.noear.socketd.transport.core.Message;

import java.io.IOException;

/**
 * 消息结构体实现
 *
 * @author Afish
 * @date 2025/3/11 17:55
 * @since 1.0
 */
public class MqMessageImpl implements MqMessage {
    private final MqClientInternal clientInternal;
    private final Message message;
    private final String content;
    private final int times;

    public MqMessageImpl(MqClientInternal mqClientInternal, Message message) {
        this.clientInternal = mqClientInternal;
        this.message = message;
        this.content =message.dataAsString();
        this.times = Integer.parseInt(message.metaOrDefault(MqConstants.MQ_TIMES, "0"));
    }

    @Override
    public String getKey() {
        return message.sid();
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public int getTimes() {
        return times;
    }

    @Override
    public void affirm(boolean isOk) throws IOException {
        clientInternal.affirm(message, isOk);
    }

    @Override
    public String toString() {
        return getContent();
    }
}
