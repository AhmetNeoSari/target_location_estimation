package com.example.worldsimulator.data;

import com.example.basedomain.dto.Point;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.apache.kafka.common.protocol.types.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

@Component
@Data
public class Target {
    private static final Logger LOGGER = LoggerFactory.getLogger(Target.class);

    private int heading;
    private int initalPointX;
    private int initialPointY;
    private double speed;
    private String name;
    private Point point;

    @Autowired
    public Target(@Value("${target.heading}") final int heading,
                  @Value("${target.initial.position.X}") final int posX,
                  @Value("${target.initial.position.Y}") final int posY,
                  @Value("${target.speed}") final double speed,
                  @Value("${target.name}") final String name
                  ) {
        this.heading        = heading;
        this.initalPointX   = posX;
        this.initialPointY  = posY;
        this.speed          = speed;
        this.name           = name;
        System.out.println("this.name: " + name);
        this.point = new Point(posX, posY, name);
    }

}
