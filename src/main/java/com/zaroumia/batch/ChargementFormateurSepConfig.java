package com.zaroumia.batch;

import com.zaroumia.batch.domaine.Formateur;
import com.zaroumia.batch.listener.ChargementFormateurListener;
import com.zaroumia.batch.mapper.FormateurItemPreparedStatementSetter;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

import static com.zaroumia.batch.mapper.FormateurItemPreparedStatementSetter.FORMATEUR_INSERT_QUERY;

//https://copilot.microsoft.com/chats/Pxg6fkmCVbp8YLSyKwHdd
@Configuration
public class ChargementFormateurSepConfig {

    /**
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
    //Test ItemReader ok
//    @Bean
//    public ItemWriter<Formateur> formateurItemWriter() {
//        return (items) -> items.forEach(System.out::println);
//    }

    @Bean
    public JdbcBatchItemWriter<Formateur> formateurItemWriter(final DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Formateur>()
                .dataSource(dataSource)
                .sql(FORMATEUR_INSERT_QUERY)
                .itemPreparedStatementSetter(new FormateurItemPreparedStatementSetter())
                .build();
    }

//    @Bean
//    public Step chargementFormateurStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("chargementFormateurStep", jobRepository)
//                .<Formateur, Formateur>chunk(10, transactionManager)
//                .reader(formateurItemReader(null))
//                .writer(formateurItemWriter(null))
//                .build();
//    }

    //avec un Listener
//    @Bean
//    public Step chargementFormateurStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("chargementFormateurStep", jobRepository)
//                .<Formateur, Formateur>chunk(10, transactionManager)
//                .reader(formateurItemReader(null))
//                .writer(formateurItemWriter(null))
//                .listener(new ChargementFormateurListener())
//                .build();
//    }
    //version ameliorer
    @Bean
    @Qualifier("chargementFormateurListener")
    public StepExecutionListener chargementFormateurListener() {
        return new ChargementFormateurListener();
    }

    //avec un Listener
    @Bean
    public Step chargementFormateurStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("chargement-formateur-step", jobRepository)
                .<Formateur, Formateur>chunk(10, transactionManager)
                .reader(formateurItemReader(null))
                .writer(formateurItemWriter(null))
                .listener(chargementFormateurListener())
                .build();
    }
}
