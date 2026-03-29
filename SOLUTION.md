# Solution Note

## 1. Time Efficiency

* The application uses **streaming processing (CSVReader with BufferedReader internally)** to read the CSV file row-by-row, avoiding loading the entire file into memory.
* Each row is processed in **O(1)** time, resulting in an overall time complexity of **O(N)** where N is the number of rows.
* Aggregation is performed using a **HashMap**, providing near **constant-time updates (O(1))** per record.
* Sorting is applied only to the **aggregated dataset**, not the raw input, significantly reducing computational overhead.

---

## 2. Memory Efficiency

* The solution does **not store raw input rows**, only aggregated results per `campaign_id`.
* Memory usage is proportional to the number of unique campaigns (**O(K)**), not the number of input rows.
* This enables processing of large files (~1GB or more) with **low and predictable memory consumption**.
* Peak memory usage is monitored using the Runtime API to ensure system stability.

---

## 3. CSV Parsing Strategy

The application uses **OpenCSV (CSVReader)** for parsing instead of manual string splitting.

### Rationale:

* Ensures **correct handling of real-world CSV data**, including:
  - quoted fields
  - commas inside fields
  - edge cases in formatting
* Avoids data corruption that can occur with naive parsing (e.g., `String.split(",")`)

### Performance Consideration:

* While OpenCSV introduces **slightly higher overhead** compared to manual parsing,
  it provides **data correctness and robustness**, which are critical in production systems.
* The impact on performance is acceptable because:
  - I/O and aggregation dominate processing time
  - Only one row is processed at a time (streaming)
  - No unnecessary data structures are retained in memory

---

## 4. Trade-offs

* Using OpenCSV trades a small amount of performance for **correctness and reliability**.
* Manual parsing (e.g., `split(",")`) could be faster but is unsafe for real-world CSV data.
* Logging is controlled to avoid performance degradation when processing large files.
* Malformed rows are skipped to maintain throughput and avoid expensive validation logic.

---

## 5. Summary

* Time Complexity: **O(N)**
* Memory Complexity: **O(K)** (number of unique campaigns)

Optimized for:

* Large-scale file processing (~1GB+)
* Low memory footprint
* High throughput
* Robust handling of real-world CSV formats