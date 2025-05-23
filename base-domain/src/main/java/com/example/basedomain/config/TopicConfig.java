package com.example.basedomain.config;

import org.apache.kafka.clients.admin.NewTopic;

public interface TopicConfig {

    public NewTopic topic();
}
