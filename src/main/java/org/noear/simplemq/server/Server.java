package org.noear.simplemq.server;

/**
 * @author Afish
 * @data 2025/3/8 09:50
 * @since 1.0
*/
public interface Server {
    void start(int port);
    void stop();
}
