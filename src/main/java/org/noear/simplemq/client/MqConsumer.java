package org.noear.simplemq.client;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * 消费者
 *
 * @author Afish
 * @data 2025/3/8 09:49
 * @since 1.0
 */
public interface MqConsumer {
    /**
     * 订阅
     * @param topic 主题
     * @param handler 消费处理
     */
    CompletableFuture<?> subscribe(String topic, MqConsumerHandler handler) throws IOException;
}
