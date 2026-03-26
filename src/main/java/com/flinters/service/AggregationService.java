
package com.flinters.service;

import com.flinters.model.CampaignStats;

import java.io.*;
import java.util.*;

public class AggregationService {

    public Map<String, CampaignStats> process(String file) throws Exception {
        Map<String, CampaignStats> map = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file), 1024 * 1024)) {
            String line;

            while ((line = br.readLine()) != null) {
                try {
                    String[] p = line.split(",");
                    String id = p[0];
                    long imp = Long.parseLong(p[2]);
                    long clk = Long.parseLong(p[3]);
                    double sp = Double.parseDouble(p[4]);
                    long conv = Long.parseLong(p[5]);

                    CampaignStats s = map.computeIfAbsent(id, k -> new CampaignStats());
                    s.add(imp, clk, sp, conv);

                } catch (Exception e) {
                    // skip malformed
                }
            }
        }

        return map;
    }
}
