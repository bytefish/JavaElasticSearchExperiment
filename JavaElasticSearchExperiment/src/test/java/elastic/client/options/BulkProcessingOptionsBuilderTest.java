// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package elastic.client.options;

import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.junit.Assert;
import org.junit.Test;

public class BulkProcessingOptionsBuilderTest {

    @Test
    public void default_values_set_when_initializing_builder() {

        // Build:
        BulkProcessingOptions options = new BulkProcessingOptionsBuilder().build();

        // Check Values:
        Assert.assertEquals(null, options.getName());
        Assert.assertEquals(1, options.getConcurrentRequests());
        Assert.assertEquals(1000, options.getBulkActions());
        Assert.assertEquals(new ByteSizeValue(5, ByteSizeUnit.MB).bytes(), options.getBulkSize().bytes());
        Assert.assertEquals(null, options.getFlushInterval());
        Assert.assertEquals(BackoffPolicy.exponentialBackoff().getClass(), options.getBackoffPolicy().getClass());
    }

    @Test
    public void custom_values_set_when_custom_values_set_for_builder() {

        // Build:
        BulkProcessingOptions options = new BulkProcessingOptionsBuilder()
                .setName("Test")
                .setConcurrentRequests(3)
                .setBulkActions(10)
                .setBulkSize(new ByteSizeValue(2, ByteSizeUnit.MB))
                .setFlushInterval(new TimeValue(321))
                .setBackoffPolicy(BackoffPolicy.noBackoff())
                .build();

        // Check Values:
        Assert.assertEquals("Test", options.getName());
        Assert.assertEquals(3, options.getConcurrentRequests());
        Assert.assertEquals(10, options.getBulkActions());
        Assert.assertEquals(new ByteSizeValue(2, ByteSizeUnit.MB).bytes(), options.getBulkSize().bytes());
        Assert.assertEquals(new TimeValue(321).getMillis(), options.getFlushInterval().getMillis());
        Assert.assertEquals(BackoffPolicy.noBackoff().getClass(), options.getBackoffPolicy().getClass());
    }
}
