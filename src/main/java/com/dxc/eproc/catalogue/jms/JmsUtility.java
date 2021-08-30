package com.dxc.eproc.catalogue.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.dxc.eproc.catalogue.controller.CatalogueItemController;
import com.dxc.eproc.catalogue.repository.CatalogueRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class JmsUtility.
 */
@Component
public class JmsUtility implements CommandLineRunner {

	/** The jms template. */
	@Autowired
	public JmsTemplate jmsTemplate;

	/** The catalogue item controller. */
	@Autowired
	CatalogueItemController catalogueItemController;

	/** The catalogue repository. */
	@Autowired
	CatalogueRepository catalogueRepository;

	/**
	 * Run.
	 *
	 * @param strings the strings
	 * @throws Exception the exception
	 */
	@Override
	public void run(String... strings) throws Exception {

	}

	/**
	 * Send message.
	 *
	 * @param filename the filename
	 */
	public void sendMessage(String filename) {
		System.out.println(String.format("Sending '%s'", filename));
		this.jmsTemplate.convertAndSend("catalogue", filename);
	}

	/**
	 * Receive message.
	 *
	 * @param message the message
	 * @throws Exception the exception
	 */
	@JmsListener(destination = "catalogue")
	public void receiveMessage(String message) throws Exception {
		System.out.println(String.format("Received '%s'", message));
		catalogueItemController.uploadCatalogueFileProcess(message);
	}

}
