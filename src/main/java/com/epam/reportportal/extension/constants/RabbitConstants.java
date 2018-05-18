package com.epam.reportportal.extension.constants;

public class RabbitConstants {

    private RabbitConstants() {
        //static only
    }

    public final class QueueNames {

        public static final String LOGS_FIND_BY_TEST_ITEM_REF_QUEUE = "logs-find-by-test-item-ref";
        public static final String DATA_STORAGE_FETCH_DATA_QUEUE = "data-storage-fetch-data";
        public static final String TEST_ITEMS_FIND_ONE_QUEUE = "test-items-find-one";
        public static final String EXTERNAL_SYSTEMS_FIND_ONE = "external-system-find-one";
        public static final String PROJECTS_FIND_BY_NAME = "project-repository-find-by-name";

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
