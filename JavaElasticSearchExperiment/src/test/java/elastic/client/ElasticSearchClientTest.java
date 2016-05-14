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

import static org.mockito.Mockito.*;

public class ElasticSearchClientTest {


    @Test
    public void bulkInsertionTest() {

        BulkProcessor.Listener mockedBulkProcessorListener = mock(BulkProcessor.Listener.class);

        // Index to insert to:
        String indexName = "weather_data";

        // Configure the BulkRequests:
        BulkProcessorConfiguration configuration = new BulkProcessorConfiguration(new BulkProcessingOptionsBuilder().build(), mockedBulkProcessorListener);

        // Mock the underlying Transport Client:
        Client mockedTransportClient = mock(Client.class);

        // Initialize it with the default settings:
        when(mockedTransportClient.settings()).thenReturn(Settings.builder().build());

        // And create a fake index builder:
        IndexRequestBuilder stubbedRequestBuilder = new IndexRequestBuilder(mockedTransportClient, IndexAction.INSTANCE);

        when(mockedTransportClient.prepareIndex()).thenReturn(stubbedRequestBuilder);

        // The mapping:
        IObjectMapping localWeatherDataMapper = new elastic.mapping.LocalWeatherDataMapper();

        ElasticSearchClient<elastic.model.LocalWeatherData> elasticSearchClient = new ElasticSearchClient<>(mockedTransportClient, indexName, localWeatherDataMapper, configuration);


        List<LocalWeatherData> entitiesToInsert = new ArrayList<>();

        for (int i = 0; i < configuration.getBulkProcessingOptions().getBulkActions() + 1; i++) {
            entitiesToInsert.add(new LocalWeatherData());
        }

        elasticSearchClient.index(entitiesToInsert.stream());

        verify(mockedTransportClient, times(1)).bulk(anyObject(), anyObject());
        verify(mockedBulkProcessorListener, times(1)).beforeBulk(anyLong(), anyObject());
    }

    @Test
    public void no_value_inserted_when_not_enough_requests() {

        BulkProcessor.Listener mockedBulkProcessorListener = mock(BulkProcessor.Listener.class);

        // Index to insert to:
        String indexName = "weather_data";

        // Configure the BulkRequests:
        BulkProcessorConfiguration configuration = new BulkProcessorConfiguration(new BulkProcessingOptionsBuilder().build(), mockedBulkProcessorListener);

        // Mock the underlying Transport Client:
        Client mockedTransportClient = mock(Client.class);

        // Initialize it with the default settings:
        when(mockedTransportClient.settings()).thenReturn(Settings.builder().build());

        // And create a fake index builder:
        IndexRequestBuilder stubbedRequestBuilder = new IndexRequestBuilder(mockedTransportClient, IndexAction.INSTANCE);

        when(mockedTransportClient.prepareIndex()).thenReturn(stubbedRequestBuilder);

        // The mapping:
        IObjectMapping localWeatherDataMapper = new elastic.mapping.LocalWeatherDataMapper();

        ElasticSearchClient<elastic.model.LocalWeatherData> elasticSearchClient = new ElasticSearchClient<>(mockedTransportClient, indexName, localWeatherDataMapper, configuration);


        List<LocalWeatherData> entitiesToInsert = new ArrayList<>();

        for (int i = 0; i < configuration.getBulkProcessingOptions().getBulkActions() - 1; i++) {
            entitiesToInsert.add(new LocalWeatherData());
        }

        elasticSearchClient.index(entitiesToInsert.stream());

        verify(mockedTransportClient, times(0)).bulk(anyObject(), anyObject());
        verify(mockedBulkProcessorListener, times(0)).beforeBulk(anyLong(), anyObject());
    }
}
