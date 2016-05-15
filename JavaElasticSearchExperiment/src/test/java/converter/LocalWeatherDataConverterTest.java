// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package converter;

import org.junit.Assert;
import org.junit.Test;
import utils.DateUtilities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;

public class LocalWeatherDataConverterTest {
    @Test
    public void testConvert() throws Exception {

        // CSV Weather Data:
        csv.model.LocalWeatherData csvLocalWeatherData = new csv.model.LocalWeatherData();

        csvLocalWeatherData.setWban("WB1931");
        csvLocalWeatherData.setDate(LocalDate.of(2013, 1, 1));
        csvLocalWeatherData.setTime(LocalTime.of(0, 30, 0, 0));
        csvLocalWeatherData.setDryBulbCelsius(22.0f);
        csvLocalWeatherData.setSkyCondition("CLR");
        csvLocalWeatherData.setStationPressure(42.12f);
        csvLocalWeatherData.setWindSpeed(5.0f);

        // CSV Station Data:
        csv.model.Station csvStationData = new csv.model.Station();
        csvStationData.setWban("24028");
        csvStationData.setWmo("72566");
        csvStationData.setCallSign("BFF");
        csvStationData.setClimateDivisionCode("01");
        csvStationData.setClimateDivisionStateCode("25");
        csvStationData.setClimateDivisionStationCode("7665");
        csvStationData.setName("SCOTTSBLUFF");
        csvStationData.setState("NE");
        csvStationData.setLocation("WESTERN NE REGIONAL/HEILIG FIELD AP");
        csvStationData.setLatitude(41.8705f);
        csvStationData.setLongitude(-103.593f);
        csvStationData.setGroundHeight(3945);
        csvStationData.setStationHeight(3958);
        csvStationData.setBarometer(3949);
        csvStationData.setTimeZone(-7);

        // Elastic Geo Data:
        elastic.model.GeoLocation elasticGeoLocation = new elastic.model.GeoLocation();

        elasticGeoLocation.lat = csvStationData.getLatitude();
        elasticGeoLocation.lon = csvStationData.getLongitude();

        // Elastic Station Data:
        elastic.model.Station elasticStation = new elastic.model.Station();

        elasticStation.wban = csvStationData.getWban();
        elasticStation.name = csvStationData.getName();
        elasticStation.state = csvStationData.getState();
        elasticStation.location = csvStationData.getLocation();
        elasticStation.geoLocation = elasticGeoLocation;

        // Elastic Weather Data:
        elastic.model.LocalWeatherData elasticLocalWeatherData = new elastic.model.LocalWeatherData();

        elasticLocalWeatherData.windSpeed = csvLocalWeatherData.getWindSpeed();
        elasticLocalWeatherData.temperature = csvLocalWeatherData.getDryBulbCelsius();
        elasticLocalWeatherData.stationPressure = csvLocalWeatherData.getStationPressure();
        elasticLocalWeatherData.skyCondition = csvLocalWeatherData.getSkyCondition();
        elasticLocalWeatherData.dateTime = DateUtilities.from(csvLocalWeatherData.getDate(), csvLocalWeatherData.getTime(), ZoneOffset.ofHours(csvStationData.getTimeZone()));
        elasticLocalWeatherData.station = elasticStation;

        // Get the Converter Result:
        elastic.model.LocalWeatherData elasticConverterResult = LocalWeatherDataConverter.convert(csvLocalWeatherData, csvStationData);

        Assert.assertNotEquals(null, elasticConverterResult);

        Assert.assertEquals(elasticLocalWeatherData.windSpeed, elasticConverterResult.windSpeed);
        Assert.assertEquals(elasticLocalWeatherData.temperature, elasticConverterResult.temperature);
        Assert.assertEquals(elasticLocalWeatherData.stationPressure, elasticConverterResult.stationPressure);
        Assert.assertEquals(elasticLocalWeatherData.skyCondition, elasticConverterResult.skyCondition);
        Assert.assertEquals(elasticLocalWeatherData.dateTime, elasticConverterResult.dateTime);

        Assert.assertNotEquals(null, elasticConverterResult.station);

        Assert.assertEquals(elasticLocalWeatherData.station.wban, elasticConverterResult.station.wban);
        Assert.assertEquals(elasticLocalWeatherData.station.name, elasticConverterResult.station.name);
        Assert.assertEquals(elasticLocalWeatherData.station.state, elasticConverterResult.station.state);
        Assert.assertEquals(elasticLocalWeatherData.station.location, elasticConverterResult.station.location);

        Assert.assertNotEquals(null, elasticLocalWeatherData.station.geoLocation );
        Assert.assertEquals(elasticLocalWeatherData.station.geoLocation.lat, elasticLocalWeatherData.station.geoLocation.lat, 1e-10);
        Assert.assertEquals(elasticLocalWeatherData.station.geoLocation.lon, elasticLocalWeatherData.station.geoLocation.lon, 1e-10);


        // Elastic Weather Data:


    }
}
