package com.example.sensorcontrol.calculation;

//import java.awt.*;
import com.example.basedomain.dto.Point;

import java.math.BigDecimal;

public class CalculateBearing {

    public static double bearing(Point sensorPoint,
                                 Point targetPoint)
    {

        double dx = targetPoint.getX() - sensorPoint.getX();
        double dy = targetPoint.getY() - sensorPoint.getY();

        double thetaRad = Math.atan2(dx, dy);
        double thetaDeg = Math.toDegrees(thetaRad);

        return (thetaDeg + 360) % 360;
    }

    public static double euclidean_distance(Point sensorPoint, Point targetPoint)
    {
        double ac = Math.abs(targetPoint.getY() - sensorPoint.getY());
        double cb = Math.abs(targetPoint.getX() - sensorPoint.getX());

        return Math.hypot(ac, cb);
    }
}
