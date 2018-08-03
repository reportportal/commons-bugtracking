package com.epam.reportportal.extension.config;

import com.epam.reportportal.extension.constants.RabbitConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

@EnableRabbit
@Configuration
public class RabbitMqConfiguration {

	static final String EXCHANGE_PLUGINS = "plugins";
	static final String KEY_PLUGINS_PONG = "broadcast.plugins.pong";
	static final String KEY_PLUGINS_PING = "broadcast.plugins.ping";

	@Bean
	public Queue projectRepoQueue() {
		return new Queue(RabbitConstants.QueueNames.PROJECTS_FIND_BY_NAME);
	}

	@Bean
	public Queue dataStorageQueue() {
		return new Queue(RabbitConstants.QueueNames.DATA_STORAGE_FETCH_DATA_QUEUE);
	}

	@Bean
	public Queue integrationRepoQueue() {
		return new Queue(RabbitConstants.QueueNames.INTEGRATION_FIND_ONE);
	}

	@Bean
	public Queue logRepoQueue() {
		return new Queue(RabbitConstants.QueueNames.LOGS_FIND_BY_TEST_ITEM_REF_QUEUE);
	}

	@Bean
	public Queue testItemRepoQueue() {
		return new Queue(RabbitConstants.QueueNames.TEST_ITEMS_FIND_ONE_QUEUE);
	}

	@Bean
	public Queue pluginsPingQueue() {
		return new AnonymousQueue(new AnonymousQueue.Base64UrlNamingStrategy(KEY_PLUGINS_PING + "."));
	}

	@Bean
	public Binding pluginsPingBinding() {
		return BindingBuilder.bind(pluginsPingQueue()).to(pluginsExchange()).with(KEY_PLUGINS_PING);
	}

	@Bean
	public TopicExchange pluginsExchange() {
		return new TopicExchange(EXCHANGE_PLUGINS, false, false);
	}

	@Bean
	public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
		return new Jackson2JsonMessageConverter(objectMapper);
	}

	@Bean
	public ConnectionFactory connectionFactory(@Value("${rp.amqp.addresses}") URI addresses) {
		return new CachingConnectionFactory(addresses);
	}

	@Bean
	public AmqpAdmin amqpAdmin(@Autowired ConnectionFactory connectionFactory) {
		return new RabbitAdmin(connectionFactory);
	}

	@Bean
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(@Autowired ConnectionFactory connectionFactory,
			ObjectMapper objectMapper) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setDefaultRequeueRejected(false);
		factory.setChannelTransacted(true);
		factory.setMessageConverter(jsonMessageConverter(objectMapper));
		factory.setConcurrentConsumers(3);
		factory.setMaxConcurrentConsumers(10);
		return factory;
	}
}
