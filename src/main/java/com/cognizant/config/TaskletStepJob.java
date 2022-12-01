package com.cognizant.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class TaskletStepJob {

//	@Autowired
//	private JobBuilderFactory jobBuilderFactory;
	
	@Bean
	@Primary
	public JobBuilderFactory getJobBuilderFactory(JobRepository jobRepository) {
		return new JobBuilderFactory( jobRepository);
	}

//	@Autowired
//	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	@Autowired
	public StepBuilderFactory getStepBuilderFactory(JobRepository jobRepository) {
		return new StepBuilderFactory(jobRepository);
	}

//	@Bean Annotation is applied on a method to specify that it returns a bean to be managed by Spring context. Spring Bean annotation is usually declared in Configuration classes methods. In this case, bean methods may reference other @Bean methods in the same class by calling them directly.
	@Bean
	public Job firstJob(JobBuilderFactory jobBuilderFactory,Step step) {
		return jobBuilderFactory.get("First Job")
				.start(step)
				.build();
	}

	@Bean
	public Step firstStep(StepBuilderFactory stepBuilderFactory,Tasklet tasklet) {
		return stepBuilderFactory.get("First Step")
				.tasklet(tasklet)
				.build();
	}

	@Bean
	public Tasklet firstTask() {
		return new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("This is first tasklet step");
				return RepeatStatus.FINISHED;
			}
		};
	}

}
