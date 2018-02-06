package io.falcon.data.processing.pipeline.demo.endpoint;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.falcon.data.processing.pipeline.demo.messaging.Message;
import io.falcon.data.processing.pipeline.demo.messaging.service.MessageService;

/**
 * REST Endpoint controller
 * 
 * @author medany
 */
@RestController
@RequestMapping(value = "/endpoint")
public class EndpointController {

	private static final Logger logger = LoggerFactory.getLogger(EndpointController.class);

	/**
	 * Messaging service instance
	 */
	@Autowired
	private MessageService service;

	/**
	 * Handler method to GET Message objects
	 * 
	 * @return All database persisted Message objects
	 */
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<Message> getAll() {
		logger.info("---------- Finding all persisted messages ----------");
		return service.findAll();
	}

	/**
	 * Handler method to POST Message object
	 * 
	 * @param json
	 *            String JSON formated to
	 * @return CREATED if created, CONFLICT otherwise
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Void> post(@RequestBody String json) {
		logger.info("---------- Start creating new message ----------");
		if (service.post(json.toString()))
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		else
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
	}
}
