package com.example.websocket.utils;

/**
 * socket客户端消息同步回调接口
 */
public interface WebSocketClientSyncCallback {

    /**
     * socket客户端消息回调
     * @param message
     */
    void callback(String message);

}

