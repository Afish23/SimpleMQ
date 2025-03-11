package org.noear.simplemq.client;

import org.noear.socketd.transport.core.Message;

import java.io.IOException;

/**
 * 客户端，内部扩展接口
 *
 * @author Afish
 * @date 2025/3/11 18:01
 * @since 1.0
 */
public interface MqClientInternal extends MqClient {
    /**
     * 确认
     */
    void affirm(Message message, boolean isOk) throws IOException;
}
