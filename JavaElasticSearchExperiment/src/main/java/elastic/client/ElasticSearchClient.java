// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package elastic.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import elastic.client.options.BulkProcessingOptions;
import elastic.mapping.IObjectMapping;
import elastic.utils.ElasticSearchUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Client;
import utils.JsonUtilities;

import java.util.UUID;
import java.util.stream.Stream;

public class ElasticSearchClient<TEntity> implements AutoCloseable {

    private static final Logger log = LogManager.getLogger(ElasticSearchClient.class);

    private static final ObjectMapper mapper = new ObjectMapper();

    private final Client client;
    private final String indexName;
    private final IObjectMapping mapping;
    private final BulkProcessor bulkProcessor;

    public ElasticSearchClient(final Client client, final String indexName, final IObjectMapping mapping, final BulkProcessingOptions options) {
        this.client = client;
        this.indexName = indexName;
        this.mapping = mapping;
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
        if(!ElasticSearchUtils.indexExist(client, indexName).isExists()) {
            ElasticSearchUtils.createIndex(client, indexName);
        }
    }

    public void createMapping() {
        if(ElasticSearchUtils.indexExist(client, indexName).isExists()) {
            ElasticSearchUtils.putMapping(client, indexName, mapping);
        }
    }

    public void index(Stream<TEntity> entities) {
        entities
                .map(x -> JsonUtilities.convertJsonToBytes(mapper, x))
                .filter(x -> x.isPresent())
                .map(x -> createIndexRequest(x.get()))
                .forEach(bulkProcessor::add);
    }

    private IndexRequest createIndexRequest(byte[] messageBytes) {
        return client.prepareIndex()
                .setId(UUID.randomUUID().toString())
                .setIndex(indexName)
                .setType(mapping.getIndexType())
                .setSource(messageBytes)
                .request();
    }

    @Override
    public void close() throws Exception {
    }
}
