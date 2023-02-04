package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.request.JobParamsRequest;

@RestController
@RequestMapping("/api/job")
public class JobController {
	
	@Autowired
	JobLauncher jobLauncher;
	
	@Qualifier("firstJob")
	@Autowired
	Job firstJob;
	
	@Qualifier("secondJob")
	@Autowired
	Job secondJob;

	@GetMapping("/start/{jobName}")
	public String startJob(@PathVariable String jobName,
							@RequestBody List<JobParamsRequest> jobParamsRequests) throws Exception {
		
		Map<String, JobParameter> params = new HashMap<>();
		params.put("currentTime", new JobParameter(System.currentTimeMillis()));
		
		JobParameters jobParameters = new JobParameters(params);
		
		if(jobName.equals("First Job")) {
			jobLauncher.run(firstJob, jobParameters);
		} else if(jobName.equals("Second Job")) {
			jobLauncher.run(secondJob, jobParameters);
		}
		
		return "Job Started...";
	}
}
