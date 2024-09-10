package com.healthcarewebapp.service;


import jakarta.websocket.OnClose;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ConcurrentModificationException;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/websocket/{client_id}")
@Component
public class Websocket {

    private Session session;
    public static CopyOnWriteArraySet<Websocket> wbSockets = new CopyOnWriteArraySet<Websocket>();
    private String kafka_topic_realTime = "healthRecord-topic";
    private String kafka_topic_average = "healthRecordAverage-topic";

    @OnOpen
    public void onOpen(Session session) throws IOException, ConcurrentModificationException {
        this.session = session;
        wbSockets.add(this);
        System.out.println("websocket connection + 1");

        ConsumerKafka consumerKafka_1 = new ConsumerKafka(kafka_topic_realTime);
        ConsumerKafka consumerKafka_2 = new ConsumerKafka(kafka_topic_average);
        consumerKafka_1.start();
        consumerKafka_2.start();
    }

    @OnClose
    public void onClose(){
        wbSockets.remove(this);
    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public String getSessionPathParam(){
        String pathParam = session.getPathParameters().values().toString().replace("[", "").replace("]", "");
        return pathParam;
    }

}

