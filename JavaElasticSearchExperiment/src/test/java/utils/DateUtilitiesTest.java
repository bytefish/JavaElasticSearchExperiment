// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package utils;

import org.junit.Assert;
import org.junit.Test;

import java.time.*;
import java.util.Date;

public class DateUtilitiesTest {

    @Test
    public void generated_date_has_default_timezone_when_no_timezone_is_used() throws Exception {
        LocalDate localDate = LocalDate.of(2013, 1, 1);
        LocalTime localTime = LocalTime.of(0, 30, 0, 0);

        LocalDateTime dateTime = localDate.atTime(localTime);
        ZonedDateTime zonedDateTime = dateTime.atZone(ZoneId.systemDefault());

        Date expectedDate = DateUtilities.from(localDate, localTime);

        Assert.assertEquals(zonedDateTime.toInstant(), expectedDate.toInstant());
    }

    @Test
    public void generated_date_has_given_timezone_when_timezone_is_given() throws Exception {
        LocalDate localDate = LocalDate.of(2013, 1, 1);
        LocalTime localTime = LocalTime.of(0, 30, 0, 0);

        ZoneId losAngelesTimeZone = ZoneId.of("America/Los_Angeles");

        LocalDateTime dateTime = localDate.atTime(localTime);
        ZonedDateTime zonedDateTime = dateTime.atZone(losAngelesTimeZone);

        Date expectedDate = DateUtilities.from(localDate, localTime, losAngelesTimeZone);

        Assert.assertEquals(zonedDateTime.toInstant(), expectedDate.toInstant());
    }

    @Test
    public void generated_date_has_default_timezone_when_only_localdatetime() throws Exception {
        LocalDate localDate = LocalDate.of(2013, 1, 1);
        LocalTime localTime = LocalTime.of(0, 30, 0, 0);

        LocalDateTime dateTime = localDate.atTime(localTime);
        ZonedDateTime zonedDateTime = dateTime.atZone(ZoneId.systemDefault());

        Date expectedDate = DateUtilities.from(dateTime);

        Assert.assertEquals(zonedDateTime.toInstant(), expectedDate.toInstant());
    }

    @Test
    public void generated_date_has_given_timezone_when_given_localdatetime_and_timezone()  throws Exception {
        LocalDate localDate = LocalDate.of(2013, 1, 1);
        LocalTime localTime = LocalTime.of(0, 30, 0, 0);

        ZoneId losAngelesTimeZone = ZoneId.of("America/Los_Angeles");

        LocalDateTime dateTime = localDate.atTime(localTime);
        ZonedDateTime zonedDateTime = dateTime.atZone(losAngelesTimeZone);

        Date expectedDate = DateUtilities.from(dateTime, losAngelesTimeZone);

        Assert.assertEquals(zonedDateTime.toInstant(), expectedDate.toInstant());
    }
}
