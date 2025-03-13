package org.noear.simplemq.server;

import org.noear.simplemq.MqConstants;
import org.noear.socketd.transport.core.Session;
import org.noear.socketd.utils.RunUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Afish
 * @date 2025/3/12 13:30
 * @since 1.0
 */
public class MqMessageQueueImpl implements MqMessageQueue {
    private Queue<MqMessageHolder> queue = new LinkedList<>();
    private final String identity;
    private final Set<Session> sessionSet;

    public MqMessageQueueImpl(String identity, Set<Session> sessionSet) {
        this.identity = identity;
        this.sessionSet = sessionSet;
    }

    @Override
    public String getIdentity() {
        return identity;
    }

    @Override
    public void add(MqMessageHolder messageHolder) {
        queue.add(messageHolder);
        distribute();
    }

    public void addDelayed(MqMessageHolder messageHolder) {
        if(messageHolder.deferredFuture != null) {
            messageHolder.deferredFuture.cancel(true);
        }
        messageHolder.deferredFuture = RunUtils.delay(()->{
            add(messageHolder);
        }, messageHolder.getNextTime() - System.currentTimeMillis());
    }

    /**
     * 派发
     */
    private void distribute() {
        //找到此身份的其中一个会话（如果是ip就一个；如果是集群名则任选一个）
        List<Session> sessions = sessionSet.parallelStream()
                .filter(s -> s.attrMap().containsKey(identity))
                .collect(Collectors.toList());

        if (!sessions.isEmpty()) {
            MqMessageHolder messageHolder;
            while (true) {
                messageHolder = queue.poll();
                if (messageHolder == null) {
                    break;
                }

                if (!MqNextTime.allowDistribute(messageHolder)) {
                    //进入延后队列
                    addDelayed(messageHolder);
                    continue;
                }

                try {
                    distributeDo(messageHolder, sessions);
                } catch (Exception e) {
                    //进入延后队列
                    addDelayed(messageHolder.deferred());
                }
            }
        }
    }

    /**
     * 派发执行
     *
     * @param messageHolder
     * @param sessions
     * @throws IOException
     */
    private void distributeDo(MqMessageHolder messageHolder, List<Session> sessions) throws IOException {
        //随机取一个会话
        int idx = 0;
        if (sessions.size() > 1) {
            idx = new Random().nextInt(sessions.size());
        }
        Session s1 = sessions.get(idx);

        //todo:这里可能会有线程同步问题
        messageHolder.getContent().meta(MqConstants.MQ_TIMES, String.valueOf(messageHolder.getTimes()));

        //给会话发消息
        s1.sendAndSubscribe(MqConstants.MQ_CMD_DISTRIBUTE, messageHolder.getContent(), m -> {
            int ack = Integer.parseInt(m.metaOrDefault(MqConstants.MQ_ACK, "0"));
            if (ack == 0) {
                //进入延后队列，之后再试 //todo:如果因为网络原因，没有回调怎么办？
                addDelayed(messageHolder.deferred());
            }
        });
    }

}
