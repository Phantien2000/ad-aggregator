package com.flinters.io;

import com.flinters.model.CampaignStats;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.*;
import java.util.stream.Collectors;

public class OutputWriter {

    private static final int TOP_N = 10;

    public void writeResults(Map<String, CampaignStats> statsMap, String outputDir) throws IOException {
        createOutputDir(outputDir);

        List<Map.Entry<String, CampaignStats>> entries = new ArrayList<>(statsMap.entrySet());

        List<Map.Entry<String, CampaignStats>> topCtr = getTopByCtr(entries);
        List<Map.Entry<String, CampaignStats>> topCpa = getTopByCpa(entries);

        writeCsv(topCtr, outputDir + "/top10_ctr.csv");
        writeCsv(topCpa, outputDir + "/top10_cpa.csv");
    }

    private void createOutputDir(String outputDir) {
        new File(outputDir).mkdirs();
    }

    private List<Map.Entry<String, CampaignStats>> getTopByCtr(List<Map.Entry<String, CampaignStats>> entries) {
        return entries.stream()
                .sorted(Comparator.comparingDouble((Map.Entry<String, CampaignStats> e)
                        -> e.getValue().getCtr()).reversed())
                .limit(TOP_N)
                .collect(Collectors.toList());
    }

    private List<Map.Entry<String, CampaignStats>> getTopByCpa(List<Map.Entry<String, CampaignStats>> entries) {
        return entries.stream()
                .filter(e -> e.getValue().getConversions() > 0)
                .filter(e -> e.getValue().getCpa() != 0.0)
                .sorted(Comparator.comparingDouble(e -> e.getValue().getCpa()))
                .limit(TOP_N)
                .collect(Collectors.toList());
    }

    private void writeCsv(List<Map.Entry<String, CampaignStats>> data, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            writer.write("campaign_id,total_impressions,total_clicks,total_spend,total_conversions,CTR,CPA");
            writer.newLine();

            for (Map.Entry<String, CampaignStats> entry : data) {
                CampaignStats stats = entry.getValue();

                writer.write(formatRow(entry.getKey(), stats));
                writer.newLine();
            }
        }
    }

    private String formatRow(String campaignId, CampaignStats stats) {
        return String.join(",",
                campaignId,
                String.valueOf(stats.getImpressions()),
                String.valueOf(stats.getClicks()),
                String.format("%.2f", stats.getSpend()),
                String.valueOf(stats.getConversions()),
                String.format("%.4f", stats.getCtr()),
                String.format("%.2f", stats.getCpa())
        );
    }
}
