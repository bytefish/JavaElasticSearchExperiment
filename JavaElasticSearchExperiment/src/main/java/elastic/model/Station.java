// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package elastic.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.elasticsearch.common.geo.GeoPoint;

public class Station {

    @JsonProperty("wban")
    public String wban;

    @JsonProperty("name")
    public String name;

    @JsonProperty("state")
    public String state;

    @JsonProperty("location")
    public String location;

    @JsonProperty("coordinates")
    public GeoLocation geoLocation;

}
