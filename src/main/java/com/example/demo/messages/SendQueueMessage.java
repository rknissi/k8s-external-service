package com.example.demo.messages;

import com.example.demo.person.data.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class SendQueueMessage {

    static final String topicExchangeName = "personCreationTopicExchange";
    static final String directExchangeName = "personCreationDirectExchange";
    static final String headerExchangeName = "personCreationHeaderExchange";

    static final String fanoutExchangeName = "personCreationFanoutExchange";

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired(required = false)
    private final RabbitTemplate rabbitTemplate;

    public SendQueueMessage(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(Person person, String type, String routingkey, String header1, String header2) throws JsonProcessingException {
        if (type.equals("topic")) {
            System.out.println("Sending message from RabbitMQ (topic): " + person);
            rabbitTemplate.convertAndSend(topicExchangeName, routingkey, objectMapper.writeValueAsString(person));
        }
        else if (type.equals("direct")) {
            System.out.println("Sending message from RabbitMQ (Direct): " + person);
            rabbitTemplate.convertAndSend(directExchangeName, routingkey, objectMapper.writeValueAsString(person));
        }
        else if (type.equals("headers")) {
            System.out.println("Sending message from RabbitMQ (Headers): " + person);

            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setHeader("exchangeHeader1", header1);
            messageProperties.setHeader("exchangeHeader2", header2);
            MessageConverter messageConverter = new SimpleMessageConverter();
            Message message = messageConverter.toMessage(objectMapper.writeValueAsString(person), messageProperties);
            rabbitTemplate.send(headerExchangeName, "", message);
        }
        else if (type.equals("fanout")) {
            System.out.println("Sending message from RabbitMQ (Fanout): " + person);
            rabbitTemplate.convertAndSend(fanoutExchangeName, routingkey, objectMapper.writeValueAsString(person));
        }
    }
}
