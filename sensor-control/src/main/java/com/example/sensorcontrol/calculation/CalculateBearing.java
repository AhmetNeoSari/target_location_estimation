package com.example.sensorcontrol.calculation;

//import java.awt.*;
import com.example.basedomain.dto.Point;

public class CalculateBearing {
    public static double bearing(Point sensorPoint, Point targetPoint)
    {
        double longitude1 = sensorPoint.getY();
        double longitude2 = targetPoint.getY();
        double latitude1 = Math.toRadians(sensorPoint.getX());
        double latitude2 = Math.toRadians(targetPoint.getX());
        double longDiff= Math.toRadians(longitude2-longitude1);
        double y= Math.sin(longDiff)*Math.cos(latitude2);
        double x=Math.cos(latitude1)*Math.sin(latitude2)-Math.sin(latitude1)*Math.cos(latitude2)*Math.cos(longDiff);
        double resultDegree= (Math.toDegrees(Math.atan2(y, x))+360)%360;

        return resultDegree;
    }

    public static double euclidean_distance(Point sensorPoint, Point targetPoint)
    {
        double ac = Math.abs(targetPoint.getY() - sensorPoint.getY());
        double cb = Math.abs(targetPoint.getX() - sensorPoint.getX());

        return Math.hypot(ac, cb);
    }
}
