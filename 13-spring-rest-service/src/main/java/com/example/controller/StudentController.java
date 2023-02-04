package com.example.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.StudentResponse;
import com.github.javafaker.Faker;

@RestController
@RequestMapping("/api/v1")
public class StudentController {
	
	private static final Faker FAKER = new Faker();

	@GetMapping("/students")
	public List<StudentResponse> students() {
		List<StudentResponse> studentResponses = LongStream.range(1, 100)
				.boxed()	
				.map(e -> new StudentResponse(e, FAKER.name().firstName(), FAKER.name().lastName(), FAKER.name().username()+"@gmail.com"))
				.collect(Collectors.toList());


//				return Arrays.asList(
//						new StudentResponse(1L, "John", "Smith", "john@gmail.com"),
//						new StudentResponse(2L, "Sachin", "Dave", "sachin@gmail.com"),
//						new StudentResponse(3L, "Peter", "Mark", "peter@gmail.com"),
//						new StudentResponse(4L, "Martin", "Smith", "martin@gmail.com"),
//						new StudentResponse(5L, "Raj", "Patel", "raj@gmail.com"),
//						new StudentResponse(6L, "Virat", "Yadav", "virat@gmail.com"),
//						new StudentResponse(7L, "Prabhas", "Shirke", "prabhas@gmail.com"),
//						new StudentResponse(8L, "Tina", "Kapoor", "tina@gmail.com"),
//						new StudentResponse(9L, "Mona", "Sharma", "mona@gmail.com"),
//						new StudentResponse(10L, "Rahul", "Varma", "rahul@gmail.com"));
		return studentResponses;
	}
}
