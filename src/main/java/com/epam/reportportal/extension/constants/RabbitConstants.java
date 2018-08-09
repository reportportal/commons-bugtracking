package com.epam.reportportal.extension.constants;

public class RabbitConstants {

	private RabbitConstants() {
		//static only
	}

	public final class QueueNames {

		public static final String LOGS_FIND_BY_TEST_ITEM_REF_QUEUE = "repository.find.logs.by.item";
		public static final String DATA_STORAGE_FETCH_DATA_QUEUE = "repository.find.data";
		public static final String TEST_ITEMS_FIND_ONE_QUEUE = "repository.find.item";
		public static final String INTEGRATION_FIND_ONE = "repository.find.integration";
		public static final String PROJECTS_FIND_BY_NAME = "repository.find.project.by.name";

		private QueueNames() {
			//static only
		}
	}

	public final class MessageHeaders {

		public static final String ITEM_REF = "itemRef";
		public static final String LIMIT = "limit";
		public static final String IS_LOAD_BINARY_DATA = "isLoadBinaryData";

		private MessageHeaders() {
			//static only
		}
	}
}
