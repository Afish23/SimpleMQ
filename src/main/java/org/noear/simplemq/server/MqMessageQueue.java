package org.noear.simplemq.server;

import org.noear.socketd.transport.core.Session;

/**
 * @author Afish
 * @date 2025/3/12 13:29
 * @since 1.0
 */
public interface MqMessageQueue {

    /**
     * 添加订阅者
     * @param session
     */
    void addSubscriber(Session session);

    /**
     * 删除订阅者
     * @param session
     */
    void removeSubscriber(Session session);

    /**
     * 获取关联身份
     * @return
     */
    String getIdentity();

    /**
     * 推入消息持有人
     * @param messageHolder
     */
    void push(MqMessageHolder messageHolder);

}
