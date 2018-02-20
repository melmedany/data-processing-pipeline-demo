package io.falcon.data.processing.pipeline.demo.endpoint;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import io.falcon.data.processing.pipeline.demo.messaging.Message;
import io.falcon.data.processing.pipeline.demo.messaging.repository.MessageRepository;

/**
 * @author medany
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EndpointTest {

	private static final Message actual = new Message(EndpointTest.class.getSimpleName());

	@Autowired
	private MessageRepository repo;

	@Autowired
	private MockMvc endpoint;

	@Test
	public void getMessageTest() throws Exception {
		int entityNumber = repo.findAll().size();

		endpoint.perform(get("/endpoint").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(entityNumber)));
	}

	@Test
	public void postMessageTest() throws Exception {
		endpoint.perform(post("/endpoint").contentType(MediaType.APPLICATION_JSON).content(actual.toString()))
				.andExpect(status().isAccepted());

		Thread.sleep(2000); // allow time to persist message

		repo.delete(repo.findByContent(actual.toString()));
	}

	@Test
	public void postWrongJSONMessageTest() throws Exception {
		endpoint.perform(post("/endpoint").contentType(MediaType.APPLICATION_JSON)
				.content("\"" + EndpointTest.class.getSimpleName() + "\"")).andExpect(status().isUnprocessableEntity());
	}
}
