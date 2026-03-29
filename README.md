# Ad Aggregator CLI

## 🛠️ Setup Instructions

Requirements:

* Java 8+ (compatible)
* Java 17 recommended
* Maven 3+

Build:

```bash
mvn clean package
```

---

## ▶️ How to Run

```bash
java -jar target/ad-aggregator-1.0.jar \
  --input ad_data.csv \
  --output ./results
```

Output:

* `top10_ctr.csv`
* `top10_cpa.csv`

---

## 📚 Libraries Used

* Java Standard Library + lightweight CSV parsing library:
  * BufferedReader (used internally by CSVReader for efficient IO)
  * BufferedWriter
  * HashMap
  * Runtime API
  * OpenCSV (CSVReader for correct CSV parsing)

---

## ⏱️ Processing Time (1GB file)

* ~11.5 seconds (depending on hardware)

---

## 🧠 Peak Memory Usage

* ~260 MB
* Measured using a background monitoring thread

