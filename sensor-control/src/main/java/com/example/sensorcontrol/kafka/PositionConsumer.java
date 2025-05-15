package com.example.sensorcontrol.kafka;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PositionConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(PositionConsumer.class);



    @KafkaListener(
            topics = "${spring.kafka.topic.consume.name}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(Point point)
    {
        try {
            LOGGER.info("listener triggered");

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
//        LOGGER.info("Received point: {}", point.toString());
    }

}
