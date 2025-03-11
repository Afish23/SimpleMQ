package org.noear.simplemq.client;

/**
 * 客户端
 *
 * @author Afish
 * @date 2025/3/8 09:48
 * @since 1.0
 */
public interface MqClient extends MqConsumer, MqProducer {
    /**
     * 是否自动 ack
     * @param auto
     * @return
     */
    MqClient autoAck(boolean auto);
}
