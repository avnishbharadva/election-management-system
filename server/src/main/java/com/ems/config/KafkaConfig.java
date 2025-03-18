package com.ems.config;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.Map;

@Configuration
public class KafkaConfig {  //this is class to create new topic in kafka server
    @Bean
    NewTopic createTopic(){
        return TopicBuilder.name("update-voter-events-topic")
                .partitions(3)    //this is to define partition for higher throughput no of services will consume the message then we will hava difine that number of partitions
                .replicas(1)    //this is for backup means in this number of broker our partition data will be backup number of brokker
                .configs(Map.ofEntries(
                        Map.entry("min.insync.replicas", "1") // this is the config to say that in number of replicas the backup will be done
                ))
                .build();
    }
    @Bean
    NewTopic addressupdateTopic(){
        return TopicBuilder.name("address-update-event-topic")
                .partitions(3)
                .replicas(1)
                .configs(Map.ofEntries(Map.entry("min.insync.replicas","1")))
                .build();
    }
    @Bean
    NewTopic emailSendEventTopic(){
        return TopicBuilder.name("email-send-event-topic")
                .partitions(3)
                .replicas(1)
                .configs(Map.ofEntries(Map.entry("min.insync.replicas","1")))
                .build();
    }
}