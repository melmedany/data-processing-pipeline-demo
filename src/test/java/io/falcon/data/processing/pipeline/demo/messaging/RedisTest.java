package io.falcon.data.processing.pipeline.demo.messaging;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.falcon.data.processing.pipeline.demo.messaging.repository.MessageRepository;

/**
 * @author medany
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RedisTest {

	private static final Message actual = new Message(RedisTest.class.getSimpleName());
	private static List<Message> expected;

	@Autowired
	private RedisProducer redis;

	@Autowired
	private MessageRepository repo;


	@Test
	public void produceConsumeMessageTest() throws Exception {
		actual.setId(10000L);
		actual.setDate(new Date());
		redis.produce(actual.toString());

		Thread.sleep(2000); // allow time to persist produces message when received

		expected = repo.findByContent(actual.toString());

		assertThat(expected).isNotEqualTo(null);
		assertThat(actual.toString()).isEqualTo(expected.get(0).getContent());

		repo.delete(expected);
	}
}
