package com.test.msbatches;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/batch")
@RequiredArgsConstructor
public class OrderController {

    private final JobLauncher jobLauncher;

    private final Job job;


    //test api gateway
    @GetMapping("/alert/{msg}")
    public ResponseEntity<String> echo(@PathVariable String msg) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        jobLauncher.run(job, new JobParametersBuilder().toJobParameters());
        return new ResponseEntity<>("Your alert message, " + msg, HttpStatus.OK);
    }
    @Scheduled(fixedRate = 5000)
    private void jobRun() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        jobLauncher.run(job, new JobParametersBuilder().toJobParameters());
    }
}
