package com.healthcarewebapp.service;


import com.google.gson.Gson;
import com.healthcarewebapp.model.HealthRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.UUID;


import static com.healthcarewebapp.service.Websocket.wbSockets;

public class ConsumerKafka extends Thread{

    private KafkaConsumer<String, String > kafkaConsumer;
    private String kafkaTopic;

    public ConsumerKafka(String topic){
        this.kafkaTopic = topic;
    }

    @Override
    public void run(){

        String unique_id = UUID.randomUUID().toString();
        String group_instance_id = "user-group-" + unique_id;

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:29092");
        props.put("group.id", "user-group");
        props.put("group.instance.id", group_instance_id);
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("max.poll.interval.ms", 1000);

        kafkaConsumer = new KafkaConsumer<>(props);
        kafkaConsumer.subscribe(Arrays.asList(kafkaTopic));


        while(!wbSockets.isEmpty()){
            ConsumerRecords<String, String> consumerRecord = kafkaConsumer.poll(100);
            for (ConsumerRecord<String, String> record: consumerRecord){

                System.out.println("(ConsumerKafka.java) consumerRecord: " + record);

                Gson gson = new Gson();
                HealthRecord healthRecord = gson.fromJson(record.value(),HealthRecord.class);

                for (Websocket websocket: wbSockets) {
                    synchronized (websocket) {
                        try {
                            System.out.println("(ConsumerKafka.java) pathParameter using session inside loop: " + websocket.getSessionPathParam());
                            if (healthRecord.getCitizenid().toString().equals(websocket.getSessionPathParam())) {
                                websocket.sendMessage(gson.toJson(healthRecord));
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
        close();
    }


    public void close(){
        try{
            kafkaConsumer.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
