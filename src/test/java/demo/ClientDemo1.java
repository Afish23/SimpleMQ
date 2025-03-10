package demo;

import org.noear.simplemq.client.MqClientImpl;
import org.noear.simplemq.client.MqClient;
import org.noear.simplemq.client.Subscription;


/**
 * @author Afish
 * @data 2025/3/8 10:35
 * @since 1.0
 */
public class ClientDemo1 {
    public static void main(String[] args) throws Exception {
        //客户端
        MqClient mqClient = new MqClientImpl("SimpleMQ://127.0.0.1:9393?accessKey=root&accessSecretKey=123456");
        //订阅
        mqClient.subscribe("demo", new Subscription("a", ((topic, message) -> {
            System.out.println("ClientDemo1::" + topic + " - " + message);
        })));

        //发布
        mqClient.publish("demo","hi");
        for (int i = 0; i < 10; i++) {
            Thread.sleep(100);
            mqClient.publish("demo","hi-" + i);
            mqClient.publish("demo2","hi-" + i);
        }
    }
}
