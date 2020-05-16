package com.stephen.learning.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stephen.learning.model.MessageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author jack
 * @description
 * @date 2020/5/16 11:49
 */
@Component
@ServerEndpoint("/webSocket")
@Slf4j
public class MyWebSocket {
    //初始化当前在线人数
    private static int onlineCount = 0;

    //保存每个客户端对应的MyWebsocket对象
    private static CopyOnWriteArraySet<MyWebSocket> webSockets = new CopyOnWriteArraySet<MyWebSocket>();

    //保存客户端的连接
    private Session session;

    private MessageVO messageVO = new MessageVO();

    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        webSockets.add(this);
        messageVO.setType(1);
        messageVO.setUserNum(webSockets.size());
        messageVO.setMessage("有新的连接");
        //发送消息
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonText = mapper.writeValueAsString(messageVO);
            this.sendMessage(jsonText);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        log.info("【websocket消息】有新的连接, 总数:{}", webSockets.size());
    }

    @OnClose
    public void onClose(){
        webSockets.remove(this);

        messageVO.setMessage("有用户离开");
        messageVO.setType(2);
        messageVO.setUserNum(webSockets.size());

        //发送消息
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonText = mapper.writeValueAsString(messageVO);
            this.sendMessage(jsonText);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        log.info("【websocket消息】有用户断开连接, 总数:{}", webSockets.size());
    }

    @OnMessage
    public void onMessage(String message){
        messageVO.setMessage(message);
        messageVO.setType(3);
        messageVO.setUserNum(webSockets.size());
        //发送消息
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonText = mapper.writeValueAsString(messageVO);
            this.sendMessage(jsonText);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        log.info("【websocket消息】有用户正在发送消息, 总数:{}", webSockets.size());
    }

    @OnError
    public void onError(Session session, Throwable e) {
        log.info("【websocket消息】出现异常：{}"+e.getMessage());
    }

    public void sendMessage(String message) {
        for (MyWebSocket webSocket : webSockets) {
            log.info("【websocket消息】广播消息, message={}", message);
            try {
                webSocket.session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
