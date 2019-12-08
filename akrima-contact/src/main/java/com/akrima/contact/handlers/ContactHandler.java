package com.akrima.contact.handlers;

import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.akrima.contact.entities.Contact;
import com.akrima.contact.repositories.ContactReactiveRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * C'est un handler ou je met toutes les methodes sur un contacts
 * 
 * @author Abderrahim
 *
 */

@Component
@RequiredArgsConstructor
public class ContactHandler {

	private final ContactReactiveRepository contactReactiveRepository;

	public Mono<ServerResponse> findAll(ServerRequest serverRequest) {
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromPublisher(contactReactiveRepository.findAll(), Contact.class));
	}

	public Mono<ServerResponse> findById(ServerRequest serverRequest) {
		String id = serverRequest.pathVariable("id");
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromPublisher(contactReactiveRepository.findById(id), Contact.class))
				.switchIfEmpty(ServerResponse.notFound().build());
	}

	public Mono<ServerResponse> findBySalary(ServerRequest serverRequest) {
		Double salary = Double.valueOf(serverRequest.pathVariable("salary"));
		Integer page = Integer.valueOf(serverRequest.queryParam("page").get());
		Integer size = Integer.valueOf(serverRequest.queryParam("size").get());
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromPublisher(contactReactiveRepository.findBySalary(salary, PageRequest.of(page, size)), Contact.class))
				.switchIfEmpty(ServerResponse.notFound().build());
	}

	public Mono<ServerResponse> findByKeyName(ServerRequest serverRequest) {
		Integer page = Integer.valueOf(serverRequest.queryParam("page").get());
		Integer size = Integer.valueOf(serverRequest.queryParam("size").get());
		String keyname = serverRequest.queryParam("keyname").get();
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromPublisher(
						contactReactiveRepository.findAllByKeyName(keyname, PageRequest.of(page, size)), Contact.class))
				.switchIfEmpty(ServerResponse.notFound().build());
	}

	public Mono<ServerResponse> post(ServerRequest serverRequest) {
		Mono<Contact> newContact = serverRequest.body(BodyExtractors.toMono(Contact.class));
		return ServerResponse.ok().body(newContact.flatMap(contactReactiveRepository::save), Contact.class);
	}

	public Mono<ServerResponse> put(ServerRequest serverRequest) {
		Mono<Contact> editedContact = serverRequest.bodyToMono(Contact.class);
		return editedContact.flatMap(edit -> {
			Mono<Contact> contactDb = contactReactiveRepository.findById(edit.getId());
			return contactDb
					.map(db -> contactReactiveRepository.save(Contact.builder().id(db.getId()).name(edit.getName())
							.salary(edit.getSalary()).birthDate(edit.getBirthDate()).build()))
					.flatMap(contact -> ServerResponse.ok().body(BodyInserters.fromPublisher(contact, Contact.class))
							.switchIfEmpty(ServerResponse.notFound().build()));
		});
	}

	public Mono<ServerResponse> delete(ServerRequest serverRequest) {
		String id = serverRequest.pathVariable("id");
		Mono<Contact> contactDb = contactReactiveRepository.findById(id);
		return contactDb.flatMap(contact -> ServerResponse.noContent().build(contactReactiveRepository.delete(contact)))
				.switchIfEmpty(ServerResponse.notFound().build());
	}

}
