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

import com.google.common.collect.Multimap;

import java.util.Map;

/**
 *
 */
public class InternalTicket {

	private String summary;

	private String comments;

	private Multimap<String, String> fields;

//	private List<LogEntry> logs;

	/**
	 * Item --> Item URL map
	 */
	private Map<Long, String> backLinks;

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String value) {
		this.comments = value;
	}

	public Multimap<String, String> getFields() {
		return fields;
	}

	public void setFields(Multimap<String, String> fields) {
		this.fields = fields;
	}

//	public List<LogEntry> getLogs() {
//		return logs;
//	}
//
//	public void setLogs(List<LogEntry> logs) {
//		this.logs = logs;
//	}

	public Map<Long, String> getBackLinks() {
		return backLinks;
	}

	public void setBackLinks(Map<Long, String> backLinks) {
		this.backLinks = backLinks;
	}

//	public static class LogEntry {
//		private Log log;
//		private BinaryData attachment;
//		private boolean isIncludeLogs;
//
//		public LogEntry(Log log, BinaryData attachment, boolean includeLogs) {
//			this.log = log;
//			this.attachment = attachment;
//			this.isIncludeLogs = includeLogs;
//		}
//
//		public Log getLog() {
//			return log;
//		}
//
//		public BinaryData getAttachment() {
//			return attachment;
//		}
//
//		public boolean getIncludeLogs() {
//			return isIncludeLogs;
//		}
//	}
}