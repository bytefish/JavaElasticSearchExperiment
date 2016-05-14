// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package csv.mapping;

import csv.model.Station;
import de.bytefish.jtinycsvparser.builder.IObjectCreator;
import de.bytefish.jtinycsvparser.mapping.CsvMapping;

public class StationMapper extends CsvMapping<Station>
{
    public StationMapper(IObjectCreator creator)
    {
        super(creator);

        MapProperty(0, String.class, Station::setWban);
        MapProperty(1, String.class, Station::setWmo);
        MapProperty(2, String.class, Station::setCallSign);
        MapProperty(3, String.class, Station::setClimateDivisionCode);
        MapProperty(4, String.class, Station::setClimateDivisionStateCode);
        MapProperty(5, String.class, Station::setClimateDivisionStationCode);
        MapProperty(6, String.class, Station::setName);
        MapProperty(7, String.class, Station::setState);
        MapProperty(8, String.class, Station::setLocation);
        MapProperty(9, Float.class, Station::setLatitude);
        MapProperty(10, Float.class, Station::setLongitude);
        MapProperty(11, Integer.class, Station::setGroundHeight);
        MapProperty(12, Integer.class, Station::setStationHeight);
        MapProperty(13, Integer.class, Station::setBarometer);
        MapProperty(14, Integer.class, Station::setTimeZone);
    }
}