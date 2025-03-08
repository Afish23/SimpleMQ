package demo;

import org.noear.simplemq.client.ClientImpl;
import org.noear.simplemq.client.Client;


/**
 * @author Afish
 * @data 2025/3/8 10:35
 * @since 1.0
 */
public class DemoTest {
    public static void main(String[] args) throws Exception {
        //客户端
        Client client = new ClientImpl("sd:tpc://127.0.0.1:9393");
        //订阅
        client.subscribe("demo",((topic, message) -> {
            System.out.println(topic + ":" + message);
        }));

        //发布
        client.publish("demo","hi");
    }
}
