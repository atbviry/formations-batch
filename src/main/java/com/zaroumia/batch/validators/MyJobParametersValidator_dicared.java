package com.zaroumia.batch.validators;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;

public class MyJobParametersValidator_dicared implements JobParametersValidator {
    @Override
    public void validate(JobParameters parameters) throws JobParametersInvalidException {
        String file = parameters.getString("inputFile");
        String date = parameters.getString("runDate");
        if (file == null || !file.endsWith(".csv")) {
            throw new JobParametersInvalidException("Le fichier doit être un .csv");
        }

        if (date == null || !date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new JobParametersInvalidException("La date doit être au format YYYY-MM-DD");
        }
    }
    /**
     *
     * @param parameters some {@link JobParameters} (can be {@code null})
     * @throws JobParametersInvalidException
     */
//    @Override
//    public void validate(JobParameters parameters) throws JobParametersInvalidException {
//        if (parameters.getString("fichier") == null) {
//            throw new JobParametersInvalidException("Le paramètre 'fichier' est obligatoire pour traiter les données.");
//        }
//        if(parameters == null){
//            throw new JobParametersInvalidException("Le paramètre est obligatoire");
//        }
//        if(!StringUtils.endsWithIgnoreCase(parameters.getString("formateursFile"),"csv")) {
//            throw  new JobParametersInvalidException("Le Fichier formateurs doit être au format CSV");
//        }
//
//        if(!StringUtils.endsWithIgnoreCase(parameters.getString("formationsFile"),"xml")) {
//            throw  new JobParametersInvalidException("Le Fichier formations doit être au format XML");
//        }
//
//        if(!StringUtils.endsWithIgnoreCase(parameters.getString("seancesFile"),"csv")) {
//            throw  new JobParametersInvalidException("Le Fichier séances doit être au format CSV");
//        }
//
//    }
}
