// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package csv.model;

public class Station {

    private String wban;

    private String wmo;

    private String callSign;

    private String climateDivisionCode;

    private String climateDivisionStateCode;

    private String climateDivisionStationCode;

    private String name;

    private String state;

    private String location;

    private Float latitude;

    private Float longitude;

    private Integer groundHeight;

    private Integer stationHeight;

    private Integer barometer;

    private Integer timeZone;

    public Station() {
    }

    public String getWban() {
        return wban;
    }

    public void setWban(String wban) {
        this.wban = wban;
    }

    public String getWmo() {
        return wmo;
    }

    public void setWmo(String wmo) {
        this.wmo = wmo;
    }

    public String getCallSign() {
        return callSign;
    }

    public void setCallSign(String callSign) {
        this.callSign = callSign;
    }

    public String getClimateDivisionCode() {
        return climateDivisionCode;
    }

    public void setClimateDivisionCode(String climateDivisionCode) {
        this.climateDivisionCode = climateDivisionCode;
    }

    public String getClimateDivisionStateCode() {
        return climateDivisionStateCode;
    }

    public void setClimateDivisionStateCode(String climateDivisionStateCode) {
        this.climateDivisionStateCode = climateDivisionStateCode;
    }

    public String getClimateDivisionStationCode() {
        return climateDivisionStationCode;
    }

    public void setClimateDivisionStationCode(String climateDivisionStationCode) {
        this.climateDivisionStationCode = climateDivisionStationCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Integer getGroundHeight() {
        return groundHeight;
    }

    public void setGroundHeight(Integer groundHeight) {
        this.groundHeight = groundHeight;
    }

    public Integer getStationHeight() {
        return stationHeight;
    }

    public void setStationHeight(Integer stationHeight) {
        this.stationHeight = stationHeight;
    }

    public Integer getBarometer() {
        return barometer;
    }

    public void setBarometer(Integer barometer) {
        this.barometer = barometer;
    }

    public Integer getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(Integer timeZone) {
        this.timeZone = timeZone;
    }
}
