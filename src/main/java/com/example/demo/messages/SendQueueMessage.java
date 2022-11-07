package com.example.demo.messages;

import com.example.demo.person.data.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class SendQueueMessage {

    static final String topicExchangeName = "personCreationTopicExchange";

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired(required = false)
    private final RabbitTemplate rabbitTemplate;

    public SendQueueMessage(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(Person person) throws JsonProcessingException {
        System.out.println("Sending message from RabbitMQ: " + person);
        rabbitTemplate.convertAndSend(topicExchangeName, person.getName(), objectMapper.writeValueAsString(person));
    }
}
