// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package elastic.utils;

import elastic.exceptions.CreateIndexFailedException;
import elastic.exceptions.IndicesExistsFailedException;
import elastic.exceptions.PutMappingFailedException;
import org.elasticsearch.client.Client;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import utils.TestUtils;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ElasticSearchUtilsTest {

    @Mock
    private Client client;

    @Before
    public void setup() {
        initMocks(this);
    }

    @Test
    public void indices_exist_throws_correct_exception_when_underlying_client_throws() throws Exception {

        when(client.admin()).thenThrow(new RuntimeException());

        TestUtils.assertThrows(() -> ElasticSearchUtils.indexExist(client, ""), IndicesExistsFailedException.class);
    }

    @Test
    public void create_index_throws_correct_exception_when_underlying_client_throws() throws Exception {

        when(client.admin()).thenThrow(new RuntimeException());

        TestUtils.assertThrows(() -> ElasticSearchUtils.createIndex(client, ""), CreateIndexFailedException.class);
    }


    @Test
    public void put_mapping_throws_correct_exception_when_underlying_client_throws() throws Exception {

        when(client.admin()).thenThrow(new RuntimeException());

        TestUtils.assertThrows(() -> ElasticSearchUtils.putMapping(client, "", null), PutMappingFailedException.class);
    }
}
