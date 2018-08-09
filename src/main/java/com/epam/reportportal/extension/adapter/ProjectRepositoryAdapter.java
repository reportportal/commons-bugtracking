package com.epam.reportportal.extension.adapter;

import com.epam.ta.reportportal.ws.model.project.ProjectResource;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
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

	public ProjectResource findByName(String projectName) {
		return rabbitTemplate.convertSendAndReceiveAsType(
				PROJECTS_FIND_BY_NAME,
				projectName,
				new ParameterizedTypeReference<ProjectResource>() {
				}
		);
	}
}
