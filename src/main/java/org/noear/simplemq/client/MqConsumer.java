package org.noear.simplemq.client;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * 消费者
 *
 * @author Afish
 * @date 2025/3/8 09:49
 * @since 1.0
 */
public interface MqConsumer {
    /**
     * 订阅
     * @param topic 主题
     * @param subscription 订阅
     */
    CompletableFuture<?> subscribe(String topic, Subscription subscription) throws IOException;
}
