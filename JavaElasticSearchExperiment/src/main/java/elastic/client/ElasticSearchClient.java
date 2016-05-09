// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package elastic.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import elastic.client.options.BulkProcessingOptions;
import elastic.mapping.IObjectMapping;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Client;
import utils.JsonUtilities;

import java.io.IOException;
import java.util.UUID;
import java.util.stream.Stream;

public class ElasticSearchClient<TEntity> implements AutoCloseable {

    private static final Logger log = LogManager.getLogger(ElasticSearchClient.class);

    private static final ObjectMapper mapper = new ObjectMapper();

    private Client client;
    private String indexName;
    private IObjectMapping objectMapping;
    private BulkProcessor bulkProcessor;

    public ElasticSearchClient(final Client client, IObjectMapping objectMapping, String indexName, final BulkProcessingOptions options) {
        this.client = client;
        this.indexName = indexName;
        this.objectMapping = objectMapping;
        this.bulkProcessor = createBulkProcessor(options);
    }

    private BulkProcessor createBulkProcessor(final BulkProcessingOptions options) {
        return BulkProcessor.builder(client,
                new BulkProcessor.Listener() {
                    @Override
                    public void beforeBulk(long executionId, BulkRequest request) {
                        if(log.isDebugEnabled()) {
                            log.debug("Index {}: Before Bulk Insert with {} actions.", options.getName(), options.getBulkActions());
                        }
                    }

                    @Override
                    public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
                        if(log.isDebugEnabled()) {
                            log.debug("Index {}: After Bulk Insert with {} actions.", options.getName(), options.getBulkActions());
                        }
                    }

                    @Override
                    public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
                        if(log.isErrorEnabled()) {
                            log.error("Error executing Bulk Insert", failure);
                        }
                    }
                })
                .setName(options.getName())
                .setConcurrentRequests(options.getConcurrentRequests())
                .setBulkActions(options.getBulkActions())
                .setBulkSize(options.getBulkSize())
                .setFlushInterval(options.getFlushInterval())
                .setBackoffPolicy(options.getBackoffPolicy())
                .build();
    }

    public void createIndex() {
        try {
            internalCreateIndex();
        } catch(Exception e) {
            if(log.isErrorEnabled()) {
                log.error("Error Creating Index", e);
            }
        }
    }

    private void internalCreateIndex()throws IOException {

        final CreateIndexRequestBuilder createIndexRequestBuilder = client
                .admin() // Get the Admin interface...
                .indices() // Get the Indices interface...
                .prepareCreate(indexName) // We want to create a new index ...
                .setSource(objectMapping.getMapping().string());  // And set the custom mapping...

        final CreateIndexResponse indexResponse = createIndexRequestBuilder.execute().actionGet();

        if(log.isDebugEnabled()) {
            log.debug("CreatedIndexResponse: isAcknowledged {}", indexResponse.isAcknowledged());
        }
    }

    public void index(Stream<TEntity> entities) {
        entities
                // Get the JSON representation:
                .map(x -> JsonUtilities.convertJsonToBytes(mapper, x))
                // Filter only valid messages:
                .filter(x -> x.isPresent())
                // Create an IndexRequest for each message:
                .map(x -> createIndexRequest(x.get()))
                // And add it to the BulkProcessor:
                .forEach(bulkProcessor::add);
    }

    private IndexRequest createIndexRequest(byte[] messageBytes) {
        return client.prepareIndex()
                .setId(UUID.randomUUID().toString())
                .setIndex(indexName)
                .setSource(messageBytes)
                .request();
    }

    @Override
    public void close() throws Exception {
        if(client != null) {
            client.close();
        }
    }
}
