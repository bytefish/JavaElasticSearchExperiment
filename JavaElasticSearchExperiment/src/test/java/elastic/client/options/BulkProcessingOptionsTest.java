// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package elastic.client.options;

import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.junit.Assert;
import org.junit.Test;

public class BulkProcessingOptionsTest {

    @Test
    public void get_methods_return_correct_values_when_initialized() {

        BulkProcessingOptions options = new BulkProcessingOptions("Name", 1, 2, new ByteSizeValue(10), new TimeValue(10), BackoffPolicy.exponentialBackoff());

        Assert.assertEquals("Name", options.getName());
        Assert.assertEquals(1, options.getConcurrentRequests());
        Assert.assertEquals(2, options.getBulkActions());
        Assert.assertEquals(10, options.getBulkSize().bytes());
        Assert.assertEquals(10, options.getFlushInterval().getMillis());
        Assert.assertEquals(BackoffPolicy.exponentialBackoff().getClass(), options.getBackoffPolicy().getClass());
    }
}
