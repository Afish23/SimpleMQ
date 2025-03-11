package org.noear.simplemq.client;

import java.io.IOException;

/**
 * 消息结构体定义
 *
 * @author Afish
 * @date  2025/3/11 17:47
 * @since 1.0
 */
public interface MqMessage {
    /**
     * 消息键
     * @return
     */
    String getKey();

    /**
     * 消息内容
     * @return
     */
    String getContent();

    /**
     * 已派发次数
     * @return
     */
    int getTimes();

    /**
     * 确认
     * @param isOk
     */
    void affirm(boolean isOk) throws IOException;
}
