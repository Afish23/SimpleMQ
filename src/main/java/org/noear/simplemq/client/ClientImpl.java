package org.noear.simplemq.client;

import org.noear.simplemq.Constants;
import org.noear.socketd.SocketD;
import org.noear.socketd.transport.core.Message;
import org.noear.socketd.transport.core.Session;
import org.noear.socketd.transport.core.entity.StringEntity;
import org.noear.socketd.transport.core.listener.SimpleListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Afish
 * @data 2025/3/8 09:57
 * @since 1.0
 */
public class ClientImpl extends SimpleListener implements Client {

    private String serverUrl;
    private Session session;
    private Map<String, ConsumerHandler> subscribeMap = new HashMap<>();

    public ClientImpl(String serverUrl) throws Exception {
        this.serverUrl = serverUrl;

        this.session = SocketD.createClient(serverUrl)
                .listen(this)
                .open();
    }

    /**
     * 订阅
     * @param topic 主题
     * @param handler 消费处理
     * @throws IOException
     */
    @Override
    public void subscribe(String topic, ConsumerHandler handler) throws IOException {
        subscribeMap.put(topic, handler);
        session.send(Constants.MQ_CMD_SUBSCRIBE, new StringEntity("").meta(Constants.MQ_TOPIC, topic));
    }

    /**
     * 发布
     * @param topic 主题
     * @param message 消息
     * @throws IOException
     */
    @Override
    public void publish(String topic, String message) throws IOException {
        session.send(Constants.MQ_CMD_PUBLISH, new StringEntity(message).meta(Constants.MQ_TOPIC, topic));
    }

    /**
     * 通知回来
     * @param session
     * @param message
     * @throws IOException
     */
    @Override
    public void onMessage(Session session, Message message) throws IOException {
        String topic = message.meta(Constants.MQ_TOPIC);
        ConsumerHandler handler = subscribeMap.get(topic);
        if(handler != null) {
            handler.handle(topic, message.dataAsString());
        }
    }
}
