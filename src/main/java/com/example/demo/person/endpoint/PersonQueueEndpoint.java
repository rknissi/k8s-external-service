package com.example.demo.person.endpoint;

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

    @PostMapping("/personQueue")
    @ResponseBody
    public ResponseEntity<Person> createPersonOnQueue (@RequestBody Person person) throws JsonProcessingException {
        sendQueueMessage.sendMessage(person);
        return ResponseEntity.status(202).build();
    }
}
