package com.example.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.listener.FirstJobListener;
import com.example.listener.FirstStepListener;
import com.example.tasklet.ThirdTasklet;

@Configuration
public class SampleJob {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	@Autowired
	private ThirdTasklet thirdTasklet;
	@Autowired
	private FirstJobListener firstJobListener;
	@Autowired
	private FirstStepListener firstStepListener;
	
	@Bean
	public Job firstJob() {
		return jobBuilderFactory.get("First Job")
				.incrementer(new RunIdIncrementer())
				.start(firstStep())
				.next(secondStep())
				.next(thirdStep())
				.listener(firstJobListener)
				.build();

	}
	
	
	@Bean
	public Step firstStep() {
		return stepBuilderFactory.get("First Step")
				.tasklet(firstTasklet())
				.build();
				
	}
	
	@Bean
	public Step secondStep() {
		return stepBuilderFactory.get("Second Step")
				.tasklet(secondTasklet())
				.listener(firstStepListener)
				.build();
				
	}
	
	@Bean
	public Step thirdStep() {
		return stepBuilderFactory.get("Third Step")
				.tasklet(thirdTasklet)
				.build();
				
	}
	
	
	private Tasklet firstTasklet() {
		return new Tasklet() {
			
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("This is first tasklet step");
				System.out.println("SEC = "+ chunkContext.getStepContext().getJobExecutionContext());
				return RepeatStatus.FINISHED;
			}
		};
	}
	
	private Tasklet secondTasklet() {
		return new Tasklet() {
			
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("This is second tasklet step");
				return RepeatStatus.FINISHED;
			}
		};
	}
}
