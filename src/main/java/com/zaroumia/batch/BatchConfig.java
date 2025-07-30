package com.zaroumia.batch;

import com.zaroumia.batch.tasklets.impl.HelloWordTasklet;
import com.zaroumia.batch.validators.MyJobParametersValidator;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.CompositeJobParametersValidator;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
    /**
     * Job Parameters
     * Connfiguration des parmétres en entrée de notre job
     *
     * @return bean
     */
    @Bean
    public JobParametersValidator defaultJobParametersValidator() {
        DefaultJobParametersValidator defaultValidator = new DefaultJobParametersValidator();
        defaultValidator.setRequiredKeys(new String[]{"formateursFile", "formationsFile", "seancesFile"});
        //defaultValidator.setOptionalKeys(new String[]{"run.id"}); pas de besoin  dans cette version,  j'arrive a le lancer au tant de fois que je veux sans exception already raidy exist.
        return defaultValidator;
    }

    @Bean
    MyJobParametersValidator myJobParametersValidator() {
        return new MyJobParametersValidator();
    }

//    @Bean
//    public JobParametersValidator compositeValidator() {
//        CompositeJobParametersValidator composite = new CompositeJobParametersValidator();
//        List<JobParametersValidator> validators = new ArrayList<>();
//        validators.add(defaultJobParametersValidator());
//        validators.add(myJobParametersValidator());
//        composite.setValidators(validators);
//        return composite;
//    }

    @Bean
    public JobParametersValidator compositeValidator() {
        CompositeJobParametersValidator composite = new CompositeJobParametersValidator();
        composite.setValidators(Arrays.asList(defaultJobParametersValidator(), myJobParametersValidator()));
        return composite;
    }

//    @Bean
//    public HelloWordTasklet helloWordTasklet() {
//        return new HelloWordTasklet();
//    }

//    @Bean
//    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("step1", jobRepository)
//                .tasklet(helloWordTasklet(), transactionManager)
//                .build();
//    }

//    @Bean
//    public Job helloWordJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new JobBuilder("hello-word-job", jobRepository)
//                .validator(compositeValidator())
//                .start(step1(jobRepository, transactionManager))
//                //.incrementer(new RunIdIncrementer()) //pas de besoin  dans cette version
//                .build();
//    }

    @Bean
    public Job formationsBatch(JobRepository jobRepository, Step chargementFormateurStep) {
        return new JobBuilder("formations-batch", jobRepository)
                .validator(compositeValidator())
                .start(chargementFormateurStep)
                .build();
    }
}
