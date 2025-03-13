package org.noear.simplemq.client;

import org.noear.simplemq.MqConstants;
import org.noear.socketd.SocketD;
import org.noear.socketd.transport.core.Entity;
import org.noear.socketd.transport.core.Message;
import org.noear.socketd.transport.core.Session;
import org.noear.socketd.transport.core.entity.StringEntity;
import org.noear.socketd.transport.core.listener.BuilderListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;


/**
 * @author Afish
 * @date 2025/3/8 09:57
 * @since 1.0
 */
public class MqClientImpl extends BuilderListener implements MqClientInternal {
    private static final Logger log = LoggerFactory.getLogger(MqClientImpl.class);

    private String serverUrl;
    private Session session;
    private Map<String, MqConsumerHandler> subscribeMap = new HashMap<>();
    private boolean autoAck = true;

    public MqClientImpl(String serverUrl) throws Exception {
        this.serverUrl = serverUrl.replace("SimpleMQ://", "sd:tcp://");

        this.session = SocketD.createClient(this.serverUrl)
                .listen(this)
                .open();

        //接受派发指令
        on(MqConstants.MQ_CMD_DISTRIBUTE, (s, m) -> {
            String topic = m.meta(MqConstants.MQ_TOPIC);

            try {
                onDistribute(topic, m);
                //是否自动ACK
                if (autoAck) {
                    affirm(m, true);
                }
            } catch (IOException e) {
                affirm(m, false);
            }
        });
    }

    /**
     * 订阅主题
     *
     * @param topic        主题
     * @param subscription 消费处理
     * @throws IOException
     */
    @Override
    public CompletableFuture<?> subscribe(String topic, Subscription subscription) throws IOException {
        //支持Qos1
        subscribeMap.put(topic, subscription.getHandler());

        Entity entity = new StringEntity("")
                .meta(MqConstants.MQ_TOPIC, topic)
                .meta(MqConstants.MQ_IDENTITY, subscription.getIdentity());

        CompletableFuture<?> future = new CompletableFuture<>();
        session.sendAndSubscribe(MqConstants.MQ_CMD_SUBSCRIBE, entity, (r) -> {
            future.complete(null);
        });
        return future;
    }

    /**
     * 发布消息
     *
     * @param topic   主题
     * @param message 消息
     * @throws IOException
     */
    @Override
    public CompletableFuture<?> publish(String topic, String message) throws IOException {
        //支持Qos1
        CompletableFuture<?> future = new CompletableFuture<>();
        session.sendAndSubscribe(MqConstants.MQ_CMD_PUBLISH, new StringEntity(message).meta(MqConstants.MQ_TOPIC, topic), (r) -> {
            future.complete(null);
        });
        return future;
    }

    @Override
    public MqClient autoAck(boolean auto) {
        this.autoAck = auto;
        return this;
    }

    /**
     * 消费确认
     * @param message
     * @param isOk
     * @throws IOException
     */
    @Override
    public void affirm(Message message, boolean isOk) throws IOException {
        session.replyEnd(message, new StringEntity("")
                .meta(MqConstants.MQ_ACK, isOk ? "1" : "0"));
    }

    /**
     * 当派发时
     *
     * @param topic
     * @param message
     * @throws IOException
     */
    private void onDistribute(String topic, Message message) throws IOException {
        MqConsumerHandler handler = subscribeMap.get(topic);
        if (handler != null) {
            handler.handle(topic, new MqMessageImpl(this, message));
        }
    }

    /**
     * 当失败时
     * @param session
     * @return
     */
    @Override
    public void onError(Session session, Throwable error) {
        super.onError(session, error);

        if (log.isWarnEnabled()) {
            log.warn("{}", error);
        }
    }

}
