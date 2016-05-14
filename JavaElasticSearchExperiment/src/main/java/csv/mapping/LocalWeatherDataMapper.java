// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package csv.mapping;

import csv.converter.IgnoreMissingValuesConverter;
import csv.model.LocalWeatherData;
import de.bytefish.jtinycsvparser.builder.IObjectCreator;
import de.bytefish.jtinycsvparser.mapping.CsvMapping;
import de.bytefish.jtinycsvparser.typeconverter.LocalDateConverter;
import de.bytefish.jtinycsvparser.typeconverter.LocalTimeConverter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalWeatherDataMapper extends CsvMapping<LocalWeatherData>
{
    public LocalWeatherDataMapper(IObjectCreator creator)
    {
        super(creator);

        MapProperty(0, String.class, LocalWeatherData::setWban);
        MapProperty(1, LocalDate.class, LocalWeatherData::setDate, new LocalDateConverter(DateTimeFormatter.ofPattern("yyyyMMdd")));
        MapProperty(2, LocalTime.class, LocalWeatherData::setTime, new LocalTimeConverter(DateTimeFormatter.ofPattern("HHmm")));
        MapProperty(4, String.class, LocalWeatherData::setSkyCondition);
        MapProperty(12, Float.class, LocalWeatherData::setDryBulbCelsius, new IgnoreMissingValuesConverter("M"));
        MapProperty(24, Float.class, LocalWeatherData::setWindSpeed, new IgnoreMissingValuesConverter("M"));
        MapProperty(30, Float.class, LocalWeatherData::setStationPressure, new IgnoreMissingValuesConverter("M"));
    }
}