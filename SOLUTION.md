# Solution Note

## 1. Time Efficiency

* The application uses **streaming processing (BufferedReader)** to read the CSV file line-by-line, avoiding the overhead of loading the entire file into memory.
* Each row is processed in **O(1)** time, and the overall complexity is **O(N)** where N is the number of rows.
* Aggregation is performed using a **HashMap**, providing near **constant-time updates (O(1))** per record.
* Sorting is only applied to the **aggregated result set**, not the raw dataset, significantly reducing computational cost.

---

## 2. Memory Efficiency

* The solution does **not store raw input data**, only aggregated results per `campaign_id`.
* Memory usage is proportional to the number of unique campaigns (**O(K)**), not the number of rows.
* This allows the application to handle large files (~1GB or more) with **low and stable memory consumption**.
* Peak memory usage is monitored during execution to ensure the system remains within acceptable limits.

---

## 3. Why Not Use CSV Libraries

CSV libraries such as Apache Commons CSV or OpenCSV were intentionally avoided due to performance considerations:

* They introduce **additional object creation** (e.g., CSVRecord, String wrappers), increasing memory usage.
* Parsing is often **less efficient** due to abstraction layers and validation logic.
* They may perform **extra checks (escaping, quoting, validation)** that are unnecessary for well-structured input data.
* For large datasets (~1GB), these overheads can significantly impact both **execution time and garbage collection pressure**.

Instead, the solution uses:

* `BufferedReader` for efficient I/O
* Simple `String.split(",")` for fast parsing

This approach minimizes overhead and provides **better control over performance and memory usage**.

---

## 4. Trade-offs

* Manual parsing assumes the input CSV is **well-formed** (no complex quoting or escaping).
* Error handling is simplified by skipping malformed rows instead of performing heavy validation.
* The design prioritizes **performance and scalability** over full CSV specification compliance.

---

## 5. Summary

* Time Complexity: **O(N)**
* Memory Complexity: **O(K)** (number of campaigns)
* Optimized for:

    * Large file processing
    * Low memory footprint
    * High throughput

