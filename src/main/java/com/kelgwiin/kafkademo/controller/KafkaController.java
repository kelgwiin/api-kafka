package com.kelgwiin.kafkademo.controller;

import com.kelgwiin.kafkademo.model.Message;
import com.kelgwiin.kafkademo.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    @Autowired
    private Producer producer;

    @PostMapping("/publish")
    public void publish(@RequestBody Message message) {
        producer.sendMessage(message);
    }

}
