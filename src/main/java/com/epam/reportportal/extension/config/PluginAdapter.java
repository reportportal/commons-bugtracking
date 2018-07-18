package com.epam.reportportal.extension.config;

import com.google.common.collect.ImmutableMap;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

import static com.epam.reportportal.extension.config.RabbitMqConfiguration.EXCHANGE_PLUGINS;
import static com.epam.reportportal.extension.config.RabbitMqConfiguration.KEY_PLUGINS_PONG;

/**
 * Plugin configuration
 *
 * @author Andrei Varabyeu
 */
@Service
public class PluginAdapter {

	private final AmqpTemplate ampqTemplate;

	@Autowired
	public PluginAdapter(AmqpTemplate ampqTemplate) {
		this.ampqTemplate = ampqTemplate;
	}

	@RabbitListener(queues = "#{ @pluginsPingQueue.name }")
	void fulfillPluginsList2(@Payload Map<String, ?> payload) {
		this.ampqTemplate.convertAndSend(
				EXCHANGE_PLUGINS,
				KEY_PLUGINS_PONG,
				ImmutableMap.builder().put("type", "bugtracking").put("id", UUID.randomUUID().toString()).build()
		);
	}
}
