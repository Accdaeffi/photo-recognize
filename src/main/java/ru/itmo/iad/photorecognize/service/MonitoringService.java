package ru.itmo.iad.photorecognize.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

@Service
public class MonitoringService {
    private final Counter recognizeCounter;
    private final Counter recognizeErrorCounter;

    private final Counter startCommandCounter;
    private final Counter startCommandErrorCounter;
    private final Counter helpCommandCounter;
    private final Counter helpCommandErrorCounter;

    public MonitoringService(MeterRegistry meterRegistry) {
        recognizeCounter = Counter.builder("recognize_command_counter")
                .register(meterRegistry);
        recognizeErrorCounter = Counter.builder("error_recognize_command_countrer")
                .register(meterRegistry);
        startCommandCounter = Counter.builder("start_command_counter")
                .register(meterRegistry);
        startCommandErrorCounter = Counter.builder("start_command_error_counter")
                .register(meterRegistry);
        helpCommandCounter = Counter.builder("help_command_counter")
                .register(meterRegistry);
        helpCommandErrorCounter = Counter.builder("help_command_error_counter")
                .register(meterRegistry);
    }

    public void incrementRecognizeCounter() {
        recognizeCounter.increment();
    }

    public void incrementErrorRecognizeCounter() {
        recognizeErrorCounter.increment();
    }

    public void incrementStartCounter() {
        startCommandCounter.increment();
    }

    public void incrementStartErrorCounter() {
        startCommandErrorCounter.increment();
    }

    public void incrementHelpCounter() {
        helpCommandCounter.increment();
    }

    public void incrementHelpErrorCounter() {
        helpCommandErrorCounter.increment();
    }
}
