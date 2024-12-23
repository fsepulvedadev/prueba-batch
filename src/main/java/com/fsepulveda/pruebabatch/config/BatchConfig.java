package com.fsepulveda.pruebabatch.config;

import com.fsepulveda.pruebabatch.domain.PuntoDeVentaPrepago;
import com.fsepulveda.pruebabatch.reader.PuntoDeVentaReader;
import com.fsepulveda.pruebabatch.writer.PuntoDeVentaWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Autowired
    public BatchConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }

    @Bean
    public PuntoDeVentaReader excelItemReader() {
        return new PuntoDeVentaReader("input/puntos_retiro_CONDATOS.xlsx");
    }

    @Bean
    public PuntoDeVentaWriter datoItemWriter() {
        return new PuntoDeVentaWriter();
    }

    @Bean
    public Job importJob() {
        return new JobBuilder("importJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return new StepBuilder("step1")
                .<PuntoDeVentaPrepago, PuntoDeVentaPrepago>chunk(10)
                .reader(excelItemReader())
                .writer(datoItemWriter())
                .allowStartIfComplete(true)
                .build();
    }
}