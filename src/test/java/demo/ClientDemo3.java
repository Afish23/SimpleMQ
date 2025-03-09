package demo;

import org.noear.simplemq.client.MqClient;
import org.noear.simplemq.client.MqClientImpl;


/**
 * @author Afish
 * @data 2025/3/8 10:35
 * @since 1.0
 */
public class ClientDemo3 {
    public static void main(String[] args) throws Exception {
        //客户端
        MqClient mqClient = new MqClientImpl("sd:tcp://127.0.0.1:9393");
        //订阅
        mqClient.subscribe("domo2",((topic, message) -> {
            System.out.println("ClientDemo3::" + topic + " - " + message);
        }));
    }
}
