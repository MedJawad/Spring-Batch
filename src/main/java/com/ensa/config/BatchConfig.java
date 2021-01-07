package com.ensa.config;

import com.ensa.beans.Transaction;
import com.ensa.beans.TransactionDTO;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DBProcessor dbProcessor;
    private final DBWriter dbWriter;
    @Value("${transactionsFile}")
    String pathToFile;

    public BatchConfig(DBWriter dbWriter, DBProcessor dbProcessor, JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.dbWriter = dbWriter;
        this.dbProcessor = dbProcessor;
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Job readCSVFileJob() {
        return jobBuilderFactory
                .get("readCSVFileJob")
                .incrementer(new RunIdIncrementer())
                .start(step())
                .build();
    }

    @Bean
    public Step step() {
        return stepBuilderFactory
                .get("step")
                .<TransactionDTO, Transaction>chunk(5)
                .reader(reader())
                .processor(dbProcessor)
                .writer(dbWriter)
                .build();
    }

    @Bean
    public FlatFileItemReader<TransactionDTO> reader() {
        FlatFileItemReader<TransactionDTO> itemReader = new FlatFileItemReader<>();
        itemReader.setLineMapper(lineMapper());
        itemReader.setLinesToSkip(1);
        itemReader.setResource(new PathResource(pathToFile));
        return itemReader;

    }

    @Bean
    public LineMapper<TransactionDTO> lineMapper() {
        DefaultLineMapper<TransactionDTO> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("idTransaction", "idCompte", "montant", "dateTransaction");
        BeanWrapperFieldSetMapper<TransactionDTO> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(TransactionDTO.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

}