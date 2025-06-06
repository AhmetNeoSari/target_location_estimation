package com.example.centralunit.config;

import com.example.basedomain.config.TopicConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

public class TargetPositionTopicConfig implements TopicConfig {

    @Value("${spring.kafka.topic.produce.position.name}")
    private String topicName;

    @Bean(name = "CentralUnitTargetPositionInfo")
    @Override
    public NewTopic topic()
    {
        return TopicBuilder.name(topicName).build();
    }

}
