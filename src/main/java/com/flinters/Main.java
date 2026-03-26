package com.flinters;

import com.flinters.cli.CliArgs;
import com.flinters.io.OutputWriter;
import com.flinters.metrics.PerformanceMonitor;
import com.flinters.model.CampaignStats;
import com.flinters.service.AggregationService;

import java.util.Map;

public class Main {

    public static void main(String[] args) throws Exception {
        CliArgs cli = CliArgs.parse(args);

        PerformanceMonitor monitor = new PerformanceMonitor();
        monitor.start();

        AggregationService service = new AggregationService();
        Map<String, CampaignStats> map = service.process(cli.getInput());

        OutputWriter writer = new OutputWriter();
        writer.writeResults(map, cli.getOutput());

        monitor.stop();
        monitor.printStats();
    }
}