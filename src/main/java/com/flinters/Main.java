
package com.flinters;

import java.io.File;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        String input = null;
        String output = null;

        for (int i = 0; i < args.length; i++) {
            if ("--input".equals(args[i])) input = args[++i];
            if ("--output".equals(args[i])) output = args[++i];
        }

        if (input == null || output == null) {
            System.err.println("Usage: java -jar app.jar --input <file> --output <dir>");
            return;
        }

        long start = System.currentTimeMillis();

        AggregationService service = new AggregationService();
        Map<String, CampaignStats> map = service.process(input);

        OutputWriter writer = new OutputWriter();
        writer.writeResults(map, output);

        long end = System.currentTimeMillis();

        Runtime rt = Runtime.getRuntime();
        long used = (rt.totalMemory() - rt.freeMemory()) / (1024 * 1024);

        System.out.println("Time(ms): " + (end - start));
        System.out.println("Memory(MB): " + used);
    }
}
