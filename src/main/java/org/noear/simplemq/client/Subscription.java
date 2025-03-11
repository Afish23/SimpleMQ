package org.noear.simplemq.client;

/**
 *订阅者
 *
 * @author Afish
 * @date 2025/3/10 16:31
 * @since 1.0
 */
public class Subscription {
    private String identity;
    private MqConsumerHandler handler;

    public String getIdentity() {
        return identity;
    }

    public MqConsumerHandler getHandler() {
        return handler;
    }

    public Subscription(String identity, MqConsumerHandler handler) {
        this.identity = identity;
        this.handler = handler;
    }
}
