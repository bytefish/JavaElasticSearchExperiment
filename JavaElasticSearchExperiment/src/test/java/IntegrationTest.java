// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

import converter.LocalWeatherDataConverter;
import csv.parser.Parsers;
import elastic.client.ElasticSearchClient;
import elastic.client.options.BulkProcessingOptions;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.Period;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IntegrationTest {

    @Test
    public void bulkProcessingTest() throws Exception {

        // Bulk Options:
        BulkProcessingOptions options = BulkProcessingOptions.builder()
                .setBulkActions(100)
                .build();

        // Create a new Client with default options:
        try (Client wrappedClient = TransportClient.builder().build()) {
            // Now wrap the client:
            try (ElasticSearchClient<elastic.model.LocalWeatherData> client = new ElasticSearchClient<>(wrappedClient, "weather_data", options)) {
                // And now process the data stream:
                client.index(getData());
            }
        }
    }

    private static Stream<elastic.model.LocalWeatherData> getData() {

        // Data to read from:
        Path stationFilePath = FileSystems.getDefault().getPath("C:\\Users\\philipp\\Downloads\\csv", "201503station.txt");
        Path weatherDataFilePath = FileSystems.getDefault().getPath("C:\\Users\\philipp\\Downloads\\csv", "201503hourly.txt");

        // Create Lookup Dictionary to map stations from:
        Map<String, csv.model.Station> stationMap = getStations(stationFilePath)
                .collect(Collectors.toMap(csv.model.Station::getWban, x -> x));

        return getLocalWeatherData(weatherDataFilePath)
                .filter(x -> stationMap.containsKey(x.getWban()))
                .map(x -> {
                    // Get the matching Station:
                    csv.model.Station station = stationMap.get(x.getWban());
                    // Convert to the Elastic Representation:
                    return LocalWeatherDataConverter.convert(x, station);
                });
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