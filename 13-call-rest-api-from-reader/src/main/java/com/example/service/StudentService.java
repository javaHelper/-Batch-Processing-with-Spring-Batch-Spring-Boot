package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.model.StudentResponse;

@Service
public class StudentService {

	private static final String URL = "http://localhost:8080/api/v1/students";
	private RestTemplate restTemplate = new RestTemplate();
	List<StudentResponse> studentResponses;

	public List<StudentResponse> restCallToGetStudents() {
		StudentResponse[] studentResponseArray = restTemplate.getForObject(URL, StudentResponse[].class);

		studentResponses = new ArrayList<>();

		for (StudentResponse sr : studentResponseArray) {
			studentResponses.add(sr);
		}

		return studentResponses;
	}

	public StudentResponse getStudent() {
		if (studentResponses == null) {
			restCallToGetStudents();
		}

		if (studentResponses != null && !studentResponses.isEmpty()) {
			return studentResponses.remove(0);
		}
		return null;
	}
}
