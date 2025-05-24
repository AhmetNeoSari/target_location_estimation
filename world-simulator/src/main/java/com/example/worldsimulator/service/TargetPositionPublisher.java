package com.example.worldsimulator.service;

import com.example.basedomain.dto.Point;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class TargetPositionPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(TargetPositionPublisher.class);
    private NewTopic topic;

    private KafkaTemplate<String, Point> kafkaTemplate;

    public TargetPositionPublisher(NewTopic topic, KafkaTemplate<String, Point> kafkaTemplate)
    {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(Point point)
    {
        Message<Point> message = MessageBuilder
                .withPayload(point)
                .setHeader(KafkaHeaders.TOPIC, topic.name())
                .build();

        kafkaTemplate.send(message);
    }


}
