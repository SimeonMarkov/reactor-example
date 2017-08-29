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

		MonoProcessor<String> promise = MonoProcessor.create();

		Mono<String> result = promise.doOnSuccess(p -> LOG.info("Promise completed {}", p))
				.doOnTerminate((s, e) -> LOG.info("Got value: {}", s))
				.doOnError(t -> LOG.error(t.getMessage(), t))
				.subscribe();

		promise.onNext("Hello World!");
		//promise.onError(new IllegalArgumentException("Hello Shmello! :P"));

		String s = result.blockMillis(1_000);
		LOG.info("s={}", s);
	}
}
