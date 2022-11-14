package com.example.demo.messages;

import com.example.demo.person.data.Person;
import com.example.demo.person.data.PersonAvro;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@Profile("!test")
public class KafkaMessageProducer {
    @Autowired
    private KafkaTemplate<PersonAvro, PersonAvro> kafkaTemplate;

    ObjectMapper objectMapper = new ObjectMapper();

    public void sendMessage(Person person) throws JsonProcessingException {
        System.out.println("Sending message from Kafka: " + person);
        PersonAvro personAvro = new PersonAvro();
        personAvro.setName(person.getName());
        personAvro.setAge(person.getAge());
        ListenableFuture<SendResult<PersonAvro, PersonAvro>> future = kafkaTemplate.send("personCreation", personAvro);

        future.addCallback(new ListenableFutureCallback<SendResult<PersonAvro, PersonAvro>>() {

            @Override
            public void onSuccess(SendResult<PersonAvro, PersonAvro> result) {
                System.out.println("Sent message=[" + person +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");

            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message=["
                        + person + "] due to : " + ex.getMessage());
            }
        });
    }

    public void sendMessageError(Person person) throws JsonProcessingException {
        System.out.println("Sending message from Kafka: " + person);
        PersonAvro personAvro = new PersonAvro();
        personAvro.setName(person.getName());
        personAvro.setAge(person.getAge());
        ListenableFuture<SendResult<PersonAvro, PersonAvro>> future = kafkaTemplate.send("personCreationError", personAvro);
    }
}
