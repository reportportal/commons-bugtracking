package com.epam.reportportal.extension.adapter;

import com.epam.ta.reportportal.entity.integration.Integration;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.epam.reportportal.extension.constants.RabbitConstants.QueueNames.INTEGRATION_FIND_ONE;

@Component
public class IntegrationRepositoryAdapter {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public IntegrationRepositoryAdapter(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public Integration findOne(String systemId) {
        return (Integration) rabbitTemplate.convertSendAndReceive(INTEGRATION_FIND_ONE, systemId);
    }
}
