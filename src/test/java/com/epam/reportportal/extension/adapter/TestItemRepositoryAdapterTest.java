package com.epam.reportportal.extension.adapter;

import static com.epam.reportportal.extension.constants.RabbitConstants.QueueNames.TEST_ITEMS_FIND_ONE_QUEUE;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.epam.ta.reportportal.database.entity.item.TestItem;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@RunWith(MockitoJUnitRunner.class)
public class TestItemRepositoryAdapterTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private TestItemRepositoryAdapter testItemRepositoryAdapter = new TestItemRepositoryAdapter();

    @Test
    public void testFindOne() {

        //given:
        String itemId = "test";
        TestItem testItem = new TestItem();

        //setup:
        when(rabbitTemplate.convertSendAndReceive(any(), eq(itemId))).thenReturn(testItem);

        //when:
        testItemRepositoryAdapter.findOne(itemId);

        //then:
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        verify(rabbitTemplate).convertSendAndReceive(captor.capture(), captor.capture());

        List<String> capturedItems = captor.getAllValues();

        assertEquals(TEST_ITEMS_FIND_ONE_QUEUE, capturedItems.get(0));
        assertEquals(itemId, capturedItems.get(1));
    }
}