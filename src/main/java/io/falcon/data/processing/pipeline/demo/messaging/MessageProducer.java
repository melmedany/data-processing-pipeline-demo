package io.falcon.data.processing.pipeline.demo.messaging;

/**
 * @author medany
 */

public interface MessageProducer {

	/**
	 * 
	 * @param message
	 *            messaage to produce
	 */
	public void produce(String message);
}
