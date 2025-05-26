package com.example.sensorcontrol.kafka;

import com.example.basedomain.dto.BearingPosition;
import com.example.sensorcontrol.calculation.CalculateBearing;
import com.example.sensorcontrol.data.SensorTower;
import com.example.sensorcontrol.producer.TargetBearingPositionPublisher;
import com.example.sensorcontrol.producer.TowerPositionPublisher;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;
import com.example.basedomain.dto.Point;

@Service
public class PositionConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(PositionConsumer.class);

    private final TowerPositionPublisher towerPositionPublisher;
    private final TargetBearingPositionPublisher targetBearingPositionPublisher;

    private SensorTower tower1 ;
    private SensorTower tower2 ;

    @Value("${CentralUnit.name}")
    private String centralName;

    @Autowired
    public PositionConsumer(@Value("${SensorTower1.Position.X}") final int tower1PosX,
                            @Value("${SensorTower1.Position.Y}") final int tower1PosY,
                            @Value("${SensorTower1.name}") final String tower1Name,
                            @Value("${SensorTower2.Position.X}") final int tower2PosX,
                            @Value("${SensorTower2.Position.Y}") final int tower2PosY,
                            @Value("${SensorTower2.name}") final String tower2Name,
                            TowerPositionPublisher towerPositionPublisher,
                            TargetBearingPositionPublisher targetBearingPositionPublisher)
    {
        tower1 = new SensorTower(new Point(tower1PosX, tower1PosY, tower1Name),
                new BearingPosition(tower1Name));
        tower2 = new SensorTower(new Point(tower2PosX, tower2PosY, tower2Name),
                new BearingPosition(tower2Name));

        this.towerPositionPublisher = towerPositionPublisher;
        this.targetBearingPositionPublisher = targetBearingPositionPublisher;
    } //constructor

    @KafkaListener( groupId = "${spring.kafka.consumer.group-id.tower-2}",
            topicPartitions ={
                    @TopicPartition(
                            topic = "${spring.kafka.topic.consume.name}",
                            partitionOffsets = @PartitionOffset(
                                    partition = "0",
                                    initialOffset = "0",
                                    seekPosition = "END")
                    )
            }
    )
    public void consumerTower2(Point targetPoint)
    {
        try {
            if (targetPoint.getSourceName().equals(centralName))
                return;

            if (targetPoint.getX() == tower2.getPoint().getX()
                    && targetPoint.getY() == tower2.getPoint().getY())
                return;
            processTower(tower2, targetPoint);

        } catch (RuntimeException e) {
            LOGGER.error("Error processing consumerTower2 data", e);;
        }
    } //consumerTower2 func


    @KafkaListener( groupId = "${spring.kafka.consumer.group-id.tower-1}",
            topicPartitions ={
                    @TopicPartition(
                            topic = "${spring.kafka.topic.consume.name}",
                            partitionOffsets = @PartitionOffset(
                                    partition = "0",
                                    initialOffset = "0",
                                    seekPosition = "END")
                    )
            }
    )
    public void consumerTower1(Point targetPoint)
    {
        try {
            if (targetPoint.getSourceName().equals(centralName))
                return;

            if (targetPoint.getX() == tower1.getPoint().getX()
                    && targetPoint.getY() == tower1.getPoint().getY())
                return;

            processTower(tower1, targetPoint);

        } catch (RuntimeException e) {
            LOGGER.error("Error processing consumerTower1 data", e);
        }
    } //consumerTower1 func

    private void processTower(SensorTower tower, Point targetPoint) {
        double degree = CalculateBearing.bearing(tower.getPoint(), targetPoint);
        double distance = CalculateBearing.euclidean_distance(tower.getPoint(), targetPoint);

        tower.getBearingPosition().setDegree(degree);
        tower.getBearingPosition().setDistance(distance);

        this.towerPositionPublisher.publish(tower.getPoint());
        this.targetBearingPositionPublisher.publish(tower.getBearingPosition());
    }

} //PositionConsumer class
