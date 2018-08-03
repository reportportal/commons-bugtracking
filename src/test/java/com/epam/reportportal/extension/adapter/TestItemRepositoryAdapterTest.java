package com.epam.reportportal.extension.adapter;

import com.epam.ta.reportportal.entity.item.TestItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.List;

import static com.epam.reportportal.extension.constants.RabbitConstants.QueueNames.TEST_ITEMS_FIND_ONE_QUEUE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestItemRepositoryAdapterTest {

	@Mock
	private RabbitTemplate rabbitTemplate;

	@InjectMocks
	private TestItemRepositoryAdapter testItemRepositoryAdapter;

	@Test
	public void testFindOne() {

		//given:
		Long itemId = 1L;
		TestItem testItem = new TestItem();

		//setup:
		when(rabbitTemplate.convertSendAndReceiveAsType(any(), eq(itemId), any())).thenReturn(testItem);

		//when:
		testItemRepositoryAdapter.findOne(itemId);

		//then:
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

		verify(rabbitTemplate).convertSendAndReceiveAsType(captor.capture(), captor.capture(), any());

		List<String> capturedItems = captor.getAllValues();

		assertEquals(TEST_ITEMS_FIND_ONE_QUEUE, capturedItems.get(0));
		assertEquals(itemId, capturedItems.get(1));
	}
}