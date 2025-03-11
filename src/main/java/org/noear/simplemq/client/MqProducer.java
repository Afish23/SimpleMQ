package org.noear.simplemq.client;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * 生产者
 *
 * @author Afish
 * @date 2025/3/8 09:51
 * @since 1.0
*/
public interface MqProducer {
    /**
     * 发送
     * @param topic 主题
     * @param message 消息
     */
    CompletableFuture<?> publish(String topic, String message) throws IOException;
}
