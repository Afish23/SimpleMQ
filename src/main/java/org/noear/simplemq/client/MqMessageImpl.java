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
    private final Message from;
    private final String topic;
    private final String content;
    private final int times;

    public MqMessageImpl(MqClientInternal mqClientInternal, Message from) {
        this.clientInternal = mqClientInternal;
        this.from = from;
        this.topic = from.metaOrDefault(MqConstants.MQ_TOPIC, "");
        this.content = from.dataAsString();
        this.times = Integer.parseInt(from.metaOrDefault(MqConstants.MQ_TIMES, "0"));
    }

    public String getTopic() {
        return topic;
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
        clientInternal.affirm(from, isOk);
    }

    @Override
    public String toString() {
        return "MqMessage{" +
                "content='" + content + '\'' +
                ", times=" + times +
                ", topic='" + topic + '\'' +
                '}';
    }
}
