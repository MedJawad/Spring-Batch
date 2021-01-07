package com.ensa.trigger;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

//@Component
@RestController
public class Trigger {
    private final JobLauncher jobLauncher;
    private final Job job;

    public Trigger(JobLauncher jobLauncher, Job job) {
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    @GetMapping(path = "/run")
    public void onApplicationEvent() {
        Map<String, JobParameter> maps = new HashMap<>();
        maps.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters parameters = new JobParameters(maps);
        JobExecution jobExecution = null;
        try {
            jobExecution = jobLauncher.run(job, parameters);
            while (jobExecution.isRunning()) {
                System.out.println("Loading...");
            }
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException | JobParametersInvalidException | JobRestartException e) {
            e.printStackTrace();
        } finally {
            assert jobExecution != null;
            System.out.println(jobExecution.getStatus().name());
        }
    }
}