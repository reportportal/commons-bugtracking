package com.epam.reportportal.extension.adapter;

import com.epam.ta.reportportal.entity.item.TestItem;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.epam.reportportal.extension.constants.RabbitConstants.QueueNames.TEST_ITEMS_FIND_ONE_QUEUE;

/**
 * @author Andrei Varabyeu
 */
@Component
public class TestItemRepositoryAdapter {

	private final RabbitTemplate rabbitTemplate;

	@Autowired
	public TestItemRepositoryAdapter(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	public TestItem findOne(String itemId) {
		return (TestItem) rabbitTemplate.convertSendAndReceive(TEST_ITEMS_FIND_ONE_QUEUE, itemId);
	}
}
