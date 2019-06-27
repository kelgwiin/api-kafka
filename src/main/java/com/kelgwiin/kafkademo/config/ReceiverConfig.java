package com.kelgwiin.kafkademo.config;

import com.kelgwiin.kafkademo.consumer.Subscriber;
import com.kelgwiin.kafkademo.model.Customer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class ReceiverConfig {

    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "json");
        return props;
    }

    @Bean
    public ConsumerFactory<String, Customer> consumerFactory() {

        JsonDeserializer<Customer>  customerJsonDeserializer = new JsonDeserializer<>(Customer.class, false);
        customerJsonDeserializer.addTrustedPackages("*");// it's useful when your producer is in a different server

        return new DefaultKafkaConsumerFactory<>(consumerConfigs(), new StringDeserializer(), customerJsonDeserializer);

    }

    @Bean
    public StringJsonMessageConverter stringJsonMessageConverter(){
        return new StringJsonMessageConverter();
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Customer> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Customer> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public Subscriber subscriber() {
        return new Subscriber();
    }

}
