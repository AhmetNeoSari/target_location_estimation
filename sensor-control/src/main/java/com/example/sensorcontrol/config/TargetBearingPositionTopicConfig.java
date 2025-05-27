package com.example.sensorcontrol.config;

import com.example.basedomain.config.TopicConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TargetBearingPositionTopicConfig implements TopicConfig {
    @Value("${spring.kafka.topic.produce.position.info.name}")
    private String topicName;

    @Bean(name = "targetBearingTopic")
    @Override
    public NewTopic topic()
    {
        return TopicBuilder.name(topicName).build();
    }

}
