package com.epam.reportportal.extension.adapter;

import static com.epam.reportportal.extension.constants.RabbitConstants.QueueNames.EXTERNAL_SYSTEMS_FIND_ONE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.epam.ta.reportportal.database.entity.ExternalSystem;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@RunWith(MockitoJUnitRunner.class)
public class ExternalSystemRepositoryAdapterTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private ExternalSystemRepositoryAdapter externalSystemRepositoryAdapter = new ExternalSystemRepositoryAdapter();

    @Test
    public void testFindOne() {

        //given:
        String systemId = "test";
        ExternalSystem externalSystem = new ExternalSystem();

        //setup:
        when(rabbitTemplate.convertSendAndReceive(any(), eq(systemId))).thenReturn(externalSystem);

        //when:
        externalSystemRepositoryAdapter.findOne(systemId);

        //then:
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        verify(rabbitTemplate).convertSendAndReceive(captor.capture(), captor.capture());

        List<String> capturedItems = captor.getAllValues();

        assertEquals(EXTERNAL_SYSTEMS_FIND_ONE, capturedItems.get(0));
        assertEquals(systemId, capturedItems.get(1));
    }

}