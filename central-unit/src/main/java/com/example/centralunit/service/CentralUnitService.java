package com.example.centralunit.service;

import com.example.basedomain.dto.BearingPosition;
import com.example.basedomain.dto.Point;
import com.example.centralunit.data.CentralUnit;
import com.example.centralunit.producer.TargetPositionPublisher;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class CentralUnitService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CentralUnitService.class);

    private final TargetPositionPublisher targetPositionPublisher;
    private ConcurrentHashMap<String, CentralUnit> centralMap = new ConcurrentHashMap<>();
    private Point point;
    private final AtomicBoolean running = new AtomicBoolean(false); // use for interrupt thread

    BigDecimal bigDecimalNumberNinety = new BigDecimal(90);
    BigDecimal bigDecimalNumberOneHundredAndEighty = new BigDecimal(180);

    @Value("${CentralUnit.name}")
    private String centralUnitName;

    @Autowired
    public CentralUnitService(TargetPositionPublisher targetPositionPublisher) {
        this.targetPositionPublisher = targetPositionPublisher;
        this.point = new Point();
    }

    public void updateWithPoint(Point point)
    {
        if (point.getSourceName() == null){return;}
        String name = point.getSourceName();
        centralMap.putIfAbsent(name, new CentralUnit());
        CentralUnit centralUnit = centralMap.get(name);
        centralUnit.setX(point.getX());
        centralUnit.setY(point.getY());
    }

    public void updateWithBearing(BearingPosition bearingPosition)
    {
        if (bearingPosition.getSourceName() == null){return;}
        String name = bearingPosition.getSourceName();
        centralMap.putIfAbsent(name, new CentralUnit());
        CentralUnit centralUnit = centralMap.get(name);
        centralUnit.setBearing(bearingPosition.getDegree());
        centralUnit.setDistance(bearingPosition.getDistance());
    }

    public int[] calculateIntersectionPoint(
            double m1,
            double b1,
            double m2,
            double b2)
    {
        int[] result = new int[2];

        if (m1 == m2) {
            result[0] = 0;
            result[1] = 0;
            return result;
        }
        double x = ((b2 - b1) / (m1 - m2));

        result[0] = (int)Math.round(x);
        result[1] = (int)(m1 * x + b1);

        return result; // 0 -> x, 1 -> y

    }

    public double findYIntercept(int posX, int posY, double gradient)
    {
        /*
        find y-intercept that given point

        equation of a line:
        y - y1 = m * (x - x1)
        x1 and y1 is known position
        Any straight line (except vertical) on a plane can be defined by the linear function:
        y = mx + b
        m is gradiant and b is y-intercept
        we will find out b in this equation
         */

        return ((double)posY - gradient * posX);
    }

    private double getDegreeFromBearing(double bearing)
    {
        return 90 - bearing;
    }

    public double calculateGradientFromBearing(double bearing)
    {
        double gradient;
        if (bearing > 180 ) bearing -= 180;
        if (bearing == 0 || bearing == 180) gradient =Double.POSITIVE_INFINITY;
        if (bearing == 90) gradient = 0.0;

        BigDecimal bigDecimalBearing = new BigDecimal(bearing);

        double a = bigDecimalNumberNinety.subtract(bigDecimalBearing).doubleValue();
        double b = bigDecimalNumberOneHundredAndEighty.subtract(bigDecimalBearing).doubleValue();

        gradient = Math.sin(Math.toRadians(a)) / Math.sin(Math.toRadians(b));

        return  gradient;
    }

    @Async
    public void run()
    {
        running.set(true);
        while (running.get())
        {
            if(Thread.currentThread().isInterrupted())
                break;

            if(!isComputable())
                continue;

            ArrayList<CentralUnit> list = new ArrayList<CentralUnit>(centralMap.values());
            int[] target_point = new int[2];

            double towerGradient1 = calculateGradientFromBearing(list.get(0).getBearing());
            double towerGradient2 = calculateGradientFromBearing(list.get(1).getBearing());

            double towerYAxisIntercept1 = findYIntercept(
                                        list.get(0).getX(),
                                        list.get(0).getY(),
                                        towerGradient1
            );
            double towerYAxisIntercept2 = findYIntercept(
                                        list.get(1).getX(),
                                        list.get(1).getY(),
                                        towerGradient2
            );

            target_point = calculateIntersectionPoint(towerGradient1, towerYAxisIntercept1,
                                                    towerGradient2, towerYAxisIntercept2
                                                        );

            point.setX(target_point[0]);
            point.setY(target_point[1]);
            point.setSourceName(centralUnitName);

            LOGGER.info(point.toString());
            targetPositionPublisher.publish(point);
            centralMap.clear();
        }

    }

    private boolean isComputable()
    {
        if(centralMap.size() < 2)
            return false;
        for(String name : centralMap.keySet())
        {
            if (!centralMap.get(name).isComplete())
                return false;
        }
        return true;
    }

    @PreDestroy
    public void stop() {
        running.set(false);
        Thread.currentThread().interrupt();
        LOGGER.info("Shutting down TargetSimulator");
    }

}
