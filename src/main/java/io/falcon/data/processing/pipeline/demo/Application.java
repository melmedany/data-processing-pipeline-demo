package io.falcon.data.processing.pipeline.demo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		new SpringApplicationBuilder(Application.class).properties("server.port=${other.port:9000}").run(args);
	}
}
