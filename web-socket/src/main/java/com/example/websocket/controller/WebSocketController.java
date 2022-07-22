package com.example.websocket.controller;

import com.example.websocket.utils.CustomizedWebSocketClient;
import com.example.websocket.utils.WebSocketClientSyncCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@RequestMapping(value = "/socket")
public class WebSocketController {

    @Autowired
    public CustomizedWebSocketClient socketClient;

    private String callbackMessage;
    @ResponseBody
    @PostMapping(value = "/clientCallback")
    public String testClientCallback(HttpServletRequest request)  {

        try {
            socketClient.send("test message",new WebSocketClientSyncCallback(){
                @Override
                public void callback(String message) {
                    callbackMessage = message;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  callbackMessage;
    }

}
