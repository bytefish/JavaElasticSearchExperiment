// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package elastic.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeoLocation {

    @JsonProperty("lat")
    public double lat;

    @JsonProperty("lon")
    public double lon;

    public GeoLocation() {}

    public GeoLocation(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }
}
