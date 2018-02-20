package io.falcon.data.processing.pipeline.demo.messaging.repo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.falcon.data.processing.pipeline.demo.messaging.Message;
import io.falcon.data.processing.pipeline.demo.messaging.repository.MessageRepository;

/**
 * @author medany
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MessageRepositoryTest {

	private static Message actual = new Message(MessageRepositoryTest.class.getSimpleName());
	private static Message expected;

	@Autowired
	private MessageRepository repo;

	@Test
	public void messageCreateRertieveTest() throws Exception {
		actual = repo.save(actual);
		expected = repo.findOne(actual.getId());

		assertThat(expected).isNotEqualTo(null);
		assertThat(actual.toString()).isEqualTo(expected.toString());

		repo.delete(actual.getId());
	}
}
