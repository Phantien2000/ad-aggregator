You are a senior software engineer.
Help me build a **production-quality, high-performance CLI application** that processes a large CSV file (~1GB) for an ad performance aggregation task.

---

# 🎯 Goal

Build a **memory-efficient and high-performance CLI application** that:

1. Reads a large CSV file (~1GB)
2. Aggregates data by `campaign_id`
3. Outputs 2 CSV files:

   * Top 10 campaigns by highest CTR
   * Top 10 campaigns by lowest CPA (excluding zero conversions)

---

# 📥 Input CSV Schema

Columns:

* campaign_id (string)
* date (YYYY-MM-DD)
* impressions (int)
* clicks (int)
* spend (double)
* conversions (int)

---

# 🧠 Business Logic

For each `campaign_id`, compute:

* total_impressions
* total_clicks
* total_spend
* total_conversions

Derived metrics:

* CTR = total_clicks / total_impressions
* CPA = total_spend / total_conversions

  * If conversions == 0 → CPA = null (exclude from CPA ranking)

---

# 📤 Output Requirements

## 1. top10_ctr.csv

* Top 10 campaigns sorted by CTR DESC

## 2. top10_cpa.csv

* Top 10 campaigns sorted by CPA ASC
* Exclude campaigns with conversions = 0

Both outputs must include:
campaign_id, total_impressions, total_clicks, total_spend, total_conversions, CTR, CPA

---

# ⚙️ Technical Requirements (CRITICAL)

## 🚀 Performance & Memory (TOP PRIORITY)

* File size is ~1GB → MUST NOT load entire file into memory

* Use **streaming processing (line-by-line)**

* Avoid heavy CSV parsing libraries (DO NOT use Apache Commons CSV or OpenCSV)

* Use:

  * BufferedReader
  * Manual parsing (String.split or custom fast parser)

* Minimize:

  * Object allocations
  * Temporary objects
  * String operations

---

# 🖥️ CLI REQUIREMENT

Application must be runnable via CLI:

```bash
java -jar app.jar --input ad_data.csv --output ./results
```

---

# 🧱 Tech Stack

* Java 17+
* Maven
* NO heavy CSV libraries

---

# 🏗️ Architecture Requirements

Design clean and maintainable code:

* Layers:

  * Main (CLI entry point)
  * CSV Reader (streaming, high-performance)
  * Aggregation Service
  * Domain model (CampaignStats)
  * Output Writer

* Follow:

  * Separation of concerns
  * Clear naming
  * Clean code principles

---

# 🚀 Performance Strategy (MUST IMPLEMENT)

* Use BufferedReader to read file line-by-line

* Skip header manually

* Parse each line using:

  * String.split(",") OR a custom fast parser (preferred if possible)

* Use:

  * HashMap<String, CampaignStats> for aggregation

* Store ONLY aggregated data in memory

* Do NOT store raw rows

* After aggregation:

  * Compute CTR & CPA
  * Sort results using efficient comparator
  * Extract top 10

---

# ⚠️ Edge Cases

Handle:

* Missing input file
* Malformed CSV rows (skip or log)
* Division by zero
* Very large numeric values

---

# 🧪 Testing

Include:

* Unit tests for aggregation logic
* Small sample CSV test file

---

# 📊 Metrics (IMPORTANT)

Log:

* Total processing time
* Peak memory usage using Runtime API

---

# 📁 Project Structure

Generate full project:

* src/main/java/...
* src/test/java/...
* pom.xml
* README.md
* PROMPTS.md

---

# 📘 README.md must include:

* How to build (mvn clean package)
* How to run (.jar and mvn exec)
* Example CLI commands
* Performance considerations
* Processing time (approx)
* Peak memory usage
* Design decisions (especially why NOT using CSV libraries)

---

# 📄 PROMPTS.md

Include the prompts used to generate this project (raw prompts)

---

# 🧹 Code Quality

* Clean, readable code
* No dead/commented code
* Proper error handling
* Logging included

---

# 📦 Output

Return:

1. Full project code
2. All important files (Java classes, pom.xml, README.md, etc.)
3. Example output CSV files
4. Instructions to build and run

---

# 🔥 IMPORTANT

* Performance is the TOP priority
* Code must handle 1GB efficiently
* Avoid unnecessary abstractions
* Keep it simple, fast, and production-ready

---

Now generate the complete project.