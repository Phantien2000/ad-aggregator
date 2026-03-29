package com.flinters.service;

import com.flinters.model.CampaignStats;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class AggregationService {

    private static final Logger logger = Logger.getLogger(AggregationService.class.getName());

    private static final int IDX_ID = 0;
    private static final int IDX_IMPRESSIONS = 2;
    private static final int IDX_CLICKS = 3;
    private static final int IDX_SPEND = 4;
    private static final int IDX_CONVERSIONS = 5;

    private static final int MIN_COLUMNS = 6;

    public Map<String, CampaignStats> process(String filePath) throws IOException, CsvValidationException {
        Map<String, CampaignStats> result = new HashMap<>();

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] parts;

            while ((parts = reader.readNext()) != null) {
                if (parts.length < MIN_COLUMNS) {
                    continue;
                }

                try {
                    String id = parts[IDX_ID];

                    long impressions = Long.parseLong(parts[IDX_IMPRESSIONS]);
                    long clicks = Long.parseLong(parts[IDX_CLICKS]);
                    double spend = Double.parseDouble(parts[IDX_SPEND]);
                    long conversions = Long.parseLong(parts[IDX_CONVERSIONS]);

                    result.computeIfAbsent(id, k -> new CampaignStats())
                            .add(impressions, clicks, spend, conversions);

                } catch (NumberFormatException e) {
                    logger.warning("Invalid number at line: " + String.join(",", parts));
                }
            }
        }
        return result;
    }
}