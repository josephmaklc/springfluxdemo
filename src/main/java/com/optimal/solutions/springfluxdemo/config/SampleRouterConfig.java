package com.optimal.solutions.springfluxdemo.config;

import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.optimal.solutions.springfluxdemo.component.SampleHandler;
import com.optimal.solutions.springfluxdemo.r2dbc.Person;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

/**
 * This is a Reactive Spring router config
 */
@Configuration
public class SampleRouterConfig {

	
	@RouterOperations({
			@RouterOperation(path = "/router-path/sayHello", produces = {
					MediaType.TEXT_PLAIN_VALUE }, 
					method = RequestMethod.GET, 
					beanClass = SampleHandler.class, 
					beanMethod = "helloWorld", operation = @Operation(operationId = "helloWorld")),
			@RouterOperation(path = "/router-path/person/{id}", produces = {
					MediaType.APPLICATION_JSON_VALUE },
					method = RequestMethod.GET, 
					beanClass = SampleHandler.class, 
					beanMethod = "lookupPerson",
					operation = @Operation(operationId = "lookupPerson",
					        summary = "lookup a person",
					        parameters = {
					                @Parameter(in = ParameterIn.PATH,
					                        name = "id",
					                        schema = @Schema(type = "integer"), 
					                        description = "id of person",
					                        required = true),
					        })
					),
			@RouterOperation(path = "/router-path/person", produces = {
					MediaType.APPLICATION_JSON_VALUE },
					method = RequestMethod.POST, 
					beanClass = SampleHandler.class, 
					beanMethod = "addPerson",
					operation = @Operation(operationId = "addPerson",
					        summary = "add a person",
					        requestBody = 
					                @RequestBody(description="omit the id field",
					                			 content=@Content(schema=@Schema(implementation=Person.class)), 
					                			 required=true)
					))
			})
	

	@Bean
	public RouterFunction<ServerResponse> routeHelloWorld(SampleHandler helloWorldHandler) {

		return RouterFunctions.route(RequestPredicates.GET("/router-path/sayHello")
                .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), helloWorldHandler::helloWorld)
				.andRoute(RequestPredicates.GET("/router-path/person/{id}"), helloWorldHandler::lookupPerson)
				.andRoute(RequestPredicates.POST("/router-path/person"), helloWorldHandler::addPerson);
	}
}
