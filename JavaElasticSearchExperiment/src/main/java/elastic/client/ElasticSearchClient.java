// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package elastic.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import elastic.client.bulk.configuration.BulkProcessorConfiguration;
import elastic.mapping.IObjectMapping;
import elastic.utils.ElasticSearchUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Client;
import utils.JsonUtilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class ElasticSearchClient<TEntity> implements AutoCloseable {

    private static final Logger log = LogManager.getLogger(ElasticSearchClient.class);

    private static final ObjectMapper mapper = new ObjectMapper();

    private final Client client;
    private final String indexName;
    private final IObjectMapping mapping;
    private final BulkProcessor bulkProcessor;

    public ElasticSearchClient(final Client client, final String indexName, final IObjectMapping mapping, final BulkProcessorConfiguration bulkProcessorConfiguration) {
        this.client = client;
        this.indexName = indexName;
        this.mapping = mapping;
        this.bulkProcessor = bulkProcessorConfiguration.build(client);
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

    public void index(TEntity entity) {
        index(Arrays.asList(entity));

        bulkProcessor.flush();
    }

    public void index(List<TEntity> entities) {
        index(entities.stream());
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
