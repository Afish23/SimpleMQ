package org.noear.simplemq.client;

import org.noear.socketd.transport.core.Message;

/**
 * 消费者处理
 *
 * @author Afish
 * @data 2025/3/8 09:50
 * @since 1.0
*/
public interface ConsumerHandler {
    /**
     * 处理
     * @param topic
     * @param message
     */
    void handle(String topic, String message);
}
