package com.example.worldsimulator.service;

import com.example.worldsimulator.data.Point;
import com.example.worldsimulator.data.Target;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TargetPositionPublisher {

    private NewTopic topic;
    private Target target;

    private KafkaTemplate<String, Point> kafkaTemplate;

    public TargetPositionPublisher(){

    }

    public sendMessage(NewTopic topic, KafkaTemplate<String, Point>)
    {
        kafkaTemplate.send(topic.name(), target.getPoint()).
    }

}
