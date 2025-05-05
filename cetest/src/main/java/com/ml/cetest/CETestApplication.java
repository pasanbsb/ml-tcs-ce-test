package com.ml.cetest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@SpringBootApplication(scanBasePackages={
		"com.ml.cetest.sp500api", "com.ml.cetest.inventory"})
public class CETestApplication {
	private static final Logger logger = LoggerFactory.getLogger(CETestApplication.class);

	public static void main(String[] args) {
		logger.info("Starting ML-TCS CE Test Application");
		SpringApplication.run(CETestApplication.class, args);
	}
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
