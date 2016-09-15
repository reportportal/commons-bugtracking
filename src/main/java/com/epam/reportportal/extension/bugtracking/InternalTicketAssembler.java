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

import com.epam.ta.reportportal.database.BinaryData;
import com.epam.ta.reportportal.database.DataStorage;
import com.epam.ta.reportportal.database.dao.LogRepository;
import com.epam.ta.reportportal.database.dao.TestItemRepository;
import com.epam.ta.reportportal.database.entity.Log;
import com.epam.ta.reportportal.database.entity.item.TestItem;
import com.epam.ta.reportportal.ws.model.externalsystem.PostFormField;
import com.epam.ta.reportportal.ws.model.externalsystem.PostTicketRQ;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


/**
 * Converts REST model into internal domain model representation
 *
 * @author Andrei Varabyeu
 */
@Service
public class InternalTicketAssembler implements Function<PostTicketRQ, InternalTicket> {

	@Autowired
	private LogRepository logRepository;

	@Autowired
	private TestItemRepository itemRepository;

	@Autowired
	private DataStorage dataStorage;

	@Override
	public InternalTicket apply(PostTicketRQ input) {
		InternalTicket ticket = new InternalTicket();

		if (null != input.getFields()) {
			Multimap<String, String> fieldsMultimap = LinkedListMultimap.create();
			for (PostFormField field : input.getFields()) {
				fieldsMultimap.putAll(field.getId(), field.getValue());
			}
			ticket.setFields(fieldsMultimap);
		}

		if (input.getIsIncludeLogs() || input.getIsIncludeScreenshots()) {
			List<Log> logs = logRepository.findByTestItemRef(input.getTestItemId(), 0 == input.getNumberOfLogs() ? Integer.MAX_VALUE
					: input.getNumberOfLogs(), input.getIsIncludeScreenshots());
			List<InternalTicket.LogEntry> entries = new ArrayList<>(logs.size());
			for (Log log : logs) {
				BinaryData attachment = null;
				/* Get screenshots if required and they are present */
				if (null != log.getBinaryContent() && input.getIsIncludeScreenshots()) {
					attachment = dataStorage.fetchData(log.getBinaryContent().getBinaryDataId());
				}
				/* Forwarding enabled logs boolean if screens only required */
				entries.add(new InternalTicket.LogEntry(log, attachment, input.getIsIncludeLogs()));
			}
			ticket.setLogs(entries);
		}

		if (input.getIsIncludeComments()) {
			TestItem testItem = itemRepository.findOne(input.getTestItemId());
			if (null != testItem.getIssue().getIssueDescription())
				ticket.setComments(testItem.getIssue().getIssueDescription());
		}

		if (!CommonPredicates.IS_MAP_EMPTY.test(input.getBackLinks())) {
			ticket.setBackLinks(ImmutableMap.copyOf(input.getBackLinks()));
		}
		return ticket;
	}
}