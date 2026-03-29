package com.flinters.metrics;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

public class PerformanceMonitor {

    private static final Logger logger = Logger.getLogger(PerformanceMonitor.class.getName());

    private static final long MONITOR_INTERVAL_MS = 50;

    private long startTime;
    private long peakMemory = 0;

    private Thread monitorThread;
    private final AtomicBoolean running = new AtomicBoolean(false);

    public void start() {
        startTime = System.nanoTime();
        running.set(true);

        monitorThread = new Thread(() -> {
            Runtime runtime = Runtime.getRuntime();

            while (running.get()) {
                long used = runtime.totalMemory() - runtime.freeMemory();
                peakMemory = Math.max(peakMemory, used);

                try {
                    Thread.sleep(MONITOR_INTERVAL_MS);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "performance-monitor-thread");

        monitorThread.setDaemon(true);
        monitorThread.start();
    }

    public void stop() {
        if (monitorThread == null) {
            return;
        }

        running.set(false);

        try {
            monitorThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void printStats() {
        long durationMs = (System.nanoTime() - startTime) / 1_000_000;

        logger.info("Execution time (ms): " + durationMs);
        logger.info("Peak memory (MB): " + (peakMemory / (1024 * 1024)));
    }
}