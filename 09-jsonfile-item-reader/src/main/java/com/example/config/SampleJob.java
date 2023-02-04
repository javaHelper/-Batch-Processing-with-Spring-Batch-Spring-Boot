package com.example.config;

import java.io.File;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.example.model.StudentJson;
import com.example.writer.FirstItemWriter;

@Configuration
public class SampleJob {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private FirstItemWriter firstItemWriter;

	@Bean
	public Job chunkJob() {
		return jobBuilderFactory.get("Chunk Job")
				.incrementer(new RunIdIncrementer())
				.start(firstChunkStep())
				.build();
	}

	private Step firstChunkStep() {
		return stepBuilderFactory.get("First Chunk Step")
				.<StudentJson, StudentJson>chunk(3)
				.reader(jsonItemReader())
				.writer(firstItemWriter)
				.build();
	}

	@StepScope
	@Bean
	public JsonItemReader<StudentJson> jsonItemReader() {
		JsonItemReader<StudentJson> jsonItemReader = new JsonItemReader<StudentJson>();

		jsonItemReader.setResource(new FileSystemResource(new File("students.json")));
		jsonItemReader.setJsonObjectReader(new JacksonJsonObjectReader<>(StudentJson.class));

//		jsonItemReader.setMaxItemCount(8);
//		jsonItemReader.setCurrentItemCount(2);

		return jsonItemReader;
	}
}
