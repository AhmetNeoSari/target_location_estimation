package com.example.worldsimulator.service;

import com.example.worldsimulator.data.Point;
import com.example.worldsimulator.data.Target;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

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
        LOGGER.info("Target message publishing");
        Message<Point> message = MessageBuilder
                .withPayload(point)
                .setHeader(KafkaHeaders.TOPIC, topic.name())
                .build();
//        String topic_name = topic.name();
//        LOGGER.info("topic name: {}",topic_name);
//        LOGGER.info("point: {}",point.toString());
//        kafkaTemplate.send(topic_name, point);
        kafkaTemplate.send(message);
//        kafkaTemplate.
        LOGGER.info("Target message published");
    }


}
