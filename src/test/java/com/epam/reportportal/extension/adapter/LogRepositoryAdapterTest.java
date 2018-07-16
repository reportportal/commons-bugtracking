package com.epam.reportportal.extension.adapter;

import static com.epam.reportportal.extension.constants.RabbitConstants.QueueNames.LOGS_FIND_BY_TEST_ITEM_REF_QUEUE;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@RunWith(MockitoJUnitRunner.class)
public class LogRepositoryAdapterTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private LogRepositoryAdapter logRepositoryAdapter;

    @Test
    public void testFindByTestItemRef() {

        //given:
        String itemRef = "ref";
        int limit = 3;
        boolean isLoadBinaryData = true;

        //setup:
        when(rabbitTemplate.convertSendAndReceive(any(), (Object) any())).thenReturn(new ArrayList<>());

        //when:
        logRepositoryAdapter.findByTestItemRef(itemRef, limit, isLoadBinaryData);

        //then:
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        verify(rabbitTemplate).convertSendAndReceive(captor.capture(), (Object) any());

        String capturedQueueName = captor.getValue();

        assertEquals(LOGS_FIND_BY_TEST_ITEM_REF_QUEUE, capturedQueueName);
    }
}