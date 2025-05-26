package com.example.centralunit.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CentralUnit {
    private Integer x;
    private Integer y;
    private Double bearing;
    private Double distance;

    public boolean isComplete() {
        return x != null && y != null && bearing != null && distance != null;
    }
}
