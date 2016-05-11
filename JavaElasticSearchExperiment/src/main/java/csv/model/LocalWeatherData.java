// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package csv.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class LocalWeatherData {

    private String wban;

    private LocalDate date;

    private LocalTime time;

    private String skyCondition;

    private Float dryBulbCelsius;

    private Float windSpeed;

    private Float stationPressure;

    public LocalWeatherData() {

    }

    public String getWban() {
        return wban;
    }

    public void setWban(String wban) {
        this.wban = wban;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getSkyCondition() {
        return skyCondition;
    }

    public void setSkyCondition(String skyCondition) {
        this.skyCondition = skyCondition;
    }

    public Float getDryBulbCelsius() {
        return dryBulbCelsius;
    }

    public void setDryBulbCelsius(Float dryBulbCelsius) {
        this.dryBulbCelsius = dryBulbCelsius;
    }

    public Float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Float getStationPressure() {
        return stationPressure;
    }

    public void setStationPressure(Float stationPressure) {
        this.stationPressure = stationPressure;
    }
}
