package com.flinters.service;

import com.flinters.model.CampaignStats;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AggregationServiceTest {

    @Test
    void shouldAggregateCorrectly() throws Exception {
        File temp = File.createTempFile("test", ".csv");

        try (FileWriter fw = new FileWriter(temp)) {
            fw.write("campaign_id,date,impressions,clicks,spend,conversions\n");
            fw.write("CMP1,2025-01-01,100,10,20.0,2\n");
            fw.write("CMP1,2025-01-02,200,20,40.0,4\n");
        }

        AggregationService service = new AggregationService();
        Map<String, CampaignStats> result = service.process(temp.getAbsolutePath());

        CampaignStats stats = result.get("CMP1");

        assertEquals(300, stats.impressions);
        assertEquals(30, stats.clicks);
        assertEquals(60.0, stats.spend);
        assertEquals(6, stats.conversions);
    }
}