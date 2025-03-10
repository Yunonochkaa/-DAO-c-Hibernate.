package com.example.sima.repository;

import com.example.sima.person.Person;
import com.example.sima.personId.PersonId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, PersonId> {
    List<Person> findByCityOfLiving(String city);
}
