package org.noear.simplemq.client;

import java.io.IOException;

/**
 * 生产者
 *
 * @author Afish
 * @data 2025/3/8 09:51
 * @since 1.0
*/
public interface Producer {
    /**
     * 发送
     * @param topic 主题
     * @param message 消息
     */
    void publish(String topic, String message) throws IOException;
}
