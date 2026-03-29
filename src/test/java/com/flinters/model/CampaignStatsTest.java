package com.flinters.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CampaignStatsTest {

    @Test
    void shouldCalculateCtrCorrectly() {
        CampaignStats s = new CampaignStats();
        s.add(100, 10, 20.0, 2);

        assertEquals(0.1, s.getCtr());
    }

    @Test
    void shouldReturnZeroCtrWhenNoImpression() {
        CampaignStats s = new CampaignStats();
        s.add(0, 10, 20.0, 2);

        assertEquals(0.0, s.getCtr());
    }

    @Test
    void shouldCalculateCpaCorrectly() {
        CampaignStats s = new CampaignStats();
        s.add(100, 10, 20.0, 2);

        assertEquals(10.0, s.getCpa());
    }

    @Test
    void shouldReturnNullCpaWhenNoConversion() {
        CampaignStats s = new CampaignStats();
        s.add(100, 10, 20.0, 0);

        assertEquals(0.0, s.getCpa());
    }
}