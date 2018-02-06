package io.falcon.data.processing.pipeline.demo.messaging.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.falcon.data.processing.pipeline.demo.messaging.Message;

/**
 * @author medany
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

	/**
	 * 
	 * @return All persisted messages. If no messages is found, this method returns
	 *         an empty list.
	 */
	public List<Message> findAllByOrderByDateDesc();
}
