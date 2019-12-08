package com.akrima.contact;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.akrima.contact.entities.Contact;
import com.akrima.contact.repositories.ContactReactiveRepository;
import com.akrima.contact.utils.RandomUtil;

@SpringBootApplication
public class AkrimaContactApplication {

	public static void main(String[] args) {
		SpringApplication.run(AkrimaContactApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner run(ContactReactiveRepository contactReactiveRepository) {
		return args->{
			Stream<Contact> contacts = Stream.generate(()-> Contact.builder().name(RandomUtil.getRandomName()).salary(RandomUtil.getRandomSalary()).birthDate(RandomUtil.getRandomDate()).build()).limit(700);
			contactReactiveRepository.saveAll(contacts.collect(Collectors.toList())).subscribe(System.out::println);
		};
	}
	
}
