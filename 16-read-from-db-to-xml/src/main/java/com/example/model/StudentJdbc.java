package com.example.model;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "student")
public class StudentJdbc {
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
}
