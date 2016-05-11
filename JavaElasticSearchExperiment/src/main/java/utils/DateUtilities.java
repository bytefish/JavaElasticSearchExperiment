// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package utils;

import java.time.*;
import java.util.Date;

public class DateUtilities {

    public static Date from(LocalDate localDate, LocalTime localTime) {
        return from(localDate, localTime, ZoneId.systemDefault());
    }

    public static Date from(LocalDate localDate, LocalTime localTime, ZoneId zoneId) {
        LocalDateTime localDateTime = localDate.atTime(localTime);

        return from(localDateTime, zoneId);
    }

    public static Date from(LocalDateTime localDateTime) {
        return from(localDateTime, ZoneId.systemDefault());
    }

    public static Date from(LocalDateTime localDateTime, ZoneId zoneId) {
        ZonedDateTime zdt = localDateTime.atZone(zoneId);

        return Date.from(zdt.toInstant());
    }
}
