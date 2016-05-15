// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

import converter.LocalWeatherDataConverter;
import csv.parser.Parsers;
import elastic.client.ElasticSearchClient;
import elastic.client.bulk.configuration.BulkProcessorConfiguration;
import elastic.client.bulk.options.BulkProcessingOptions;
import elastic.mapping.IObjectMapping;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.junit.Ignore;
import org.junit.Test;

import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Ignore("Integration Test")
public class IntegrationTest {

    @Test
    public void bulkProcessingTest() throws Exception {

        // Describes how to build the Index:
        IObjectMapping mapping = new elastic.mapping.LocalWeatherDataMapper();

        // Bulk Options for the Wrapped Client:
        BulkProcessorConfiguration bulkConfiguration = new BulkProcessorConfiguration(BulkProcessingOptions.builder()
                .setBulkActions(100)
                .build());

        // Create a new Client with default options:
        try (TransportClient transportClient = TransportClient.builder().build()) {

            transportClient.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

            // Now wrap the Elastic client in our bulk processing client:
            try (ElasticSearchClient<elastic.model.LocalWeatherData> client = new ElasticSearchClient<>(transportClient, "weather_data", mapping, bulkConfiguration)) {

                // Create the Index:
                client.createIndex();

                // Create the Mapping:
                client.createMapping();

                // And now process the data stream:
                try (Stream<elastic.model.LocalWeatherData> weatherDataStream = getData()) {
                    client.index(weatherDataStream);
                }
            }
        }
    }

    private static Stream<elastic.model.LocalWeatherData> getData() {

        // Data to read from:
        Path stationFilePath = FileSystems.getDefault().getPath("C:\\Users\\philipp\\Downloads\\csv", "201503station.txt");
        Path weatherDataFilePath = FileSystems.getDefault().getPath("C:\\Users\\philipp\\Downloads\\csv", "201503hourly.txt");

        try (Stream<csv.model.Station> stationStream = getStations(stationFilePath)) {

            // Get a Map of Stations for faster Lookup:
            Map<String, csv.model.Station> stationMap = stationStream
                    .collect(Collectors.toMap(csv.model.Station::getWban, x -> x));

            // Now read the LocalWeatherData from CSV:
            return getLocalWeatherData(weatherDataFilePath)
                    .filter(x -> stationMap.containsKey(x.getWban()))
                    .map(x -> {
                        // Get the matching Station:
                        csv.model.Station station = stationMap.get(x.getWban());
                        // Convert to the Elastic Representation:
                        return LocalWeatherDataConverter.convert(x, station);
                    });
        }
    }

    private static Stream<csv.model.Station> getStations(Path path) {
        return Parsers.StationParser().readFromFile(path, StandardCharsets.US_ASCII)
                .filter(x -> x.isValid())
                .map(x -> x.getResult());
    }


    private static Stream<csv.model.LocalWeatherData> getLocalWeatherData(Path path) {
        return Parsers.LocalWeatherDataParser().readFromFile(path, StandardCharsets.US_ASCII)
                .filter(x -> x.isValid())
                .map(x -> x.getResult());

    }
}