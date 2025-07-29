package com.zaroumia.batch;

import com.zaroumia.batch.tasklets.impl.HelloWordTasklet;
import com.zaroumia.batch.validators.MyJobParametersValidator;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.CompositeJobParametersValidator;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;

//@Configuration
//@EnableBatchProcessing
public class BatchConfig_bad {
    /**
     * Job Parameters
     * Connfiguration des parmétres en entrée de notre job
     *
     * @return bean
     */

    @Bean
    public JobParametersValidator defaultJobParametersValidator() {
        DefaultJobParametersValidator bean = new DefaultJobParametersValidator();
        bean.setRequiredKeys(new String[]{"formateursFile", "formationsFile", "seancesFile"});
        return bean;
    }

    //là j'ai un bean de type MyJobParametersValidator()
    @Bean
    public MyJobParametersValidator myJobParametersValidator() {
        return new MyJobParametersValidator();
    }
        public JobParametersValidator validator() {
        CompositeJobParametersValidator bean = new CompositeJobParametersValidator();
        bean.setValidators(Arrays.asList(defautltValidator(), myJobParametersValidator()));
        return bean;
    }

    public JobParametersValidator defautltValidator() {
        DefaultJobParametersValidator defautltValidator = new DefaultJobParametersValidator();
        defautltValidator.setRequiredKeys(new String[] { "inputFile", "runDate" });
        defautltValidator.setOptionalKeys(new String[] { "user", "mode" });
        return defautltValidator;
    }


    @Bean
public HelloWordTasklet helloWordTasklet () {
        return new HelloWordTasklet();
}
    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {

        return new StepBuilder("step1", jobRepository)
                .tasklet(helloWordTasklet(), transactionManager)
                .build();
    }

@Bean
    public Job helloWordJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("hello-word-job", jobRepository)
                .validator(validator())
                .start(step1(jobRepository, transactionManager))
                .build();
    }
}
