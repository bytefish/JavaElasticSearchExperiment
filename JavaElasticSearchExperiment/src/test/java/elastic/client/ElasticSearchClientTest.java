// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package elastic.client;

import elastic.client.bulk.configuration.BulkProcessorConfiguration;
import elastic.client.bulk.options.BulkProcessingOptionsBuilder;
import elastic.mapping.IObjectMapping;
import elastic.model.LocalWeatherData;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.index.IndexAction;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

public class ElasticSearchClientTest {


    @Test
    public void one_bulk_insert_on_transport_client_when_bulk_action_threshold_is_reached() {

        // Create Mocks:
        Client mockedTransportClient = mock(Client.class);
        BulkProcessor.Listener mockedBulkProcessorListener = mock(BulkProcessor.Listener.class);

        // Configure the BulkProcessor to use:
        BulkProcessorConfiguration configuration = new BulkProcessorConfiguration(new BulkProcessingOptionsBuilder().build(), mockedBulkProcessorListener);

        // And create a fake index builder:
        IndexRequestBuilder indexRequestBuilder = new IndexRequestBuilder(mockedTransportClient, IndexAction.INSTANCE);

        // The mapping to use:
        IObjectMapping localWeatherDataMapper = new elastic.mapping.LocalWeatherDataMapper();

        // Index to insert to:
        String indexName = "weather_data";

        // Initialize it with the default settings:
        when(mockedTransportClient.settings())
                .thenReturn(Settings.builder().build());

        when(mockedTransportClient.prepareIndex())
                .thenReturn(indexRequestBuilder);

        // Create the Test subject:
        ElasticSearchClient<elastic.model.LocalWeatherData> elasticSearchClient = new ElasticSearchClient<>(mockedTransportClient, indexName, localWeatherDataMapper, configuration);

        // Create more entities, than Bulk insertion threshold:
        Stream<LocalWeatherData> entitiesStream = getData(configuration.getBulkProcessingOptions().getBulkActions() + 1).stream();

        // Index the Data:
        elasticSearchClient.index(entitiesStream);

        // Verify, that the TransportClient bulk insert has been called:
        verify(mockedTransportClient, times(1)).bulk(anyObject(), anyObject());
        verify(mockedBulkProcessorListener, times(1)).beforeBulk(anyLong(), anyObject());
    }

    @Test
    public void no_value_inserted_when_not_enough_requests() {
        
        // Create Mocks:
        Client mockedTransportClient = mock(Client.class);
        BulkProcessor.Listener mockedBulkProcessorListener = mock(BulkProcessor.Listener.class);

        // Configure the BulkProcessor to use:
        BulkProcessorConfiguration configuration = new BulkProcessorConfiguration(new BulkProcessingOptionsBuilder().build(), mockedBulkProcessorListener);

        // And create a fake index builder:
        IndexRequestBuilder indexRequestBuilder = new IndexRequestBuilder(mockedTransportClient, IndexAction.INSTANCE);

        // The mapping to use:
        IObjectMapping localWeatherDataMapper = new elastic.mapping.LocalWeatherDataMapper();

        // Index to insert to:
        String indexName = "weather_data";

        // Initialize it with the default settings:
        when(mockedTransportClient.settings())
                .thenReturn(Settings.builder().build());

        when(mockedTransportClient.prepareIndex())
                .thenReturn(indexRequestBuilder);

        // Create the Test subject:
        ElasticSearchClient<elastic.model.LocalWeatherData> elasticSearchClient = new ElasticSearchClient<>(mockedTransportClient, indexName, localWeatherDataMapper, configuration);

        // Create more entities, than Bulk insertion threshold:
        Stream<LocalWeatherData> entitiesStream = getData(configuration.getBulkProcessingOptions().getBulkActions() - 1).stream();

        // Index the Data:
        elasticSearchClient.index(entitiesStream);

        // Verify, that the TransportClient bulk insert has been called:
        verify(mockedTransportClient, times(0)).bulk(anyObject(), anyObject());
        verify(mockedBulkProcessorListener, times(0)).beforeBulk(anyLong(), anyObject());
    }

    private List<elastic.model.LocalWeatherData> getData(int numberOfEntities) {
        List<LocalWeatherData> entitiesToInsert = new ArrayList<>();

        for (int i = 0; i < numberOfEntities; i++) {
            entitiesToInsert.add(new LocalWeatherData());
        }

        return entitiesToInsert;
    }
}
