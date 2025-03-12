package org.noear.simplemq.server;

/**
 * @author Afish
 * @date 2025/3/12 13:29
 * @since 1.0
 */
public interface MqMessageQueue {
    /**
     * 获取关联身份
     * @return
     */
    String getIdentity();

    /**
     * 添加消息持有人
     * @param messageHolder
     */
    void add(MqMessageHolder messageHolder);

}
