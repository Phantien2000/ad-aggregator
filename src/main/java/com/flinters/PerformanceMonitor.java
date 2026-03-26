package com.flinters;

public class PerformanceMonitor {

    private long startTime;
    private volatile long peakMemory = 0;
    private Thread monitorThread;

    public void start() {
        startTime = System.currentTimeMillis();

        monitorThread = new Thread(() -> {
            Runtime rt = Runtime.getRuntime();
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    long used = rt.totalMemory() - rt.freeMemory();
                    peakMemory = Math.max(peakMemory, used);
                    Thread.sleep(50);
                }
            } catch (InterruptedException ignored) {}
        });

        monitorThread.start();
    }

    public void stop() throws InterruptedException {
        monitorThread.interrupt();
        monitorThread.join();
    }

    public void printStats() {
        long endTime = System.currentTimeMillis();

        System.out.println("Time(ms): " + (endTime - startTime));
        System.out.println("Peak Memory(MB): " + (peakMemory / (1024 * 1024)));
    }
}