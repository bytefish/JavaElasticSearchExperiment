// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package elastic.client.bulk.options;

import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;

public class BulkProcessingOptions {

    private String name;
    private int concurrentRequests;
    private int bulkActions;
    private ByteSizeValue bulkSize;
    private TimeValue flushInterval;
    private BackoffPolicy backoffPolicy;

    public BulkProcessingOptions(String name, int concurrentRequests, int bulkActions, ByteSizeValue bulkSize, TimeValue flushInterval, BackoffPolicy backoffPolicy) {
        this.name = name;
        this.concurrentRequests = concurrentRequests;
        this.bulkActions = bulkActions;
        this.bulkSize = bulkSize;
        this.flushInterval = flushInterval;
        this.backoffPolicy = backoffPolicy;
    }

    public String getName() {
        return name;
    }

    public int getConcurrentRequests() {
        return concurrentRequests;
    }

    public int getBulkActions() {
        return bulkActions;
    }

    public ByteSizeValue getBulkSize() {
        return bulkSize;
    }

    public TimeValue getFlushInterval() {
        return flushInterval;
    }

    public BackoffPolicy getBackoffPolicy() {
        return backoffPolicy;
    }

    public static BulkProcessingOptionsBuilder builder() {
        return new BulkProcessingOptionsBuilder();
    }
}
