package com.example.demo.person.repository;

import com.example.demo.person.data.Person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.net.ConnectException;
import java.net.SocketException;

@Repository
public class PersonRepository {

    private RestTemplate restTemplate;

    @Value("${testh2.url}")
    private String TESTH2_URL;

    @Value("${testh2.port}")
    private String TESTH2_PORT;

    public PersonRepository(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public Person getPerson(Long id) throws HttpServerErrorException, ResourceAccessException {
        try {
            ResponseEntity<Person> personResponseEntity = restTemplate.getForEntity(TESTH2_URL + ":" + TESTH2_PORT + "/person/" + id,  Person.class);
            if (personResponseEntity.getStatusCode() == HttpStatus.OK) {
                return personResponseEntity.getBody();
            }
            return null;
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return null;
            } else {
                throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    public Person createPerson(Person person){
        return restTemplate.postForObject(TESTH2_URL + ":" + TESTH2_PORT + "/person", person, Person.class);
    }
}
