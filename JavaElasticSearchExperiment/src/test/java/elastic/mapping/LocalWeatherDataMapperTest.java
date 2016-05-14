// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package elastic.mapping;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class LocalWeatherDataMapperTest {

    private final static ObjectMapper jacksonObjectMapper = new ObjectMapper();

    @Test
    public void valid_json_mapping_generated_when_calling_get_mapping() throws Exception {

        // Create the Mapper:
        elastic.mapping.LocalWeatherDataMapper mapper = new elastic.mapping.LocalWeatherDataMapper();

        // Get the Mapping:
        XContentBuilder mapping = mapper.getMapping();

        // Turn it into a String:
        String actualJsonContent = mapping.string();

        // Expected Mapping:
        String expectedJsonContent = "{\"document\":{\"properties\":{\"dateTime\":{\"type\":\"date\",\"format\":\"strict_date_optional_time||epoch_millis\"},\"skyCondition\":{\"type\":\"string\"},\"station\":{\"type\":\"nested\",\"include_in_parent\":true,\"properties\":{\"coordinates\":{\"type\":\"geo_point\",\"lat_lon\":true},\"location\":{\"type\":\"string\"},\"name\":{\"type\":\"string\"},\"state\":{\"type\":\"string\"},\"wban\":{\"type\":\"string\"}}},\"stationPressure\":{\"type\":\"float\"},\"temperature\":{\"type\":\"float\"},\"windSpeed\":{\"type\":\"float\"}}}}";


        final JsonNode tree1 = jacksonObjectMapper.readTree(expectedJsonContent);
        final JsonNode tree2 = jacksonObjectMapper.readTree(actualJsonContent);

        Assert.assertEquals(true, tree1.equals(tree2));

    }

}
