package com.sdia.kafkaadmin.config;

import com.sdia.appconfigdata.config.KafkaConfigData;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.support.RetryTemplate;

import java.util.Map;

@EnableRetry
@Configuration
@ComponentScan(basePackages = "com.sdia.appconfigdata.config")
public class KafkaAdminConfig {

    private final KafkaConfigData kafkaConfigData;

    public KafkaAdminConfig(KafkaConfigData configData) {
        this.kafkaConfigData = configData;
    }

    @Bean
    public AdminClient adminClient() {
        return AdminClient.create(Map.of(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG,
                kafkaConfigData.getBootstrapServers()));
    }

    @Bean
    public RetryTemplate retryTemplate() {
        return new RetryTemplate();
    }
}
