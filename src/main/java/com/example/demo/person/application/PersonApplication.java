package com.example.demo.person.application;

import com.example.demo.person.data.Person;
import com.example.demo.person.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.net.ConnectException;

@Service
public class PersonApplication {

    @Autowired
    private PersonRepository personRepository;

    public Person getPerson(Long id) throws HttpServerErrorException, ResourceAccessException {
        return personRepository.getPerson(id);
    }

    public Person createPerson(Person person) {
        return personRepository.createPerson(person);
    }
}
