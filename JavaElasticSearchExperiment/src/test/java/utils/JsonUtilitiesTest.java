// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class JsonUtilitiesTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    public class TestEntity {

        @JsonProperty(value = "val")
        public String val;

    }

    @Test
    public void json_is_generated_when_serialization_succeeds() throws Exception {

        TestEntity entity = new TestEntity();

        entity.val = "Test";

        Optional<byte[]> resultBytes = JsonUtilities.convertJsonToBytes(mapper, entity);

        Assert.assertEquals(true, resultBytes.isPresent());
        Assert.assertEquals("{\"val\":\"Test\"}", new String(resultBytes.get()));
    }
}
