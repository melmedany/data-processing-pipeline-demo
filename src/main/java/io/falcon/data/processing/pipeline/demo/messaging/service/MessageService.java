package io.falcon.data.processing.pipeline.demo.messaging.service;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.falcon.data.processing.pipeline.demo.messaging.Message;
import io.falcon.data.processing.pipeline.demo.messaging.repository.MessageRepository;

/**
 * @author medany
 */
@Service
public class MessageService {

	private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

	/**
	 * Messaging repository instance
	 */
	@Autowired
	private MessageRepository repo;

	/**
	 * Redis template
	 */
	@Autowired
	private RedisTemplate<String, Object> redis;

	/**
	 * Find all database persisted Message objects
	 * 
	 * @return All database persisted Message objects in descending order
	 */
	public List<Message> findAll() {
		logger.info("---------- Query database for all persisted messages in descending order ----------");
		return repo.findAllByOrderByDateDesc();
	}

	/**
	 * Puts String JSON formated Message on Redis
	 * 
	 * @param jsonString
	 *            JSON String to put on Redis
	 */
	public boolean post(String jsonString) {
		boolean valid = false;
		try {
			valid = isJSON(jsonString);
			redis.convertAndSend("rest-payload", jsonString);
		} catch (IOException e) {
			logger.error("---------- Error reading message JSON ----------", e);
		}
		return valid;
	}

	/**
	 * Use Jackson to validate a JSON String
	 * 
	 * @param jsonString
	 *            input JSON String
	 * @return true if valid JSON, false if not
	 * @throws JsonParseException
	 * @throws IOException
	 */
	private boolean isJSON(String jsonString) throws JsonParseException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode actualObj = mapper.readTree(jsonString);
		return actualObj != null;
	}

}
