package com.zaroumia.batch.listener;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class ChargementFormateurListener implements StepExecutionListener {
    private  static final Logger LOGGER = LoggerFactory.getLogger(ChargementFormateurListener.class);

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {

        if("COMPLETED".equals(stepExecution.getExitStatus().getExitCode())){
            LOGGER.info("chargement des formateurs: {} formateur(s) unregister(s)",stepExecution.getReadCount());
        }
        return stepExecution.getExitStatus();
    }
}
