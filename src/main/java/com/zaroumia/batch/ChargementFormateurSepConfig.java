package com.zaroumia.batch;

import com.zaroumia.batch.domaine.Formateur;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;

//https://copilot.microsoft.com/chats/Pxg6fkmCVbp8YLSyKwHdd
@Configuration
public class ChargementFormateurSepConfig {

    /**
     *
     * @param inputFile
     * @return on doit retourner l'inmplemenataion au de l'interafce sinon on aura l'exception Reader must be open before it can be read.
     */

//    @Bean
//    @StepScope
//    public FlatFileItemReader<Formateur> formateurItemReader(@Value("#{jobParameters['formateursFile']}") String inputFile) {
//        return new FlatFileItemReaderBuilder<Formateur>()
//                .name("FormateurItem")
//                .resource(new PathResource(inputFile))
//                .delimited()
//                .delimiter(";")
//                .names(new String[]{"id", "nom", "prenom", "adresseEmail"})  // CSV column names
//                .targetType(Formateur.class) // Map each line to MyObject
//                .build();
//    }

    @Bean
    @StepScope
    public FlatFileItemReader<Formateur> formateurItemReader(@Value("#{jobParameters['formateursFile']}") Resource inputFile) {
        return new FlatFileItemReaderBuilder<Formateur>()
                .name("FormateurItem")
                .resource(inputFile)
                .delimited()
                .delimiter(";")
                .names(new String[]{"id", "nom", "prenom", "adresseEmail"})  // CSV column names
                .targetType(Formateur.class) // Map each line to MyObject
                .build();
    }

    //https://copilot.microsoft.com/chats/GLGeiJigNfSzZmEMTYuSr
//https://stackoverflow.com/questions/46403159/spring-batch-input-resource-must-exist-reader-is-in-strict-mode-error
    @Bean
    public ItemWriter<Formateur> formateurItemWriter() {
        return (items) -> items.forEach(System.out::println);
    }

    @Bean
    public Step chargementFormateurStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("chargementFormateurStep", jobRepository)
                .<Formateur, Formateur>chunk(10, transactionManager)
                .reader(formateurItemReader(null))
                .writer(formateurItemWriter())
                .build();
    }
}
