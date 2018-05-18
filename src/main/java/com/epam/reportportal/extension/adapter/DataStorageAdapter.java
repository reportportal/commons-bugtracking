package com.epam.reportportal.extension.adapter;

import static com.epam.reportportal.extension.constants.RabbitConstants.QueueNames.DATA_STORAGE_FETCH_DATA_QUEUE;

import com.epam.ta.reportportal.database.BinaryData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataStorageAdapter {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public BinaryData fetchData(String dataId) {

        return (BinaryData) rabbitTemplate.convertSendAndReceive(DATA_STORAGE_FETCH_DATA_QUEUE, dataId);
    }
}
