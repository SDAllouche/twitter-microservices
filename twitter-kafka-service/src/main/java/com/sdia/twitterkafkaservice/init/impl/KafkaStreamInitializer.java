package com.sdia.twitterkafkaservice.init.impl;

import com.sdia.appconfigdata.config.KafkaConfigData;
import com.sdia.twitterkafkaservice.init.StreamInitializer ;
import com.sdia.kafkaadmin.client.KafkaAdminClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
@ComponentScan(basePackages = "com.sdia.kafkaadmin.client")
public class KafkaStreamInitializer implements StreamInitializer {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaStreamInitializer.class);

    private final KafkaConfigData kafkaConfigData;

    private final KafkaAdminClient kafkaAdminClient;


    public KafkaStreamInitializer(KafkaConfigData configData, KafkaAdminClient adminClient) {
        this.kafkaConfigData = configData;
        this.kafkaAdminClient = adminClient;
    }

    @Override
    public void init() {
        kafkaAdminClient.createTopics();
        kafkaAdminClient.checkSchemaRegistry();
        LOG.info("Topics with name {} is ready for operations!", kafkaConfigData.getTopicNamesToCreate().toArray());
    }
}
