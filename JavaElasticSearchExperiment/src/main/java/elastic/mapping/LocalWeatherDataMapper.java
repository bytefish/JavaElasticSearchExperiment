// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package elastic.mapping;

import org.elasticsearch.Version;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.ToXContent;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.elasticsearch.index.mapper.ContentPath;
import org.elasticsearch.index.mapper.Mapper;
import org.elasticsearch.index.mapper.Mapping;
import org.elasticsearch.index.mapper.Mapping.SourceTransform;
import org.elasticsearch.index.mapper.MetadataFieldMapper;
import org.elasticsearch.index.mapper.core.DateFieldMapper;
import org.elasticsearch.index.mapper.core.FloatFieldMapper;
import org.elasticsearch.index.mapper.core.StringFieldMapper;
import org.elasticsearch.index.mapper.geo.GeoPointFieldMapper;
import org.elasticsearch.index.mapper.object.ObjectMapper;
import org.elasticsearch.index.mapper.object.RootObjectMapper;

import java.io.IOException;

public class LocalWeatherDataMapper {

    private static final String INDEX_TYPE = "mapping";

    public String getMapping() throws IOException {

        RootObjectMapper.Builder rootObjectMapperBuilder = new RootObjectMapper.Builder(INDEX_TYPE)
                .add(new DateFieldMapper.Builder("dateTime").store(true))
                .add(new FloatFieldMapper.Builder("temperature").store(true))
                .add(new FloatFieldMapper.Builder("windSpeed").store(true))
                .add(new FloatFieldMapper.Builder("stationPressure").store(true))
                .add(new StringFieldMapper.Builder("skyCondition").store(true))
                .add(new ObjectMapper.Builder("station")
                        .add(new StringFieldMapper.Builder("wban").store(true))
                        .add(new StringFieldMapper.Builder("name").store(true))
                        .add(new StringFieldMapper.Builder("state").store(true))
                        .add(new StringFieldMapper.Builder("location").store(true))
                        .add(new GeoPointFieldMapper.Builder("geoPoint").store(true))
                        .nested(ObjectMapper.Nested.newNested(true, false)));

        Settings settings = Settings.builder()
                .put(IndexMetaData.SETTING_VERSION_CREATED, 1)
                .put(IndexMetaData.SETTING_CREATION_DATE, System.currentTimeMillis())
                .build();

        Mapping mapping = new Mapping(
                Version.fromString("1.0.0"),
                rootObjectMapperBuilder.build(new Mapper.BuilderContext(settings, new ContentPath())),
                new MetadataFieldMapper[] {},
                new SourceTransform[] {},
                null);

        return mapping.toXContent(JsonXContent.contentBuilder(), ToXContent.EMPTY_PARAMS).string();
    }
}
