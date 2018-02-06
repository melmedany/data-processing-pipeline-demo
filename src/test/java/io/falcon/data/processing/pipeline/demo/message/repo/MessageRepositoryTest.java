package io.falcon.data.processing.pipeline.demo.message.repo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
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

	@Autowired
	private MessageRepository repo;

	@Before
	public void setUp() throws Exception {
		repo.deleteAll();
	}

	@Test
	public void messageCreateRertieveTest() throws Exception {
		Message actual = new Message("sample message");
		actual = repo.save(actual);
		Message expected = repo.findOne(actual.getId());
		assertThat(actual.toString()).isEqualTo(expected.toString());
	}
}
