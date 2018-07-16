/*
 * Copyright 2016 EPAM Systems
 *
 *
 * This file is part of EPAM Report Portal.
 * https://github.com/reportportal/commons-bugtracking
 *
 * Report Portal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Report Portal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Report Portal.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.epam.reportportal.extension.bugtracking;

import com.epam.ta.reportportal.commons.ExceptionMappings;
import com.epam.ta.reportportal.commons.exception.rest.DefaultErrorResolver;
import com.epam.ta.reportportal.commons.exception.rest.ReportPortalExceptionResolver;
import com.epam.ta.reportportal.commons.exception.rest.RestExceptionHandler;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Collections;
import java.util.List;

/**
 * Base Entry Point for BugTracking Extensions
 *
 * @author Andrei Varabyeu
 */
@SpringBootApplication
abstract public class BugTrackingApp extends WebMvcConfigurerAdapter {

	@Override
	public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
		RestExceptionHandler handler = new RestExceptionHandler();
		handler.setOrder(Ordered.HIGHEST_PRECEDENCE);

		DefaultErrorResolver defaultErrorResolver = new DefaultErrorResolver(ExceptionMappings.DEFAULT_MAPPING);
		handler.setErrorResolver(new ReportPortalExceptionResolver(defaultErrorResolver));
		handler.setMessageConverters(Collections.singletonList(new MappingJackson2HttpMessageConverter()));
		exceptionResolvers.add(handler);
	}

	@Bean
	abstract public BugTrackingService externalSystemStrategy();
}
