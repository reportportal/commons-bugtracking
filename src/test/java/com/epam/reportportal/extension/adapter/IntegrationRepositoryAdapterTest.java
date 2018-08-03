package com.epam.reportportal.extension.adapter;

import com.epam.ta.reportportal.entity.integration.Integration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.List;

import static com.epam.reportportal.extension.constants.RabbitConstants.QueueNames.INTEGRATION_FIND_ONE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IntegrationRepositoryAdapterTest {

	@Mock
	private RabbitTemplate rabbitTemplate;

	@InjectMocks
	private IntegrationRepositoryAdapter integrationRepositoryAdapter;

	@Test
	public void testFindOne() {

		//given:
		Long systemId = 1L;
		Integration externalSystem = new Integration();

		//setup:
		when(rabbitTemplate.convertSendAndReceiveAsType(any(), eq(systemId), any())).thenReturn(externalSystem);

		//when:
		integrationRepositoryAdapter.findOne(systemId);

		//then:
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

		verify(rabbitTemplate).convertSendAndReceiveAsType(captor.capture(), captor.capture(), any());

		List<String> capturedItems = captor.getAllValues();

		assertEquals(INTEGRATION_FIND_ONE, capturedItems.get(0));
		assertEquals(systemId, capturedItems.get(1));
	}

}