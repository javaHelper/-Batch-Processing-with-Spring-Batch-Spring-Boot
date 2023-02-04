package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentJdbc {
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
}
