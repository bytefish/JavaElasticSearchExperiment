// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package csv.mapping;

import de.bytefish.jtinycsvparser.mapping.CsvMappingResult;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;

public class LocalWeatherDataMapperTest {

    @Test
    public void should_parse_correctly_when_valid_line_is_given_and_no_missing_data() {

        // The Mapper we want to check:
        LocalWeatherDataMapper localWeatherDataMapper = new LocalWeatherDataMapper(() -> new csv.model.LocalWeatherData());

        // Tokenize the Line:
        String[] csvLineElements =  "00102,20150912,0517,0,SCT004 BKN010 OVC013, , 8.00, , , ,34, ,1.0, ,34, ,1.1, ,34, ,1.0, ,100, , 0, ,000, , , ,29.58, , , , , ,M, ,AA, , , ,29.76,".split(",");

        // Now do the Mapping:
        CsvMappingResult<csv.model.LocalWeatherData> localWeatherData = localWeatherDataMapper.Map(csvLineElements);

        // Check Results:

        Assert.assertEquals(true, localWeatherData.isValid());

        csv.model.LocalWeatherData actualCsvLocalWeatherData = localWeatherData.getResult();

        Assert.assertEquals("00102", actualCsvLocalWeatherData.getWban());
        Assert.assertEquals(LocalDate.of(2015, 9, 12), actualCsvLocalWeatherData.getDate());
        Assert.assertEquals(LocalTime.of(5, 17), actualCsvLocalWeatherData.getTime());
        Assert.assertEquals("SCT004 BKN010 OVC013", actualCsvLocalWeatherData.getSkyCondition());
        Assert.assertEquals(1.0f, actualCsvLocalWeatherData.getDryBulbCelsius(), 1e-4);
        Assert.assertEquals(0.0f, actualCsvLocalWeatherData.getWindSpeed(), 1e-4);
        Assert.assertEquals(29.58f, actualCsvLocalWeatherData.getStationPressure(), 1e-4);
    }

    @Test
    public void should_parse_correctly_when_valid_line_is_given_and_missing_data_is_present() {

        // The Mapper we want to check:
        LocalWeatherDataMapper localWeatherDataMapper = new LocalWeatherDataMapper(() -> new csv.model.LocalWeatherData());

        // Tokenize the Line:
        String[] csvLineElements =  "00102,20150912,0517,0,SCT004 BKN010 OVC013, , 8.00, , , ,34, ,M, ,34, ,1.1, ,34, ,1.0, ,100, ,M, ,000, , , ,M, , , , , ,M, ,AA, , , ,M,".split(",");

        // Now do the Mapping:
        CsvMappingResult<csv.model.LocalWeatherData> localWeatherData = localWeatherDataMapper.Map(csvLineElements);

        // Check Results:

        Assert.assertEquals(true, localWeatherData.isValid());

        csv.model.LocalWeatherData actualCsvLocalWeatherData = localWeatherData.getResult();

        Assert.assertEquals("00102", actualCsvLocalWeatherData.getWban());
        Assert.assertEquals(LocalDate.of(2015, 9, 12), actualCsvLocalWeatherData.getDate());
        Assert.assertEquals(LocalTime.of(5, 17), actualCsvLocalWeatherData.getTime());
        Assert.assertEquals("SCT004 BKN010 OVC013", actualCsvLocalWeatherData.getSkyCondition());
        Assert.assertEquals(null, actualCsvLocalWeatherData.getDryBulbCelsius());
        Assert.assertEquals(null, actualCsvLocalWeatherData.getWindSpeed());
        Assert.assertEquals(null, actualCsvLocalWeatherData.getStationPressure());
    }
}
