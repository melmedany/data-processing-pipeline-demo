package io.falcon.data.processing.pipeline.demo.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

/**
 * @author medany
 */
@Component
public class RedisProducer implements MessageProducer {

	private static final Logger logger = LoggerFactory.getLogger(RedisProducer.class);

	/**
	 * Redis template
	 */
	@Autowired
	private RedisTemplate<String, Object> redis;

	/**
	 * 
	 */
	@Autowired
	private ChannelTopic topic;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void produce(String content) {

		logger.info("---------- Producing new message ----------");
		redis.convertAndSend(topic.getTopic(), content);
	}
}