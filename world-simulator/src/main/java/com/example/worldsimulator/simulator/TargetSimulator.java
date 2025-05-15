package com.example.worldsimulator.simulator;

import com.example.worldsimulator.data.Point;
import com.example.worldsimulator.data.Target;
import com.example.worldsimulator.service.TargetPositionPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class TargetSimulator{

    private static final Logger LOGGER = LoggerFactory.getLogger(TargetSimulator.class);

    private Target target;
    private TargetPositionPublisher targetPositionPublisher;
    public long start_time = System.currentTimeMillis();

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.SECONDS)
    public void updatePosition() {
        long last_time = System.currentTimeMillis();
        int timeElapsed = (int)((last_time - start_time) / 1000 );

        Point point = target.getPoint();
        double v_y = target.getSpeed() * Math.cos(target.getHeading());
        double v_x = target.getSpeed() * Math.sin(target.getHeading());
        point.setX((int) (target.getInitalPointX() + (v_x * timeElapsed)));
        point.setY((int) (target.getInitialPointY() + (v_y * timeElapsed)));
        target.setPoint(point);
        targetPositionPublisher.sendMessage(point);
        LOGGER.info(target.toString());
    }

//    @Async
//    public void run(){
//        LOGGER.info("here");
//        this.targetPositionPublisher.sendMessage(target.getPoint());
//    }

    public TargetSimulator(Target target, TargetPositionPublisher targetPositionPublisher) {
        this.target = target;
        this.targetPositionPublisher = targetPositionPublisher;

    }

}
