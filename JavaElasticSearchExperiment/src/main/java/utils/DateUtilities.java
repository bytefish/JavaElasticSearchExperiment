// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.OffsetDateTime;
import java.util.Date;

public class DateUtilities {

    public static Date from(LocalDate localDate, LocalTime localTime) {
        return from(localDate, localTime, ZoneOffset.UTC);
    }

    public static Date from(LocalDate localDate, LocalTime localTime, ZoneOffset zoneOffset) {
        LocalDateTime localDateTime = localDate.atTime(localTime);

        return from(localDateTime, zoneOffset);
    }

    public static Date from(LocalDateTime localDateTime, ZoneOffset zoneOffset) {
        OffsetDateTime zdt = localDateTime.atOffset(zoneOffset);

        return Date.from(zdt.toInstant());
    }

}
