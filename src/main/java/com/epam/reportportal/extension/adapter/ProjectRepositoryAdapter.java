package com.epam.reportportal.extension.adapter;

import com.epam.ta.reportportal.entity.project.Project;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.epam.reportportal.extension.constants.RabbitConstants.QueueNames.PROJECTS_FIND_BY_NAME;

/**
 * @author Andrei Varabyeu
 */
@Component
public class ProjectRepositoryAdapter {

	private final RabbitTemplate rabbitTemplate;

	@Autowired
	public ProjectRepositoryAdapter(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	public Project findByName(String projectName) {
		return (Project) rabbitTemplate.convertSendAndReceive(PROJECTS_FIND_BY_NAME, projectName);
	}
}