package com.epam.reportportal.extension.adapter;

import com.epam.ta.reportportal.entity.project.Project;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.List;

import static com.epam.reportportal.extension.constants.RabbitConstants.QueueNames.PROJECTS_FIND_BY_NAME;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProjectRepositoryAdapterTest {

	@Mock
	private RabbitTemplate rabbitTemplate;

	@InjectMocks
	private ProjectRepositoryAdapter projectRepositoryAdapter;

	@Test
	public void testFindOne() {

		//given:
		String projectName = "test";
		Project project = new Project();

		//setup:
		when(rabbitTemplate.convertSendAndReceiveAsType(any(), eq(projectName), any())).thenReturn(project);

		//when:
		projectRepositoryAdapter.findByName(projectName);

		//then:
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

		verify(rabbitTemplate).convertSendAndReceiveAsType(captor.capture(), captor.capture(), any());

		List<String> capturedItems = captor.getAllValues();

		assertEquals(PROJECTS_FIND_BY_NAME, capturedItems.get(0));
		assertEquals(projectName, capturedItems.get(1));
	}
}