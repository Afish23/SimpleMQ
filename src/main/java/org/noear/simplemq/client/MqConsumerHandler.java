package org.noear.simplemq.client;

import java.io.IOException;

/**
 * 消费者处理
 *
 * @author Afish
 * @date 2025/3/8 09:50
 * @since 1.0
*/
public interface MqConsumerHandler {
    /**
     * 处理
     * @param topic
     * @param message
     */
    void handle(String topic, MqMessage message) throws IOException;
}
