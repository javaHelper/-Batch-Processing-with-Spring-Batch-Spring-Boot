package com.example.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.example.processor.FirstItemProcessor;
import com.example.reader.FirstItemReader;
import com.example.writer.FirstItemWriter;

@Component
public class FirstJob {
	@Autowired
	private FirstItemReader firstItemReader;
	@Autowired
	private FirstItemProcessor firstItemProcessor;
	@Autowired
	private FirstItemWriter firstItemWriter;
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job firstChunkJob() {
		return jobBuilderFactory.get("First Chunk Job")
				.incrementer(new RunIdIncrementer())
				.start(firstChunkStep())
				.build();
	}
	
	@Bean
	public Step firstChunkStep() {
		return stepBuilderFactory.get("First Chunk Step")
				.<Integer, Long>chunk(3)
				.reader(firstItemReader)
				.processor(firstItemProcessor)
				.writer(firstItemWriter)
				.build();
				
	}
}
