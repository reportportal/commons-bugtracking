package com.epam.reportportal.extension.adapter;

import static com.epam.reportportal.extension.constants.RabbitConstants.MessageHeaders.IS_LOAD_BINARY_DATA;
import static com.epam.reportportal.extension.constants.RabbitConstants.MessageHeaders.ITEM_REF;
import static com.epam.reportportal.extension.constants.RabbitConstants.MessageHeaders.LIMIT;
import static com.epam.reportportal.extension.constants.RabbitConstants.QueueNames.LOGS_FIND_BY_TEST_ITEM_REF_QUEUE;

import com.epam.ta.reportportal.database.entity.Log;
import java.util.List;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LogRepositoryAdapter {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public List<Log> findByTestItemRef(String itemRef, Integer limit, Boolean isLoadBinaryData) {

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader(ITEM_REF, itemRef);
        messageProperties.setHeader(LIMIT, limit);
        messageProperties.setHeader(IS_LOAD_BINARY_DATA, isLoadBinaryData);

        return (List<Log>) rabbitTemplate.convertSendAndReceive(LOGS_FIND_BY_TEST_ITEM_REF_QUEUE,
                new Message(null, messageProperties));
    }
}
