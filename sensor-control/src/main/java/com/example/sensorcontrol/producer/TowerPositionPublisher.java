package com.example.sensorcontrol.producer;

import com.example.basedomain.dto.Point;
import com.example.basedomain.config.Publisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TowerPositionPublisher implements Publisher<Point> {

    private final KafkaTemplate<String, Point> kafkaTemplate;

    public TowerPositionPublisher(KafkaTemplate<String, Point> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Value("${spring.kafka.topic.produce.position.name}")
    private String outputTopic;

    @Override
    public void publish(Point data) {
        kafkaTemplate.send(outputTopic, data);
    }
}
