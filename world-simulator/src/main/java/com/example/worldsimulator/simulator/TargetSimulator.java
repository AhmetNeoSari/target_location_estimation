package com.example.worldsimulator.simulator;

import com.example.worldsimulator.data.Target;
import com.example.worldsimulator.service.TargetPositionPublisher;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import com.example.basedomain.Point;

@Component
@EnableAsync
public class TargetSimulator{

    private static final Logger LOGGER = LoggerFactory.getLogger(TargetSimulator.class);

    private Point point = new Point();
    private Target target;
    private TargetPositionPublisher targetPositionPublisher;
    public long start_time = System.currentTimeMillis();

//    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.SECONDS)
    @Async
    public void updatePosition() throws RuntimeException{
        point = target.getPoint();
        while(true) {
            try {
                long last_time = System.currentTimeMillis();
                int timeElapsed = (int) ((last_time - start_time) / 1000);

                double v_y = target.getSpeed() * Math.cos(target.getHeading());
                double v_x = target.getSpeed() * Math.sin(target.getHeading());
                point.setX((int) (target.getInitalPointX() + (v_x * timeElapsed)));
                point.setY((int) (target.getInitialPointY() + (v_y * timeElapsed)));
    //            target.setPoint(point);
    //            LOGGER.info(point.toString());
    //            LOGGER.info(target.toString());
                targetPositionPublisher.sendMessage(point);
                LOGGER.info("hello");

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
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

    @PostConstruct
    public void run () throws RuntimeException
    {
        updatePosition();
    }

}
