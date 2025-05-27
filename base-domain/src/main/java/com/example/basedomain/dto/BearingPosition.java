package com.example.basedomain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BearingPosition {
    private double degree;
    private double distance;
    private String sourceName;

    public BearingPosition(String sourceName) {
        this.sourceName = sourceName;
    }
}
