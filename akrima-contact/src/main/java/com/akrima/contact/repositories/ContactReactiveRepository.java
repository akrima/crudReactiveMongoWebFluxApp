package com.akrima.contact.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.Param;

import com.akrima.contact.entities.Contact;

import reactor.core.publisher.Flux;

public interface ContactReactiveRepository extends ReactiveMongoRepository<Contact, String>{

	@Query(value = "{'name': {$regex : ?0, $options: 'i'}}") // pour dire que le name like %value% et order by and pageable
	public Flux<Contact> findAllByKeyName(@Param("keyName") String keyName, final Pageable pageable);
	
	public Flux<Contact> findBySalary(Double salary, final Pageable pageable);
	
}
