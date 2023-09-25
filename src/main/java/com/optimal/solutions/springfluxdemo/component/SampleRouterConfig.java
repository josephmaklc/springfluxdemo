package com.optimal.solutions.springfluxdemo.component;

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

import io.swagger.v3.oas.annotations.Operation;

@Configuration
public class SampleRouterConfig {

	@RouterOperations({
	    @RouterOperation(path = "/helloWorld"
	            , produces = {
	            MediaType.TEXT_PLAIN_VALUE}, 
	            method = RequestMethod.GET, 
	            beanClass = SampleHandler.class, beanMethod = "helloWorld",
	            operation = @Operation(operationId = "helloWorld")
	        ),
	    @RouterOperation(path = "/helloWorld2"
        , produces = {
        MediaType.TEXT_PLAIN_VALUE}, 
        method = RequestMethod.GET, 
        beanClass = SampleHandler.class, beanMethod = "helloWorld2",
        operation = @Operation(operationId = "helloWorld2"))
	})

	@Bean
	public RouterFunction<ServerResponse> routeHelloWorld(SampleHandler helloWorldHandler) {

		return RouterFunctions.route(RequestPredicates.GET("/helloWorld")
                .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), helloWorldHandler::helloWorld)
				.andRoute(RequestPredicates.GET("/helloWorld2"), helloWorldHandler::helloWorld2);
	}
}
