// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package elastic.client.options;

import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;

public class BulkProcessingOptionsBuilder {

    private String name;
    private int concurrentRequests = 1;
    private int bulkActions = 1000;
    private ByteSizeValue bulkSize = new ByteSizeValue(5, ByteSizeUnit.MB);
    private TimeValue flushInterval = null;
    private BackoffPolicy backoffPolicy = BackoffPolicy.exponentialBackoff();

    public BulkProcessingOptionsBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public BulkProcessingOptionsBuilder setConcurrentRequests(int concurrentRequests) {
        this.concurrentRequests = concurrentRequests;
        return this;
    }

    public BulkProcessingOptionsBuilder setBulkActions(int bulkActions) {
        this.bulkActions = bulkActions;
        return this;
    }

    public BulkProcessingOptionsBuilder setBulkSize(ByteSizeValue bulkSize) {
        this.bulkSize = bulkSize;
        return this;
    }

    public BulkProcessingOptionsBuilder setFlushInterval(TimeValue flushInterval) {
        this.flushInterval = flushInterval;
        return this;
    }

    public BulkProcessingOptionsBuilder setBackoffPolicy(BackoffPolicy backoffPolicy) {
        this.backoffPolicy = backoffPolicy;
        return this;
    }

    public BulkProcessingOptions build() {
        return new BulkProcessingOptions(name, concurrentRequests, bulkActions, bulkSize, flushInterval, backoffPolicy);
    }
}
