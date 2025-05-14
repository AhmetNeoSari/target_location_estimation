package com.example.worldsimulator.simulator;

import com.example.worldsimulator.data.Point;
import com.example.worldsimulator.data.Target;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class TargetSimulator{
    private Target target;
    public long start_time = System.currentTimeMillis();


    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.MILLISECONDS)
    public void updatePosition() {
        long last_time = System.currentTimeMillis();
        long timeElapsed = last_time - start_time;
        Point point = target.getPoint();
        double v_x = target.getSpeed() * Math.cos(target.getHeading());
        double v_y = target.getSpeed() * Math.sin(target.getHeading());
        point.setX((int) (v_x * timeElapsed));
        point.setY((int) (v_y * timeElapsed));
        target.setPoint(point);
    }

    public TargetSimulator(Target target) {
        this.target = target;
    }



}
