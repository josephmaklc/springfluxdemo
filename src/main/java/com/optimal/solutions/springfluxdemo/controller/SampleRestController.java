package com.optimal.solutions.springfluxdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.optimal.solutions.springfluxdemo.r2dbc.Person;
import com.optimal.solutions.springfluxdemo.r2dbc.PersonDao;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * This is a traditional REST controller
 */
@RestController
public class SampleRestController {

    @GetMapping("sayHello")
    public String sayHello(@RequestParam String name) {
        return "Hello my friend "+name;
    }

	@Autowired
	PersonDao personDao;

	@GetMapping("/person/{id}")
    public Mono<Person> getPersonByID(@PathVariable long id) {

		Mono<Person> p = personDao.findPerson(id);
        return p;
    }

	@PostMapping("/person")
    public Mono<Person> addNewPerson(Person p) {
		System.out.println("adding: "+p.getFirstname()+" "+p.getLastname());
		return personDao.createPerson(p);
		
    }

	@GetMapping("/person/list")
	public Flux<Person> listPersons() {
        //Flux<Person> list = personDao.findPersonWithLastName("Williams");
    	Flux<Person> list = personDao.findAllPersons();
//        list.subscribe(e-> System.out.println(e));
        return list;
	}
}