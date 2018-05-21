package com.epam.reportportal.extension.adapter;

import static com.epam.reportportal.extension.constants.RabbitConstants.QueueNames.PROJECTS_FIND_BY_NAME;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.epam.ta.reportportal.database.entity.Project;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@RunWith(MockitoJUnitRunner.class)
public class ProjectRepositoryAdapterTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private ProjectRepositoryAdapter projectRepositoryAdapter = new ProjectRepositoryAdapter();

    @Test
    public void testFindOne() {

        //given:
        String projectName = "test";
        Project project = new Project();

        //setup:
        when(rabbitTemplate.convertSendAndReceive(any(), eq(projectName))).thenReturn(project);

        //when:
        projectRepositoryAdapter.findByName(projectName);

        //then:
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        verify(rabbitTemplate).convertSendAndReceive(captor.capture(), captor.capture());

        List<String> capturedItems = captor.getAllValues();

        assertEquals(PROJECTS_FIND_BY_NAME, capturedItems.get(0));
        assertEquals(projectName, capturedItems.get(1));
    }
}