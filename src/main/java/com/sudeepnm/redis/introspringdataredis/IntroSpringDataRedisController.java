package com.sudeepnm.redis.introspringdataredis;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class IntroSpringDataRedisController {

    private PersonRepository personRepository;

    public IntroSpringDataRedisController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping("/persons")
    public ResponseEntity<Iterable<Person>> personList() {
        Iterable<Person> allRecords = personRepository.findAll();
        return ResponseEntity.ok(allRecords);
    }

    @GetMapping("/personsByFirstName/{firstName}")
    public ResponseEntity<Iterable<Person>> personsByFirstName(@PathVariable String firstName) {
        List<Person> personList = personRepository.findByFirstname(firstName);
        return ResponseEntity.ok(personList);
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestParam String firstName, @RequestParam String lastName) {
        if(firstName == null) {
            return ResponseEntity.badRequest().build();
        }
        Person person = Person.builder()
                .firstname(firstName)
                .lastname(lastName)
                .address(Address.builder()
                        .street("street")
                        .city("city").build()
                ).build();
        Person savedPerson = personRepository.save(person);
        return ResponseEntity.ok(savedPerson.getId());
    }
}
