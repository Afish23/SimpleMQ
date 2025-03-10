package demo;

import org.noear.simplemq.client.MqClient;
import org.noear.simplemq.client.MqClientImpl;


/**
 * @author Afish
 * @data 2025/3/8 10:35
 * @since 1.0
 */
public class ClientDemo2 {
    public static void main(String[] args) throws Exception {
        //客户端
        MqClient mqClient = new MqClientImpl("SimpleMQ://127.0.0.1:9393?accessKey=root&accessSecretKey=123456");
        //订阅
        mqClient.subscribe("demo",((topic, message) -> {
            System.out.println("ClientDemo2::" + topic + " - " + message);
        }));
    }
}
