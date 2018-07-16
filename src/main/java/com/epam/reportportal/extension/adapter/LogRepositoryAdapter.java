package com.epam.reportportal.extension.adapter;

import com.epam.ta.reportportal.entity.log.Log;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.epam.reportportal.extension.constants.RabbitConstants.MessageHeaders.*;
import static com.epam.reportportal.extension.constants.RabbitConstants.QueueNames.LOGS_FIND_BY_TEST_ITEM_REF_QUEUE;

@Component
public class LogRepositoryAdapter {

	private final RabbitTemplate rabbitTemplate;

	@Autowired
	public LogRepositoryAdapter(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	public List<Log> findByTestItemRef(String itemRef, Integer limit, Boolean isLoadBinaryData) {

		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setHeader(ITEM_REF, itemRef);
		messageProperties.setHeader(LIMIT, limit);
		messageProperties.setHeader(IS_LOAD_BINARY_DATA, isLoadBinaryData);

		return (List<Log>) rabbitTemplate.convertSendAndReceive(LOGS_FIND_BY_TEST_ITEM_REF_QUEUE, new Message(null, messageProperties));
	}
}
