package com.zaroumia.batch.validators;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.util.StringUtils;

public class MyJobParametersValidator implements JobParametersValidator {
    @Override
    public void validate(JobParameters parameters) throws JobParametersInvalidException {
        if ((parameters.getString("formateursFile") == null)
                && (parameters.getString("formationsFile") == null)
                && (parameters.getString("formationsFile") == null)) {
            throw new JobParametersInvalidException("Un fichier  est obligatoire pour demarrer le Batch.");
        }

        if(!StringUtils.endsWithIgnoreCase(parameters.getString("formateursFile"),"csv")) {
            throw  new JobParametersInvalidException("Le Fichier formateurs doit être au format CSV");
        }

        if(!StringUtils.endsWithIgnoreCase(parameters.getString("formationsFile"),"xml")) {
            throw  new JobParametersInvalidException("Le Fichier formations doit être au format XML");
        }

        if(!StringUtils.endsWithIgnoreCase(parameters.getString("seancesFile"),"csv") &&
                !StringUtils.endsWithIgnoreCase(parameters.getString("seancesFile"),"txt")) {
            throw  new JobParametersInvalidException("Le Fichier séances doit être au format CSV");
        }
    }
}
