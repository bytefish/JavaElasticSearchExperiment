// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package elastic.utils;

import elastic.exceptions.CreateIndexFailedException;
import elastic.exceptions.IndicesExistsFailedException;
import elastic.exceptions.PutMappingFailedException;
import elastic.mapping.IObjectMapping;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.client.Client;

import java.io.IOException;

public class ElasticSearchUtils {

    private static final Logger log = LogManager.getLogger(ElasticSearchUtils.class);

    public static IndicesExistsResponse indexExist(Client client, String indexName) {
        try {
            return client.admin().indices()
                    .prepareExists(indexName)
                    .execute().actionGet();
        } catch(Exception e) {
            if(log.isErrorEnabled()) {
                log.error("Error Checking Index Exist", e);
            }
            throw new IndicesExistsFailedException(indexName, e);
        }
    }

    public static CreateIndexResponse createIndex(Client client, String indexName) {
        try {
            return internalCreateIndex(client, indexName);
        } catch(Exception e) {
            if(log.isErrorEnabled()) {
                log.error("Error Creating Index", e);
            }
            throw new CreateIndexFailedException(indexName, e);
        }
    }

    public static PutMappingResponse putMapping(Client client, String indexName, IObjectMapping mapping) {
        try {
            return internalPutMapping(client, indexName, mapping);
        } catch(Exception e) {
            if(log.isErrorEnabled()) {
                log.error("Error Creating Index", e);
            }
            throw new PutMappingFailedException(indexName, e);
        }
    }

    private static CreateIndexResponse internalCreateIndex(Client client, String indexName) throws IOException {
        final CreateIndexRequestBuilder createIndexRequestBuilder = client
                .admin() // Get the Admin interface...
                .indices() // Get the Indices interface...
                .prepareCreate(indexName); // We want to create a new index ....

        final CreateIndexResponse indexResponse = createIndexRequestBuilder.execute().actionGet();

        if(log.isDebugEnabled()) {
            log.debug("CreatedIndexResponse: isAcknowledged {}", indexResponse.isAcknowledged());
        }

        return indexResponse;
    }

    private static PutMappingResponse internalPutMapping(Client client, String indexName, IObjectMapping mapping) throws IOException {

        final PutMappingRequest putMappingRequest = new PutMappingRequest(indexName)
                .type(mapping.getType())
                .source(mapping.getMapping().string());

        final PutMappingResponse putMappingResponse = client
                .admin()
                .indices()
                .putMapping(putMappingRequest)
                .actionGet();

        if(log.isDebugEnabled()) {
            log.debug("PutMappingResponse: isAcknowledged {}", putMappingResponse.isAcknowledged());
        }

        return putMappingResponse;
    }
}
