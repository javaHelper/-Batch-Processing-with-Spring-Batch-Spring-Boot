package com.example.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class FirstJobListener implements JobExecutionListener {

	@Override
	public void beforeJob(JobExecution jobExecution) {
		System.out.println("# Before Job : "+ jobExecution.getJobInstance().getJobName());
		System.out.println("# Job Params : "+ jobExecution.getJobParameters());
		System.out.println("# Job Execution Context : "+ jobExecution.getExecutionContext());
		
		// Lets put some value 
		jobExecution.getExecutionContext().put("jec", "jec value");
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		System.out.println("# After Job : "+ jobExecution.getJobInstance().getJobName());
		System.out.println("# Job Params : "+ jobExecution.getJobParameters());
		System.out.println("# Job Execution Context : "+ jobExecution.getExecutionContext());
	}

}
