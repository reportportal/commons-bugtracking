package com.epam.reportportal.extension.adapter;

import static com.epam.reportportal.extension.constants.RabbitConstants.QueueNames.PROJECTS_FIND_BY_NAME;

import com.epam.ta.reportportal.database.entity.Project;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectRepositoryAdapter {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public Project findByName(String projectName) {

        return (Project) rabbitTemplate.convertSendAndReceive(PROJECTS_FIND_BY_NAME, projectName);
    }
}
