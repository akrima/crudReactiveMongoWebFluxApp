package com.akrima.contact.entities;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Contact implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
	private String name;
	
	private Double salary;
	
	private Date birthDate;

}
