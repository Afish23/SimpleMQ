package org.noear.simplemq.client;

import java.io.IOException;

/**
 * 消费者
 *
 * @author Afish
 * @data 2025/3/8 09:49
 * @since 1.0
 */
public interface Comsumer {
    /**
     * 订阅
     * @param topic 主题
     * @param handler 消费处理
     */
    void subscribe(String topic, ConsumerHandler handler) throws IOException;
}
