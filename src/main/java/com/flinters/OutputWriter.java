
package com.flinters;

import java.io.*;
import java.util.*;
import java.util.stream.*;

public class OutputWriter {

    public void writeResults(Map<String, CampaignStats> map, String outDir) throws Exception {
        new File(outDir).mkdirs();

        List<Map.Entry<String, CampaignStats>> list = new ArrayList<>(map.entrySet());

        List<Map.Entry<String, CampaignStats>> topCtr = list.stream()
            .sorted((a, b) -> Double.compare(b.getValue().ctr(), a.getValue().ctr()))
            .limit(10)
            .collect(Collectors.toList());

        List<Map.Entry<String, CampaignStats>> topCpa = list.stream()
            .filter(e -> e.getValue().conversions > 0)
            .sorted((a, b) -> Double.compare(a.getValue().cpa(), b.getValue().cpa()))
            .limit(10)
            .collect(Collectors.toList());

        writeCsv(topCtr, outDir + "/top10_ctr.csv");
        writeCsv(topCpa, outDir + "/top10_cpa.csv");
    }

    private void writeCsv(List<Map.Entry<String, CampaignStats>> data, String path) throws Exception {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            bw.write("campaign_id,total_impressions,total_clicks,total_spend,total_conversions,CTR,CPA\n");

            for (var e : data) {
                CampaignStats s = e.getValue();
                bw.write(e.getKey() + "," +
                        s.impressions + "," +
                        s.clicks + "," +
                        s.spend + "," +
                        s.conversions + "," +
                        s.ctr() + "," +
                        (s.cpa() == null ? "" : s.cpa()) +
                        "\n");
            }
        }
    }
}
