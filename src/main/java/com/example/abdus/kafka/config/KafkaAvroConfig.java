package com.example.abdus.kafka.config;

import com.example.abdus.kafka.model.User;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaAvroConfig {

    private final KafkaProperties kafkaProperties;

    // Inject the KafkaProperties bean populated by the KafkaJsonLoader
    public KafkaAvroConfig(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    // --- CONSUMER CONFIGURATION (FIXES STARTUP ERROR) ---

    private Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();

        // 1. Add top-level properties
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        props.put("schema.registry.url", kafkaProperties.getSchemaRegistryUrl());

        // 2. Add all consumer map properties (group.id, deserializers, etc.)
        if (kafkaProperties.getConsumer() != null) {
            props.putAll(kafkaProperties.getConsumer());
        }

        // 3. CRITICAL FIXES: Map custom JSON keys to Kafka/Confluent required keys

        // This explicitly adds key.deserializer and value.deserializer, RESOLVING the ConfigException.
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, props.get("keyDeserializer"));
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, props.get("valueDeserializer"));

        // Avro Specific Fix: Map JSON's "specificAvroReader" to Kafka's "specific.avro.reader"
        if (props.containsKey("specificAvroReader")) {
            props.put("specific.avro.reader", props.get("specificAvroReader"));
            props.remove("specificAvroReader"); // Clean up the camelCase key
        }

        return props;
    }

    @Bean
    public ConsumerFactory<String, User> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, User> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, User> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    // --- PRODUCER CONFIGURATION ---

    @Bean
    public ProducerFactory<String, User> producerFactory() {
        Map<String, Object> props = new HashMap<>();

        // 1. Add top-level properties
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        props.put("schema.registry.url", kafkaProperties.getSchemaRegistryUrl());

        // 2. Add all producer map properties (including keySerializer and valueSerializer)
        if (kafkaProperties.getProducer() != null) {
            props.putAll(kafkaProperties.getProducer());
        }

        // 3. CRITICAL FIX: Explicitly map camelCase serializers to dot-notation Kafka keys

        // Ensures key.serializer is set
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, props.get("keySerializer"));

        // Ensures value.serializer is set
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, props.get("valueSerializer"));

        // Optional: Remove the camelCase keys if you prefer a clean final map (not strictly necessary)
        props.remove("keySerializer");
        props.remove("valueSerializer");

        return new DefaultKafkaProducerFactory<>(props);
    }
    @Bean
    public KafkaTemplate<String, User> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}