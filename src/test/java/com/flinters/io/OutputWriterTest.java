package com.flinters.io;

import com.flinters.model.CampaignStats;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OutputWriterTest {

    @Test
    void shouldFormatOutputCorrectly() throws Exception {
        Map<String, CampaignStats> map = new HashMap<>();

        CampaignStats s = new CampaignStats();
        s.add(100, 10, 19.3, 2);

        map.put("CMP1", s);

        File dir = Files.createTempDirectory("out").toFile();

        OutputWriter writer = new OutputWriter();
        writer.writeResults(map, dir.getAbsolutePath());

        File file = new File(dir, "top10_ctr.csv");
        String content = Files.readString(file.toPath());

        assertTrue(content.contains("19.30")); // spend
        assertTrue(content.contains("0.1000")); // CTR
        assertTrue(content.contains("9.65")); // CPA
    }
}