package com.example.sima.controller;

import com.example.sima.person.Person;
import com.example.sima.personId.PersonId; // Импортируйте ваш класс PersonId
import com.example.sima.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @PostMapping
    public Person createPerson(@RequestBody Person person) {
        return personRepository.save(person);
    }

    @GetMapping
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable PersonId id) {
        return personRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable PersonId id, @RequestBody Person personDetails) {
        return personRepository.findById(id)
                .map(person -> {
                    person.setName(personDetails.getName());
                    person.setSurname(personDetails.getSurname());
                    person.setCity(personDetails.getCity());
                    person.setAge(personDetails.getAge());
                    Person updatedPerson = personRepository.save(person);
                    return ResponseEntity.ok(updatedPerson);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePerson(@PathVariable PersonId id) {
        return personRepository.findById(id)
                .map(person -> {
                    personRepository.delete(person);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-city")
    public List<Person> getPersonsByCity(@RequestParam String city) {
        return personRepository.findByCity(city);
    }

    @GetMapping("/age/less-than/{age}")
    public List<Person> getPersonsYoungerThan(@PathVariable int age) {
        return personRepository.findByAgeLessThanOrderByAgeAsc(age);
    }

    @GetMapping("/name/{name}/surname/{surname}")
    public ResponseEntity<Person> getPersonByNameAndSurname(@PathVariable String name, @PathVariable String surname) {
        Optional<Person> person = personRepository.findByNameAndSurname(name, surname);
        return person.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
