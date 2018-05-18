package com.epam.reportportal.extension.adapter;

import static com.epam.reportportal.extension.constants.RabbitConstants.QueueNames.TEST_ITEMS_FIND_ONE_QUEUE;

import com.epam.ta.reportportal.database.entity.item.TestItem;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestItemRepositoryAdapter {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public TestItem findOne(String itemId) {

        return (TestItem) rabbitTemplate.convertSendAndReceive(TEST_ITEMS_FIND_ONE_QUEUE, itemId);
    }
}
