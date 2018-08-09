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

import com.epam.reportportal.extension.adapter.IntegrationRepositoryAdapter;
import com.epam.ta.reportportal.commons.validation.BusinessRule;
import com.epam.ta.reportportal.ws.model.ErrorType;
import com.epam.ta.reportportal.ws.model.YesNoRS;
import com.epam.ta.reportportal.ws.model.externalsystem.PostFormField;
import com.epam.ta.reportportal.ws.model.externalsystem.PostTicketRQ;
import com.epam.ta.reportportal.ws.model.externalsystem.Ticket;
import com.epam.ta.reportportal.ws.model.integration.IntegrationResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * REST facade for bugtracking extensions
 *
 * @author Andrei Varabyeu
 */
@Controller
public class BugTrackingController {

	@Autowired
	private BugTrackingService bugTrackingService;

	@Autowired
	private IntegrationRepositoryAdapter integrationRepositoryAdapter;

	@RequestMapping(method = RequestMethod.POST, path = "/check", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public YesNoRS checkConnection(@RequestBody IntegrationResource integration) {
		return new YesNoRS(bugTrackingService.checkConnection(integration));
	}

	@RequestMapping(method = RequestMethod.GET, path = "/{systemId}/ticket/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Ticket getTicket(@PathVariable Long systemId, @PathVariable String id) {
		Optional<Ticket> ticket = bugTrackingService.getTicket(id, integrationRepositoryAdapter.findOne(systemId));
		BusinessRule.expect(ticket, Optional::isPresent).verify(ErrorType.TICKET_NOT_FOUND, id);
		//noinspection OptionalGetWithoutIsPresent
		return ticket.get();
	}

	@RequestMapping(method = RequestMethod.POST, path = "/{systemId}/ticket", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Ticket submitTicket(@PathVariable Long systemId, @RequestBody PostTicketRQ ticketRQ) {
		return bugTrackingService.submitTicket(ticketRQ, integrationRepositoryAdapter.findOne(systemId));
	}

	@RequestMapping(method = RequestMethod.GET, path = "/{systemId}/ticket/types", produces = APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public List<String> getIssueTypes(@PathVariable Long systemId) {
		return bugTrackingService.getIssueTypes(integrationRepositoryAdapter.findOne(systemId));
	}

	@RequestMapping(method = RequestMethod.GET, path = "/{systemId}/ticket/{issueType}/fields", produces = APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public List<PostFormField> getTicketFields(@PathVariable Long systemId, @PathVariable String issueType) {
		return bugTrackingService.getTicketFields(issueType, integrationRepositoryAdapter.findOne(systemId));
	}
}