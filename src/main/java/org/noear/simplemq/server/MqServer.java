package org.noear.simplemq.server;

/**
 * @author Afish
 * @data 2025/3/8 09:50
 * @since 1.0
*/
public interface MqServer {
    MqServer addAccess(String accessKey, String accessSecretKey);
    MqServer start(int port) throws Exception;
    MqServer stop();
}
