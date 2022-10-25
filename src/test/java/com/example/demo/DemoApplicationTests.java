package com.example.demo;

import com.example.demo.person.data.Person;
import com.example.demo.person.repository.PersonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles(profiles = "test")
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private MockMvc mvc;

	private ObjectMapper objectMapper = new ObjectMapper();

	@MockBean
	private PersonRepository personRepository;

	@Test
	void checkIfSystemIsOn() throws Exception {
		mvc.perform(get("/check")).andExpect(status().isOk());
	}

	@Test
	void check200IfPersonExists() throws Exception {
		Person person = new Person();
		person.setId(1l);
		person.setAge(10l);
		person.setName("name");

		when(personRepository.getPerson(anyLong())).thenReturn(person);
		mvc.perform(get("/person/1")).andExpect(status().isOk());
	}

	@Test
	void check201IfPersonCreated() throws Exception {
		Person person = new Person();
		person.setId(1l);
		person.setAge(10l);
		person.setName("name");

		when(personRepository.createPerson(any(Person.class))).thenReturn(person);
		mvc.perform(post("/person").contentType("application/json").content(objectMapper.writeValueAsString(person)))
				.andExpect(status().isCreated());
	}

	@Test
	void check404IfPersonDoesNotExists() throws Exception {
		mvc.perform(get("/person/1")).andExpect(status().isNotFound());
	}

}
