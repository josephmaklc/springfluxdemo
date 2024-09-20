package com.optimal.solutions.springfluxdemo.r2dbc;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface PersonRepository extends ReactiveCrudRepository<Person, Integer> {

	Mono<Person> findOneById(int itemId);
	
    @Query("SELECT * FROM person WHERE lastname = :lastname")
    Flux<Person> findByLastNameCustomQuery(String lastName);

}