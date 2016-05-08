// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class JsonUtilities {

    private static final Logger logger = LogManager.getLogger(JsonUtilities.class);

    public static <TEntity> Optional<byte[]> convertJsonToBytes(ObjectMapper mapper, TEntity entity) {
        try {

            return Optional.empty().of(mapper.writeValueAsBytes(entity));
        } catch(JsonProcessingException e) {
            if(logger.isErrorEnabled()) {
                logger.error(String.format("Failed to convert entity %s to JSON", entity), e);
            }
        }
        return Optional.empty();
    }
}