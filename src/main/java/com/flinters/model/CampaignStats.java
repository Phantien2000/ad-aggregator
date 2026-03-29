package com.flinters.model;

public class CampaignStats {

    private long impressions;
    private long clicks;
    private double spend;
    private long conversions;

    public void add(long impressions, long clicks, double spend, long conversions) {
        this.impressions += impressions;
        this.clicks += clicks;
        this.spend += spend;
        this.conversions += conversions;
    }

    public long getImpressions() {
        return impressions;
    }

    public long getClicks() {
        return clicks;
    }

    public double getSpend() {
        return spend;
    }

    public long getConversions() {
        return conversions;
    }

    public double getCtr() {
        return impressions == 0 ? 0.0 : (double) clicks / impressions;
    }

    public double getCpa() {
        return conversions == 0 ? 0.0 : spend / conversions;
    }
}