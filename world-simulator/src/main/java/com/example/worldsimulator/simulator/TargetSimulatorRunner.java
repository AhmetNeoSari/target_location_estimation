package com.example.worldsimulator.simulator;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;

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


    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(1);
        executor.setDaemon(true);
        executor.setThreadNamePrefix("WorldSimulator-");

        executor.initialize();
        return executor;
    }

}