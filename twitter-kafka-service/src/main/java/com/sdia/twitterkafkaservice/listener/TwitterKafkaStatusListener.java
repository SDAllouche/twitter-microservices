package com.sdia.twitterkafkaservice.listener;

import com.sdia.appconfigdata.config.KafkaConfigData;
import com.sdia.kafkamodel.avro.model.TwitterAvroModel;
import com.sdia.kafkaproducer.config.service.KafkaProducer;
import com.sdia.twitterkafkaservice.transformer.TwitterStatusToAvroTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import twitter4j.Status;
import twitter4j.StatusAdapter;

@Component
@ComponentScan(basePackages = "com.sdia.appconfigdata.config")
@ComponentScan(basePackages = "com.sdia.kafkaproducer.config.service")
public class TwitterKafkaStatusListener extends StatusAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterKafkaStatusListener.class);

    private final KafkaConfigData kafkaConfigData;

    private final KafkaProducer<Long, TwitterAvroModel> kafkaProducer;

    private final TwitterStatusToAvroTransformer twitterStatusToAvroTransformer;



    public TwitterKafkaStatusListener(KafkaConfigData configData,
                                      KafkaProducer<Long, TwitterAvroModel> producer,
                                      TwitterStatusToAvroTransformer transformer) {
        this.kafkaConfigData = configData;
        this.kafkaProducer = producer;
        this.twitterStatusToAvroTransformer = transformer;
    }

    @Override
    public void onStatus(Status status) {
        LOG.info("Received status text {} sending to kafka topic {}", status.getText(), kafkaConfigData.getTopicName());
        TwitterAvroModel twitterAvroModel = twitterStatusToAvroTransformer.getTwitterAvroModelFromStatus(status);
        kafkaProducer.send(kafkaConfigData.getTopicName(), twitterAvroModel.getUserId(), twitterAvroModel);
    }
}
