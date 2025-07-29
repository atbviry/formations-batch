package com.zaroumia.batch;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * https://copilot.microsoft.com/chats/xGfuBavG6eJjP1cBi4iHk
 * Tester DefaultJobParametersValidator dans une application Spring Boot avec un main
 *
 * Tu peux tester le validateur directement dans ton application Spring Boot en lançant le job depuis la méthode main ou via CommandLineRunner. Voici un exemple complet et fonctionnel 👇
 */

//@SpringBootApplication
//@EnableBatchProcessing //on l'active ici juste pour la création de la metadata
public class FormationsBatchApplication_Discarerd implements CommandLineRunner {
	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job monJob;

	public static void main(String[] args) {
		SpringApplication.run(FormationsBatchApplication_Discarerd.class, args);
		//System.out.println("Bonjour BAH");
	}

	@Override
	public void run(String... args) throws Exception {
		JobParameters params = new JobParametersBuilder()
				.addString("inputFile", "data.csv")
				.addString("runDate", "2025-07-28")
				.addLong("time",System.currentTimeMillis())
				.toJobParameters();

		try {
			JobExecution execution = jobLauncher.run(monJob, params);
			System.out.println("Exit Status : " + execution.getStatus());
		} catch (JobParametersInvalidException e) {
			System.err.println("❌ Paramètres invalides : " + e.getMessage());
		}
	}
}
