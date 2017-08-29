package com.reactor.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoProcessor;

@SpringBootApplication
public class ReactorExampleApplication {

	static final Logger LOG = LoggerFactory.getLogger(ReactorExampleApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ReactorExampleApplication.class, args);
	}
}
