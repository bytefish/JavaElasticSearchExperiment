// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package csv.parser;

import csv.mapping.LocalWeatherDataMapper;
import csv.mapping.StationMapper;
import csv.model.LocalWeatherData;
import csv.model.Station;
import de.bytefish.jtinycsvparser.CsvParser;
import de.bytefish.jtinycsvparser.CsvParserOptions;
import de.bytefish.jtinycsvparser.tokenizer.StringSplitTokenizer;

public class Parsers {

    public static CsvParser<Station> StationParser() {

        return new CsvParser<>(new CsvParserOptions(true, new StringSplitTokenizer("\\|", true)), new StationMapper(() -> new Station()));
    }

    public static CsvParser<LocalWeatherData> LocalWeatherDataParser()
    {
        return new CsvParser<>(new CsvParserOptions(true, new StringSplitTokenizer(",", true)), new LocalWeatherDataMapper(() -> new LocalWeatherData()));
    }

}
