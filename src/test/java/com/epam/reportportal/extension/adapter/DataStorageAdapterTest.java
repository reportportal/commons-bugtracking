package com.epam.reportportal.extension.adapter;

import com.epam.ta.reportportal.BinaryData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.List;

import static com.epam.reportportal.extension.constants.RabbitConstants.QueueNames.DATA_STORAGE_FETCH_DATA_QUEUE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DataStorageAdapterTest {

	@Mock
	private RabbitTemplate rabbitTemplate;

	@InjectMocks
	private DataStorageAdapter dataStorageAdapter;

	@Test
	public void testFetchData() {

		//given:
		String dataId = "test";
		BinaryData data = new BinaryData(null, null, null);

		//setup:
		when(rabbitTemplate.convertSendAndReceiveAsType(any(), eq(dataId), any())).thenReturn(data);

		//when:
		dataStorageAdapter.fetchData(dataId);

		//then:
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

		verify(rabbitTemplate).convertSendAndReceiveAsType(captor.capture(), captor.capture(), any());

		List<String> capturedItems = captor.getAllValues();

		assertEquals(DATA_STORAGE_FETCH_DATA_QUEUE, capturedItems.get(0));
		assertEquals(dataId, capturedItems.get(1));
	}
}