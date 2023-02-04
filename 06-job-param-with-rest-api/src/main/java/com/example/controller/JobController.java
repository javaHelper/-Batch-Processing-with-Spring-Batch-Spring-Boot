package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.request.JobParamsRequest;
import com.example.service.JobService;

@RestController
@RequestMapping("/api/job")
public class JobController {
	
	@Autowired
	JobService jobService;

	@PostMapping("/start/{jobName}")
	public String startJob(@PathVariable String jobName, 
			@RequestBody List<JobParamsRequest> jobParamsRequestList) throws Exception {
		jobService.startJob(jobName, jobParamsRequestList);
		return "Job Started...";
	}
}
