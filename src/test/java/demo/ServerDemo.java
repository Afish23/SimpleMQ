package demo;

import org.noear.simplemq.server.MqServer;
import org.noear.simplemq.server.MqServerImpl;

/**
 * @author Afish
 * @data 2025/3/9 09:54
 * @since 1.0
 */
public class ServerDemo {
    public static void main(String[] args) throws Exception {
        MqServer server = new MqServerImpl()
                .addAccess("root", "123456")
                .start(9393);
    }
}
