package com.example.config;

import java.io.File;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.skip.AlwaysSkipItemSkipPolicy;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.example.listener.SkipListener;
import com.example.model.StudentCsv;
import com.example.model.StudentJson;
import com.example.processor.FirstItemProcessor;
import com.example.reader.FirstItemReader;
import com.example.writer.FirstItemWriter;

@Configuration
public class SampleJob {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private FirstItemReader firstItemReader;

	@Autowired
	private FirstItemProcessor firstItemProcessor;

	@Autowired
	private FirstItemWriter firstItemWriter;

	@Autowired
	private SkipListener skipListener;

	@Bean
	public Job chunkJob() {
		return jobBuilderFactory.get("Chunk Job")
				.incrementer(new RunIdIncrementer())
				.start(firstChunkStep())
				.build();
	}

	@Bean
	public Step firstChunkStep() {
		return stepBuilderFactory.get("First Chunk Step")
				.<StudentCsv, StudentJson>chunk(3)
				.reader(flatFileItemReader())
				.processor(firstItemProcessor)
				.writer(jsonFileItemWriter())
				.faultTolerant()
				.skip(Throwable.class)
				.skipPolicy(new AlwaysSkipItemSkipPolicy())
				.listener(skipListener)
				.build();
	}

	@Bean
	public FlatFileItemReader<StudentCsv> flatFileItemReader() {
		FlatFileItemReader<StudentCsv> flatFileItemReader = new FlatFileItemReader<StudentCsv>();

		flatFileItemReader.setResource(new FileSystemResource(new File("students.csv")));

		flatFileItemReader.setLineMapper(new DefaultLineMapper<StudentCsv>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setNames("ID", "First Name", "Last Name", "Email");
					}
				});

				setFieldSetMapper(new BeanWrapperFieldSetMapper<StudentCsv>() {
					{
						setTargetType(StudentCsv.class);
					}
				});

			}
		});
		flatFileItemReader.setLinesToSkip(1);
		return flatFileItemReader;
	}



	@Bean
	public JsonFileItemWriter<StudentJson> jsonFileItemWriter() {
		FileSystemResource fileSystemResource = new FileSystemResource(new File("outputFiles/students.json"));

		JsonFileItemWriter<StudentJson> jsonFileItemWriter = new JsonFileItemWriter<>(fileSystemResource, 
				new JacksonJsonObjectMarshaller<StudentJson>()) {

			@Override
			public String doWrite(List<? extends StudentJson> items) {
				items.stream().forEach(e -> {
					if(e.getId() == 3) {
						throw new NullPointerException();
					}
				});
				return super.doWrite(items);
			}

		};

		return jsonFileItemWriter;
	}
}
