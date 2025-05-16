package com.example.worldsimulator.data;

import com.example.basedomain.Point;
import com.example.worldsimulator.service.TargetPositionPublisher;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

@Component
@Data
public class Target {
    private static final Logger LOGGER = LoggerFactory.getLogger(Target.class);

    private Point point;

    public Target() {
        point = new Point();
    }


    @Value("${target.heading}")
    private Integer  heading;

    @Value("${target.initial.position.X}")
    private Integer initalPointX;

    @Value("${target.initial.position.Y}")
    private Integer initialPointY;

    @Value("${target.speed}")
    private Double speed;

    @PostConstruct
    public void init() {
        point.setX(initalPointX);
        point.setY(initialPointY);
        this.heading %= 360;
    }

}
