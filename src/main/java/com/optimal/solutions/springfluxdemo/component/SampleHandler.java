package com.optimal.solutions.springfluxdemo.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.optimal.solutions.springfluxdemo.r2dbc.Person;
import com.optimal.solutions.springfluxdemo.r2dbc.PersonDao;

import reactor.core.publisher.Mono;

/**
 * This is a handler object for handling Router Config methods
 */
@Component
public class SampleHandler {

	public Mono<ServerResponse> helloWorld(ServerRequest request) {
		
		return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
				.body(Mono.just("Hello World"),String.class);
	}

	@Autowired
	PersonDao personDao;
	
	public Mono<ServerResponse> lookupPerson(ServerRequest request) {
		int personId = Integer.valueOf(request.pathVariable("id"));
		
		Mono<Person> p = personDao.findPerson(personId);
		
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
				.body(p,Person.class);
	}
	
	public Mono<ServerResponse> addPerson(ServerRequest request) {
		
		Mono<Person> p = request.bodyToMono(Person.class);
		Mono<Person> q = p.flatMap(e->{
			return personDao.createPerson(e);
		});
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
				.body(q,Person.class);
	}

}
