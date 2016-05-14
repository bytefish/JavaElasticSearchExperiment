// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package csv.converter;

import org.junit.Assert;
import org.junit.Test;

public class IgnoreMissingValuesConverterTest {
    @Test
    public void returns_null_if_value_is_missing() throws Exception {

        IgnoreMissingValuesConverter converter = new IgnoreMissingValuesConverter("M", "m");

        Assert.assertEquals(null, converter.convert("M"));
        Assert.assertEquals(null, converter.convert("m"));

        Assert.assertEquals(1.0f, converter.convert("1.0"), 1e-3);

    }
}
