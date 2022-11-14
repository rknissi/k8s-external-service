package com.example.demo.person.endpoint;

import com.example.demo.messages.KafkaMessageProducer;
import com.example.demo.messages.SendQueueMessage;
import com.example.demo.person.application.PersonApplication;
import com.example.demo.person.data.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Profile("!test")
public class PersonQueueEndpoint {

    @Autowired(required = false)
    private SendQueueMessage sendQueueMessage;

    @Autowired
    private KafkaMessageProducer kafkaMessageProducer;

    @PostMapping("/personRabbit")
    @ResponseBody
    public ResponseEntity<Person> createPersonOnQueue (@RequestBody Person person, @RequestHeader("X-Type") String type, @RequestHeader("X-Routing-Key") String routingKey, @RequestHeader("X-Header1") String header1, @RequestHeader("X-Header2") String header2) throws JsonProcessingException {
        sendQueueMessage.sendMessage(person, type, routingKey, header1, header2);
        return ResponseEntity.status(202).build();
    }

    @PostMapping("/personKafka")
    @ResponseBody
    public ResponseEntity<Person> createPersonOnKafkaQueue (@RequestBody Person person) throws JsonProcessingException {
        kafkaMessageProducer.sendMessage(person);
        return ResponseEntity.status(202).build();
    }

    @PostMapping("/personKafkaError")
    @ResponseBody
    public ResponseEntity<Person> createPersonOnKafkaQueueWithError (@RequestBody Person person) throws JsonProcessingException {
        kafkaMessageProducer.sendMessageError(person);
        return ResponseEntity.status(202).build();
    }
}
