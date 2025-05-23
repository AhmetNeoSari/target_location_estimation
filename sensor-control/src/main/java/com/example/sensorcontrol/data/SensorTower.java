package com.example.sensorcontrol.data;

import com.example.basedomain.Point;
import com.example.basedomain.dto.BearingPosition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensorTower {
    private Point point;
    private BearingPosition bearingPosition;
}
