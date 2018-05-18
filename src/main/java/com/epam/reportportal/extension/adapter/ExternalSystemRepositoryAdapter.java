package com.epam.reportportal.extension.adapter;

import static com.epam.reportportal.extension.constants.RabbitConstants.QueueNames.EXTERNAL_SYSTEMS_FIND_ONE;

import com.epam.ta.reportportal.database.entity.ExternalSystem;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExternalSystemRepositoryAdapter {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public ExternalSystem findOne(String systemId) {

        return (ExternalSystem) rabbitTemplate.convertSendAndReceive(EXTERNAL_SYSTEMS_FIND_ONE, systemId);
    }
}
