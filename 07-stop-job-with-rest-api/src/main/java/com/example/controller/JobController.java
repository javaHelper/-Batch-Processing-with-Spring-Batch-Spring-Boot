package com.example.controller;

import java.util.List;

import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@Autowired
	JobOperator jobOperator;

	@GetMapping("/start/{jobName}")
	public String startJob(@PathVariable String jobName, 
			@RequestBody List<JobParamsRequest> jobParamsRequestList) throws Exception {
		jobService.startJob(jobName, jobParamsRequestList);
		return "Job Started...";
	}
	
	@GetMapping("/stop/{jobExecutionId}")
	public String stopJob(@PathVariable long jobExecutionId) {
		try {
			jobOperator.stop(jobExecutionId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Job Stopped...";
	}
}
