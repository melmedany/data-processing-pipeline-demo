package io.falcon.data.processing.pipeline.demo.messaging.service;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.falcon.data.processing.pipeline.demo.messaging.Message;
import io.falcon.data.processing.pipeline.demo.messaging.RedisProducer;
import io.falcon.data.processing.pipeline.demo.messaging.repository.MessageRepository;

/**
 * @author medany
 */
@Service
public class MessageService {

	private static final Logger logger = LoggerFactory.getLogger(MessageService.class);
	private static final ObjectMapper mapper = new ObjectMapper();

	/**
	 * Messaging repository instance
	 */
	@Autowired
	private MessageRepository repo;

	/**
	 * Redis Service
	 */
	@Autowired
	private RedisProducer redis;

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
		try {
			JsonNode root = parseJSON(jsonString);
			if (root != null) {
				redis.produce(root.toString());
				return true;
			}
		} catch (IOException e) {
			logger.error("---------- Error reading message JSON ----------", e);
		}
		return false;
	}

	/**
	 * Use jackson to validate a JSON String is only Key/Value pair objects
	 * 
	 * @param jsonString
	 *            input JSON String
	 * @return true if valid JSON, false if not
	 * @throws IOException
	 */
	private JsonNode parseJSON(String jsonString) throws IOException {
		JsonNode root = mapper.readTree(jsonString);
		if (root != null) {
			if (root.isArray()) // if array of key value pairs
				if (root.get(0) != null && root.get(0).fields().hasNext())
					return root;
			if (root.fields().hasNext()) // if key value pairs
				return root;
		}

		return null;
	}
}