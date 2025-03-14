package benchmark;

import org.noear.simplemq.client.MqClient;
import org.noear.simplemq.client.MqClientImpl;
import org.noear.simplemq.client.Subscription;
import org.noear.simplemq.server.MqServer;
import org.noear.simplemq.server.MqServerImpl;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author Afish
 * @date 2025/3/14 16:15
 */
public class BenchmarkTest {
    public static void main(String[] args) throws Exception {
        //服务端
        MqServer server = new MqServerImpl()
                .addAccess("root", "123456")
                .start(9393);

        Thread.sleep(1000);
        int count = 10_0000 + 10000;
        CountDownLatch countDownLatch = new CountDownLatch(count);

        //客户端
        MqClient mqClient = new MqClientImpl("SimpleMQ://127.0.0.1:9393?accessKey=root&accessSecretKey=123456");

        //订阅
        mqClient.subscribe("demo", new Subscription("a", ((topic, message) -> {
            //System.out.println("ClientDemo1::" + topic + " - " + message);
            countDownLatch.countDown();
        })));

        //预热
        for (int i = 0; i < 10000; i++) {
            mqClient.publish("demo", "hi" + i);
        }

        //发布测试
        long start_time = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            mqClient.publish("demo","hi-" + i);
        }
        long sendTime = System.currentTimeMillis() - start_time;

        System.out.println("sendTime：" + sendTime);
        countDownLatch.await(6, TimeUnit.SECONDS);

        long distributeTime = System.currentTimeMillis() - start_time;

        System.out.println("distributeTime：" + distributeTime + ", count: " + (count - countDownLatch.getCount()));
    }
}
