// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package elastic.client.bulk.listener;

import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.junit.Before;
import org.junit.Test;
import utils.TestUtils;

public class LoggingBulkProcessorListenerTest {

    private LoggingBulkProcessorListener listener;

    @Before
    public void setUp() {
        listener = new LoggingBulkProcessorListener();
    }

    @Test
    public void before_bulk_does_not_throw_when_called() throws Exception {
        TestUtils.assertDoesNotThrow(() -> listener.beforeBulk(1, new BulkRequest()));
    }

    @Test
    public void testAfterBulk() throws Exception {
        TestUtils.assertDoesNotThrow(() -> listener.afterBulk(1, new BulkRequest(), new BulkResponse(new BulkItemResponse[]{}, 1000)));
    }

    @Test
    public void testAfterBulk1() throws Exception {
        TestUtils.assertDoesNotThrow(() -> listener.afterBulk(1, new BulkRequest(), new BulkResponse(new BulkItemResponse[]{}, 1000)));
    }
}
