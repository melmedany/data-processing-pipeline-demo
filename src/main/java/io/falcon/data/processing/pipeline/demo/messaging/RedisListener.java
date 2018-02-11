package io.falcon.data.processing.pipeline.demo.messaging;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import io.falcon.data.processing.pipeline.demo.messaging.repository.MessageRepository;

/**
 * @author medany
 */
@Component
public class RedisListener implements MessageListener {

	private static final Logger logger = LoggerFactory.getLogger(RedisListener.class);

	/**
	 * WebSocket instance
	 */
	@Autowired
	private SimpMessagingTemplate socket;

	/**
	 * Messaging repository instance
	 */
	@Autowired
	private MessageRepository repo;

	/**
	 * Persists it in the database, then push it through WebSocket for listening
	 * browsers
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void onMessage(final org.springframework.data.redis.connection.Message message, final byte[] pattern) {

		logger.info("---------- Received new Message ----------");
		Message content = new Message(message.toString());
		content.setDate(new Date());
		logger.info("---------- Persisting to Database ----------");
		try {
			content = repo.save(content);
			logger.info("---------- ID:" + content.getId() + "----------");
			logger.info("---------- Notify listeners ----------");
			socket.convertAndSend("/topic/messages", new Object[] { content });
		} catch (Exception e) {
			socket.convertAndSend("/topic/messages", new Object[] { "Error Persisting Message" });
			logger.info("---------- Error Persisting Message ----------", e);
		}
	}
}
