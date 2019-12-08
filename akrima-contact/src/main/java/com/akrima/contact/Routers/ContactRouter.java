package com.akrima.contact.Routers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.akrima.contact.handlers.ContactHandler;

/**
 * C'est une classe qui ressemble a un restcontroller
 * 
 * @author Abderrahim
 *
 */

@Configuration
public class ContactRouter {
	
	@Bean
	public RouterFunction<ServerResponse> route(ContactHandler contactHandler){
		return RouterFunctions.route()
				.GET("contact/all", contactHandler::findAll)
				.GET("contact/{id}", contactHandler::findById)
				.GET("contact", contactHandler::findByKeyName)
				.GET("contact/salary/{salary}", contactHandler::findBySalary)
				.POST("contact", contactHandler::post)
				.PUT("contact", contactHandler::put)
				.DELETE("contact/{id}", contactHandler::delete)
				.build();
	}

}
