package com.example.websocket.utils;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import java.net.URI;
import java.nio.channels.NotYetConnectedException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 自定义WebSocket客户端
 */
/**
 * 自定义WebSocket客户端
 * @author machenike
 */
public class CustomizedWebSocketClient extends WebSocketClient {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(CustomizedWebSocketClient.class);

    /**
     * 消息回调接口
     */
    private WebSocketClientSyncCallback callback = null;

    /**
     * 线程安全的Boolean -是否受到消息
     */
    public AtomicBoolean hasMessage = new AtomicBoolean(false);

    /**
     * 线程安全的Boolean -是否已经连接
     */
    private AtomicBoolean hasConnection = new AtomicBoolean(false);

    /**
     * 构造方法
     *
     * @param serverUri
     */
    public CustomizedWebSocketClient(URI serverUri) {
        super(serverUri);
        logger.info("CustomizeWebSocketClient init:" + serverUri.toString());
    }

    /**
     * 打开连接时
     *
     * @param serverHandshake
     */
    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        logger.info("CustomizeWebSocketClient onOpen");
        hasConnection.set(true);
    }

    /**
     * 收到消息时
     *
     * @param s
     */
    @Override
    public void onMessage(String s) {
        hasMessage.set(true);
        if(callback !=null) {
            callback.callback(s);
        }
        logger.info("CustomizeWebSocketClient onMessage:" + s);
    }

    /**
     * 当连接关闭时
     *
     * @param i
     * @param s
     * @param b
     */
    @Override
    public void onClose(int i, String s, boolean b) {
        this.hasConnection.set(false);
        this.hasMessage.set(false);
        logger.info("CustomizeWebSocketClient onClose:" + s);
    }

    /**
     * 发生error时
     *
     * @param e
     */
    @Override
    public void onError(Exception e) {
        logger.info("CustomizeWebSocketClient onError:" + e);
    }


    /**
     * 带有回调的消息发送接口
     * @param text
     * @param callback
     * @throws NotYetConnectedException
     */
    public void send(String text, WebSocketClientSyncCallback callback) throws NotYetConnectedException {
        logger.info("CustomizeWebSocketClient send:" + text);
        hasMessage.set(false);
        //设定回调接口
        this.callback = callback;
        super.send(text);
        //计算等待；10s返回消息 超过10s直接退出
        for (int count = 0; ; ) {
            logger.debug("socketClient wait:"+count+" second, hasMessage："+hasMessage);
            //判断是否收到消息||socket返回数据超时
            if (hasMessage.get()||count>10) {
                break;
            } else if (count <=10) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count++;
            }
        }
    }

    @Override
    public void connect() {
        logger.info("CustomizeWebSocketClient connect");
        super.connect();
    }


    @Override
    public void reconnect() {
        logger.info("CustomizeWebSocketClient reconnect");
        super.reconnect();
    }

    /**
     * 定时判断连接状态:尝试重连 1分钟
     */
    @Scheduled(cron = "0 0/1 * * * ? ")
    public void autoConnect(){
        logger.info("CustomizeWebSocketClient autoConnect: [hasConnection:"+hasConnection+"]");
        if(!hasConnection.get()){
            this.reconnect();
        }
    }
}

