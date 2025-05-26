package com.example.centralunit.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class CentralUnitServiceRunner  implements CommandLineRunner {
    private final CentralUnitService simulator;

    public CentralUnitServiceRunner(CentralUnitService simulator) {
        this.simulator = simulator;
    }

    @Override
    public void run(String... args) throws Exception {
        simulator.run();
    }
}