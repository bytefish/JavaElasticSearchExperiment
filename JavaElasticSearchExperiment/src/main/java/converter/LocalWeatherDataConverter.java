// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package converter;


import elastic.model.Station;
import org.elasticsearch.common.geo.GeoPoint;

public class LocalWeatherDataConverter {

    public static elastic.model.LocalWeatherData convert(csv.model.LocalWeatherData csvLocalWeatherData, csv.model.Station csvStation) {

        elastic.model.LocalWeatherData elasticLocalWeatherData = new elastic.model.LocalWeatherData();

        elasticLocalWeatherData.dateTime = csvLocalWeatherData.getDate().atTime(csvLocalWeatherData.getTime());
        elasticLocalWeatherData.skyCondition = csvLocalWeatherData.getSkyCondition();
        elasticLocalWeatherData.stationPressure = csvLocalWeatherData.getStationPressure();
        elasticLocalWeatherData.temperature = csvLocalWeatherData.getDryBulbCelsius();
        elasticLocalWeatherData.windSpeed = csvLocalWeatherData.getWindSpeed();

        // Convert the Station data:
        elasticLocalWeatherData.station = convert(csvStation);

        return elasticLocalWeatherData;
    }

    public static elastic.model.Station convert(csv.model.Station csvStation) {
        elastic.model.Station elasticStation = new elastic.model.Station();

        elasticStation.wban = csvStation.getWban();
        elasticStation.name = csvStation.getName();
        elasticStation.state = csvStation.getState();
        elasticStation.location = csvStation.getLocation();
        elasticStation.geoPoint = new GeoPoint(csvStation.getLatitude(), csvStation.getLongitude());

        return elasticStation;
    }
}
