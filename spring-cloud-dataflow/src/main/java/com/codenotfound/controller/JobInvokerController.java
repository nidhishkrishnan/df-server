package com.codenotfound.controller;

import com.codenotfound.config.BatchConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.ListableJobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class JobInvokerController {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    private BatchConfig batchConfig;

    @Autowired
    public JobRepository jobRepository;

    @Autowired
    ListableJobLocator jobLocator;



    @Scheduled(fixedDelay = 5000)
    public String handle() throws Exception {

        JobParameters jobParameters1 = new JobParametersBuilder()
                .addLong("time",System.currentTimeMillis())
                .toJobParameters();

        JobParameters jobParameters2 = new JobParametersBuilder()
                .addLong("time",System.currentTimeMillis())
                .toJobParameters();

        JobExecution jobExecution = jobLauncher.run(batchConfig.getJob("job1"), jobParameters1);
        jobLauncher.run(batchConfig.getJob("job2"), jobParameters2);

        jobLocator.getJobNames();
        return "Batch job has been invoked";
    }
}
