kafka-storage.bat random-uuid

kafka-storage.bat format -t WJoMpGRNRfO0eUVxZcyXvQ -c C:/SW/kafka_4.1.1/config/server.properties

kafka-server-start.bat C:/SW/kafka_4.1.1/config/server.properties

kafka-topics.bat --create --topic test_topic --bootstrap-server localhost:9092

kafka-topics.bat --list --bootstrap-server localhost:9092


kafka-console-producer.bat --topic test --bootstrap-server localhost:9092

kafka-console-consumer.bat --topic test_topic --from-beginning --bootstrap-server localhost:9092

-----prompt--
spring boot kafka producer and consumer example with maven with kafka configuration in kafkaConfig.json , use avro

docker run -d \
  --name schema-registry \
  -p 8081:8081 \
  -e SCHEMA_REGISTRY_HOST_NAME=schema-registry \
  -e SCHEMA_REGISTRY_LISTENERS=http://0.0.0.0:8081 \
  -e SCHEMA_REGISTRY_KAFKASTORE_BOOTSTRAP_SERVERS=localhost:9092 \
  confluentinc/cp-schema-registry:latest
