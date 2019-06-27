package com.kelgwiin.kafkademo.consumer;

import com.kelgwiin.kafkademo.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

public class Subscriber {

    private final Logger logger = LoggerFactory.getLogger(java.util.function.Consumer.class);
    @KafkaListener(topics = "customers-02", groupId = "group_id", containerFactory  = "kafkaListenerContainerFactory")
    public void consume(Customer message){
        logger.info(String.format("$$ -> Consumed Message -> %s",message.toString()));

    }
}
