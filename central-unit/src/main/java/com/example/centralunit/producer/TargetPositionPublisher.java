package com.example.centralunit.producer;

import com.example.basedomain.config.Publisher;
import com.example.basedomain.dto.Point;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TargetPositionPublisher implements Publisher<Point> {

    private final KafkaTemplate<String, Point> kafkaTemplate;

    @Value("${spring.kafka.topic.produce.position.name}")
    private String outputTopic;

    public TargetPositionPublisher(KafkaTemplate<String, Point> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(Point position) {
        kafkaTemplate.send(outputTopic, position);
    }

}
