package org.noear.simplemq.server;

import org.noear.socketd.transport.core.Message;

/**
 * 消息持有人
 *
 * @author Afish
 * @date 2025/3/12 13:25
 * @since 1.0
 */
public class MqMessageHolder {
    private Message message;
    private long nextTime;
    private int times;

    public MqMessageHolder(Message message){
        this.message = message;
    }

    /**
     * 获取消息
     * @return
     */
    public Message getMessage(){
        return message;
    }

    /**
     * 获取下次派发时间（单位：毫秒）
     * @return
     */
    public long getNextTime(){
        return nextTime;
    }

    /**
     * 获取派发次数
     * @return
     */
    public int getTimes(){
        return times;
    }

    /**
     * 延后（生成下次派发时间）
     */
    public MqMessageHolder deferred(){
        times++;
        nextTime = MqNextTime.getNextTime(this);
        return this;
    }
}
