package org.noear.simplemq.server;

/**
 * @author Afish
 * @data 2025/3/8 09:50
 * @since 1.0
*/
public interface MqServer {
    void start(int port) throws Exception;
    void stop();
}
