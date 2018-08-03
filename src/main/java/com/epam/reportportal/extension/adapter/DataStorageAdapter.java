package com.epam.reportportal.extension.adapter;

import com.epam.ta.reportportal.BinaryData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import static com.epam.reportportal.extension.constants.RabbitConstants.QueueNames.DATA_STORAGE_FETCH_DATA_QUEUE;

/**
 * @author Andrei Varabyeu
 */
@Component
public class DataStorageAdapter {

	private final RabbitTemplate rabbitTemplate;

	@Autowired
	public DataStorageAdapter(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	public BinaryData fetchData(String dataId) {
		return rabbitTemplate.convertSendAndReceiveAsType(DATA_STORAGE_FETCH_DATA_QUEUE,
				dataId,
				new ParameterizedTypeReference<BinaryData>() {
				}
		);
	}
}
