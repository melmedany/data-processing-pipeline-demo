package io.falcon.data.processing.pipeline.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Application Entry point
 * 
 * @author medany
 */
@SpringBootApplication
public class Application {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		logger.info("----------------------------------------------------------------");
		logger.info("---------- Starting Data Processing Pipeline Demo App ----------");
		logger.info("----------------------------------------------------------------");
		new SpringApplicationBuilder(Application.class).properties("server.port=${other.port:9000}").run(args);
	}
}
