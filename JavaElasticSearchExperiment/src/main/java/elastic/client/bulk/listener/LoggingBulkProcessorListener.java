// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package elastic.client.bulk.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;

public class LoggingBulkProcessorListener implements BulkProcessor.Listener {

    private static final Logger log = LogManager.getLogger(LoggingBulkProcessorListener.class);

    public LoggingBulkProcessorListener() {
    }

    @Override
    public void beforeBulk(long executionId, BulkRequest request) {
        if(log.isDebugEnabled()) {
            log.debug("ExecutionId = {}, Actions = {}, Estimated Size = {}", executionId, request.numberOfActions(), request.estimatedSizeInBytes());
        }
    }

    @Override
    public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
        if(log.isDebugEnabled()) {
            log.debug("ExecutionId = {}, Actions = {}, Estimated Size = {}", executionId, request.numberOfActions(), request.estimatedSizeInBytes());
        }
    }

    @Override
    public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
        if(log.isErrorEnabled()) {
            log.error("ExecutionId = {}, Error = {}", executionId, failure);
        }
    }
}
