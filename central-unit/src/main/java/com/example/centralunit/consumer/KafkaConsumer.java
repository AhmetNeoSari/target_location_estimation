package com.example.centralunit.consumer;

import com.example.basedomain.dto.BearingPosition;
import com.example.basedomain.dto.Point;
import com.example.centralunit.service.CentralUnitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);
    private final CentralUnitService centralUnitService;

    public KafkaConsumer(CentralUnitService service) {
        centralUnitService = service;
    }

    @KafkaListener( groupId = "${spring.kafka.consumer.group-id.CentralUnit.name}",
            topicPartitions ={
            @TopicPartition(topic = "${spring.kafka.topic.consume.bearingInfo.name}",
                    partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "0", seekPosition = "END"))
            })
    public void consumeBearingPosition(BearingPosition bearing) {
        centralUnitService.updateWithBearing(bearing);
    }


    @KafkaListener( groupId = "${spring.kafka.consumer.group-id.CentralUnit.name}",
            topicPartitions ={
                    @TopicPartition(topic = "${spring.kafka.topic.consume.towerPosition.name}",
                            partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "0", seekPosition = "END"))
            })
    public void consumeTowerPosition(Point point) {
        centralUnitService.updateWithPoint(point);
    }

}


