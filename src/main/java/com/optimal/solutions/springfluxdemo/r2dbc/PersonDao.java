package com.optimal.solutions.springfluxdemo.r2dbc;


import static org.springframework.data.relational.core.query.Criteria.where;

import java.util.function.BiFunction;

import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Query;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;

import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//Reference: https://docs.spring.io/spring-data/r2dbc/docs/current-SNAPSHOT/reference/html/#reference

@Component
public class PersonDao {

    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final DatabaseClient databaseClient;

    public PersonDao(R2dbcEntityTemplate r2dbcEntityTemplate, DatabaseClient databaseClient) {
        this.r2dbcEntityTemplate = r2dbcEntityTemplate;
        this.databaseClient = databaseClient;
    }

    public Mono<Person> createPerson(Person person) {
        System.out.println("inside createPerson and it is: " + person);
        return r2dbcEntityTemplate.insert(Person.class).using(person);
    }

    public void deletePersonById(long id) {
    	r2dbcEntityTemplate.delete(Query.query(where("id").is(id)), Person.class);
    }

    public Mono<Person> findPerson(Long id) {
        System.out.println("inside findPerson: " + id);
        return r2dbcEntityTemplate.selectOne(Query.query(where("id").is(id)).limit(1),
                Person.class);
    }

    public Flux<Person> findAllPersons() {
        return r2dbcEntityTemplate.select(Person.class).all();
    }

    // databaseclient can do custom SQL query
    
    BiFunction<Row,RowMetadata, Person> MAPPING_FUNCTION =  ((row, rowMetaData) -> {
    	Person p = new Person();
    	p.setId((Integer) row.get("id"));
    	p.setLastname((String) row.get("lastname"));
    	p.setFirstname((String) row.get("firstname"));
    	p.setEmail((String) row.get("email"));
    	return p;
    });
    
    
    public Flux<Person> findPersonWithLastName(String lastname) {
        return this.databaseClient.sql("select * FROM person WHERE lastname=:lastname")
                .bind("lastname", lastname)
                .map(MAPPING_FUNCTION)
                .all();
    }


}