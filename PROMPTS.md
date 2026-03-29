You are a senior software engineer.

Help me build a **production-quality, memory-efficient CLI application** that processes a large CSV file (~1GB) for an ad performance aggregation task.

---

# 🎯 Goal

Build a **robust and scalable CLI application** that:

1. Reads a large CSV file (~1GB)
2. Aggregates data by `campaign_id`
3. Outputs 2 CSV files:

  - Top 10 campaigns by highest CTR
  - Top 10 campaigns by lowest CPA (excluding zero conversions)

---

# 📥 Input CSV Schema

Columns:

- campaign_id (string)
- date (YYYY-MM-DD)
- impressions (int)
- clicks (int)
- spend (double)
- conversions (int)

⚠️ CSV may contain:
- quoted fields
- commas inside quotes
- malformed rows

---

# 🧠 Business Logic

For each `campaign_id`, compute:

- total_impressions
- total_clicks
- total_spend
- total_conversions

Derived metrics:

- CTR = total_clicks / total_impressions
- CPA = total_spend / total_conversions

  - If conversions == 0 → CPA = 0 (or exclude from ranking)

---

# 📤 Output Requirements

## 1. top10_ctr.csv

- Top 10 campaigns sorted by CTR DESC

## 2. top10_cpa.csv

- Top 10 campaigns sorted by CPA ASC
- Exclude campaigns with conversions = 0 or CPA = 0

Both outputs must include:
campaign_id, total_impressions, total_clicks, total_spend, total_conversions, CTR, CPA

---

# ⚙️ Technical Requirements (CRITICAL)

## 🚀 Performance & Memory (TOP PRIORITY)

- File size is ~1GB → MUST NOT load entire file into memory

- Use **streaming processing (row-by-row)**

- Use a CSV parser library:

  - Use OpenCSV (`CSVReader`)
  - Do NOT use heavy frameworks (e.g., Spark, Pandas)

- Ensure:

  - Low memory footprint
  - No storing raw rows
  - Only aggregated data is kept in memory

---

# ⚡ Performance Considerations

- Avoid excessive object creation inside loops
- Avoid logging per row (limit or sample logs)
- Use efficient aggregation:

  - `HashMap<String, CampaignStats>`

- Sorting:

  - Use efficient comparator
  - Optionally optimize using Top-N heap (PriorityQueue)

---

# 🖥️ CLI REQUIREMENT

Application must be runnable via CLI:

```bash
java -jar app.jar --input ad_data.csv --output ./results