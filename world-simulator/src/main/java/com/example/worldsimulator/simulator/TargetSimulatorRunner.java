package com.example.worldsimulator.simulator;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TargetSimulatorRunner  implements CommandLineRunner {
    private final TargetSimulator simulator;

    public TargetSimulatorRunner(TargetSimulator simulator) {
        this.simulator = simulator;
    }

    @Override
    public void run(String... args) throws Exception {
        simulator.start();
    }
}