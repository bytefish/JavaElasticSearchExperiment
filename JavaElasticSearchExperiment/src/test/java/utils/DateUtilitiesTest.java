// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package utils;

import org.junit.Assert;
import org.junit.Test;

import java.time.*;
import java.util.Date;

public class DateUtilitiesTest {

    @Test
    public void generated_date_has_utc_offset_when_none_is_given() throws Exception {
        LocalDate localDate = LocalDate.of(2013, 1, 1);
        LocalTime localTime = LocalTime.of(0, 30, 0, 0);

        LocalDateTime dateTime = localDate.atTime(localTime);
        OffsetDateTime zonedDateTime = dateTime.atOffset(ZoneOffset.UTC);

        Date expectedDate = DateUtilities.from(localDate, localTime);

        Assert.assertEquals(zonedDateTime.toInstant(), expectedDate.toInstant());
    }

    @Test
    public void generated_date_has_given_offset_when_offset_is_given() throws Exception {
        LocalDate localDate = LocalDate.of(2013, 1, 1);
        LocalTime localTime = LocalTime.of(0, 30, 0, 0);

        ZoneOffset offset = ZoneOffset.ofHours(1);
        LocalDateTime dateTime = localDate.atTime(localTime);
        OffsetDateTime zonedDateTime = dateTime.atOffset(offset);

        Date expectedDate = DateUtilities.from(localDate, localTime, offset);

        Assert.assertEquals(zonedDateTime.toInstant(), expectedDate.toInstant());
    }


    @Test
    public void generated_date_has_given_timezone_when_given_localdatetime_and_timezone()  throws Exception {
        LocalDate localDate = LocalDate.of(2013, 1, 1);
        LocalTime localTime = LocalTime.of(0, 30, 0, 0);


        LocalDateTime dateTime = localDate.atTime(localTime);

        ZoneOffset offset = ZoneOffset.ofHours(1);

        OffsetDateTime zonedDateTime = dateTime.atOffset(offset);

        Date expectedDate = DateUtilities.from(dateTime, offset);

        Assert.assertEquals(zonedDateTime.toInstant(), expectedDate.toInstant());
    }
}
