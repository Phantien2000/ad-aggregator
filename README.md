
# Ad Aggregator

## Build
mvn clean package

## Run
java -jar target/ad-aggregator-1.0.jar --input ad_data.csv --output ./out

## Notes
- Streaming processing (BufferedReader)
- No CSV libraries used
- Memory efficient
