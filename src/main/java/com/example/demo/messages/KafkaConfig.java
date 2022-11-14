package com.example.demo.messages;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Profile("!test")
public class KafkaConfig {
    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic personCreationTopic() {
        return new NewTopic("personCreation", 1, (short) 3);
    }

    @Bean
    public NewTopic personCreationError() {
        return new NewTopic("personCreationError", 1, (short) 1);
    }

    //Did not work for now
    //@Bean
    //public NewTopic dlt() {
    //    return new NewTopic("personCreation.DLT", 3, (short) 1);
    //}
}
