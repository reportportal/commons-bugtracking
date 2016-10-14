/*
 * Copyright 2016 EPAM Systems
 * 
 * 
 * This file is part of EPAM Report Portal.
 * https://github.com/epam/ReportPortal
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
/*
 * This file is part of Report Portal.
 *
 * Report Portal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Report Portal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Report Portal.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.epam.reportportal.extension.bugtracking;

import com.epam.ta.reportportal.commons.Preconditions;
import com.epam.ta.reportportal.commons.validation.BusinessRule;
import com.epam.ta.reportportal.database.dao.ExternalSystemRepository;
import com.epam.ta.reportportal.database.entity.ExternalSystem;
import com.epam.ta.reportportal.ws.model.ErrorType;
import com.epam.ta.reportportal.ws.model.YesNoRS;
import com.epam.ta.reportportal.ws.model.externalsystem.PostFormField;
import com.epam.ta.reportportal.ws.model.externalsystem.PostTicketRQ;
import com.epam.ta.reportportal.ws.model.externalsystem.Ticket;
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
	private ExternalSystemStrategy externalSystemStrategy;

	@Autowired
	private ExternalSystemRepository externalSystemRepository;

	@RequestMapping(method = RequestMethod.POST, path = "/check", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public YesNoRS checkConnection(@RequestBody ExternalSystem externalSystem) {
		return new YesNoRS(externalSystemStrategy.checkConnection(externalSystem));
	}

	@RequestMapping(method = RequestMethod.GET, path = "/{systemId}/ticket/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Ticket getTicket(@PathVariable  String systemId, @PathVariable String id) {
		Optional<Ticket> ticket = externalSystemStrategy.getTicket(id, externalSystemRepository.findOne(systemId));
		BusinessRule.expect(ticket, Preconditions.IS_PRESENT).verify(ErrorType.TICKET_NOT_FOUND, id);
		//noinspection OptionalGetWithoutIsPresent
		return ticket.get();
	}

	@RequestMapping(method = RequestMethod.POST, path = "/{systemId}/ticket", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Ticket submitTicket(@PathVariable String systemId, @RequestBody PostTicketRQ ticketRQ) {
		return externalSystemStrategy.submitTicket(ticketRQ, externalSystemRepository.findOne(systemId));

	}

	@RequestMapping(method = RequestMethod.GET, path = "/{systemId}/ticket/{issueType}/fields", produces = APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public List<PostFormField> getTicketFields(@PathVariable String systemId, @PathVariable String issueType) {
		return externalSystemStrategy.getTicketFields(issueType, externalSystemRepository.findOne(systemId));

	}
}