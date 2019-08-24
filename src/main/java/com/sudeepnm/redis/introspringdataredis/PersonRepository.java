package com.sudeepnm.redis.introspringdataredis;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends CrudRepository<Person, String> {
    List<Person> findByFirstname(String firstName);
    List<Person> findByLastname(String firstName);

}
