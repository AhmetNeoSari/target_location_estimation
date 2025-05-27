package com.example.sensorcontrol.producer;

import com.example.basedomain.config.Publisher;
import com.example.basedomain.dto.BearingPosition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TargetBearingPositionPublisher implements Publisher<BearingPosition> {
    private final KafkaTemplate<String, BearingPosition> kafkaTemplate;

    @Value("${spring.kafka.topic.produce.position.info.name}")
    private String outputTopic;

    public TargetBearingPositionPublisher(KafkaTemplate<String, BearingPosition> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(BearingPosition position) {
        kafkaTemplate.send(outputTopic, position);
    }

}
