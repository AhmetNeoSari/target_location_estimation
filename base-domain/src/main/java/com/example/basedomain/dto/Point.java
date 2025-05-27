package com.example.basedomain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Point {
    private int x;
    private int y;
    private String sourceName;

    public Point(String sourceName) {
        this.sourceName = sourceName;
    }

}
