package com.epam.reportportal.extension.adapter;

import static com.epam.reportportal.extension.constants.RabbitConstants.QueueNames.EXTERNAL_SYSTEMS_FIND_ONE;

import com.epam.ta.reportportal.entity.integration.Integration;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IntegrationRepositoryAdapter {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public IntegrationRepositoryAdapter(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public Integration findOne(String systemId) {
        return (Integration) rabbitTemplate.convertSendAndReceive(EXTERNAL_SYSTEMS_FIND_ONE, systemId);
    }
}
