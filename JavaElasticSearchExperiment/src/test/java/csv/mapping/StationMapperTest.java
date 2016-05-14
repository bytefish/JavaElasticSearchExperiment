// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package csv.mapping;

import de.bytefish.jtinycsvparser.mapping.CsvMappingResult;
import org.junit.Assert;
import org.junit.Test;

public class StationMapperTest {

    @Test
    public void Converts_Station_Data_When_Correct_Input_CSV_Given() {

        // The Mapper we want to check:
        StationMapper stationMapper = new StationMapper(() -> new csv.model.Station());

        // Tokenize the Line:
        String[] csvLineElements = "24028|72566|BFF|01|25|7665|SCOTTSBLUFF|NE|WESTERN NE REGIONAL/HEILIG FIELD AP|41.8705|-103.593|3945|3958|3949|-7".split("\\|");

        // Now do the Mapping:
        CsvMappingResult<csv.model.Station> station = stationMapper.Map(csvLineElements);

        // Expected CSV Station Data:
        csv.model.Station expectedCsvStationData = new csv.model.Station();

        expectedCsvStationData.setWban("24028");
        expectedCsvStationData.setWmo("72566");
        expectedCsvStationData.setCallSign("BFF");
        expectedCsvStationData.setClimateDivisionCode("01");
        expectedCsvStationData.setClimateDivisionStateCode("25");
        expectedCsvStationData.setClimateDivisionStationCode("7665");
        expectedCsvStationData.setName("SCOTTSBLUFF");
        expectedCsvStationData.setState("NE");
        expectedCsvStationData.setLocation("WESTERN NE REGIONAL/HEILIG FIELD AP");
        expectedCsvStationData.setLatitude(41.8705f);
        expectedCsvStationData.setLongitude(-103.593f);
        expectedCsvStationData.setGroundHeight(3945);
        expectedCsvStationData.setStationHeight(3958);
        expectedCsvStationData.setBarometer(3949);
        expectedCsvStationData.setTimeZone(-7);

        // Check for Equality
        Assert.assertEquals(true, station.isValid());

        csv.model.Station actualCsvStationData = station.getResult();

        Assert.assertEquals(expectedCsvStationData.getWban(), actualCsvStationData.getWban());
        Assert.assertEquals(expectedCsvStationData.getWmo(), actualCsvStationData.getWmo());
        Assert.assertEquals(expectedCsvStationData.getCallSign(), actualCsvStationData.getCallSign());
        Assert.assertEquals(expectedCsvStationData.getClimateDivisionCode(),actualCsvStationData.getClimateDivisionCode());
        Assert.assertEquals(expectedCsvStationData.getClimateDivisionStateCode(),actualCsvStationData.getClimateDivisionStateCode());
        Assert.assertEquals(expectedCsvStationData.getClimateDivisionStationCode(),actualCsvStationData.getClimateDivisionStationCode());
        Assert.assertEquals(expectedCsvStationData.getName(), actualCsvStationData.getName());
        Assert.assertEquals(expectedCsvStationData.getState(), actualCsvStationData.getState());
        Assert.assertEquals(expectedCsvStationData.getLocation(),actualCsvStationData.getLocation());
        Assert.assertEquals(expectedCsvStationData.getLatitude(),actualCsvStationData.getLatitude(), 1e-3);
        Assert.assertEquals(expectedCsvStationData.getLongitude(),actualCsvStationData.getLongitude(), 1e-3);
        Assert.assertEquals(expectedCsvStationData.getGroundHeight(),actualCsvStationData.getGroundHeight());
        Assert.assertEquals(expectedCsvStationData.getStationHeight(),actualCsvStationData.getStationHeight());
        Assert.assertEquals(expectedCsvStationData.getBarometer(),actualCsvStationData.getBarometer());
        Assert.assertEquals(expectedCsvStationData.getTimeZone(),actualCsvStationData.getTimeZone());
    }
}