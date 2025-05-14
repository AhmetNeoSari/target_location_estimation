package com.example.worldsimulator.data;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

@Component
@Data
public class Target {
    private Point point;

    public Target() {
        point = new Point();
    }

    @Value("${target.heading}")
    private int heading;

    @Value("${target.initial.position.X}")
    private int pointX;

    @Value("${target.initial.position.Y}")
    private int pointY;

    @Value("${target.speed}")
    private int speed;

    @PostConstruct
    public void init() {
        point.setX(pointX);
        point.setY(pointY);
        this.heading = this.heading % 360;
    }

}
