
package com.flinters;

public class CampaignStats {
    public long impressions;
    public long clicks;
    public double spend;
    public long conversions;

    public void add(long imp, long clk, double sp, long conv) {
        impressions += imp;
        clicks += clk;
        spend += sp;
        conversions += conv;
    }

    public double ctr() {
        return impressions == 0 ? 0 : (double) clicks / impressions;
    }

    public Double cpa() {
        return conversions == 0 ? null : spend / conversions;
    }
}
