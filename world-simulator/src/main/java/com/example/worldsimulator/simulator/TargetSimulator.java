package com.example.worldsimulator.simulator;

import com.example.worldsimulator.data.Target;
import com.example.worldsimulator.service.TargetPositionPublisher;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
@EnableAsync
public class TargetSimulator{

    private static final Logger LOGGER = LoggerFactory.getLogger(TargetSimulator.class);

    private final double headingRad;
    private Target target;
    private TargetPositionPublisher targetPositionPublisher;
    private final AtomicBoolean running = new AtomicBoolean(false); // use for interrupt thread
    public long start_time = System.currentTimeMillis();

    @Async
    public void start(){
        running.set(true);
        while(running.get()) {
            try {
                updatePositionAndPublish();
                if(Thread.currentThread().isInterrupted())
                    break;
            } catch (Exception e) {
                LOGGER.error("Simulation error", e);
            }
        }
    }

    private void updatePositionAndPublish()
    {

        long cuurent_time = System.currentTimeMillis();
        int timeElapsed = (int) ((cuurent_time - start_time) / 1000);

        double v_y = target.getSpeed() * Math.cos(headingRad);
        double v_x = target.getSpeed() * Math.sin(headingRad);
        target.getPoint().setX((int) Math.round(target.getInitalPointX() + (v_x * timeElapsed)));
        target.getPoint().setY((int) Math.round(target.getInitialPointY() + (v_y * timeElapsed)));
        targetPositionPublisher.sendMessage(target.getPoint());
    }

    public TargetSimulator(Target target, TargetPositionPublisher targetPositionPublisher) {
        this.target = target;
        this.targetPositionPublisher = targetPositionPublisher;
        this.headingRad = Math.toRadians(this.target.getHeading());
    }

    @PreDestroy
    public void stop() {
        running.set(false);
        Thread.currentThread().interrupt();
        LOGGER.info("Shutting down TargetSimulator");
    }

}
