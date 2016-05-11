// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package elastic.mapping;

import elastic.exceptions.GetMappingFailedException;
import org.elasticsearch.Version;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.ToXContent;
import org.elasticsearch.common.xcontent.XContentBuilder;
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

public class LocalWeatherDataMapper implements IObjectMapping {

    private static final String INDEX_TYPE = "document";

    public XContentBuilder getMapping() {
        try {
            return internalGetMapping();
        } catch(Exception e) {
            throw new GetMappingFailedException(INDEX_TYPE, e);
        }
    }

    public String getIndexType() {
        return INDEX_TYPE;
    }

    public XContentBuilder internalGetMapping() throws IOException {

        RootObjectMapper.Builder rootObjectMapperBuilder = new RootObjectMapper.Builder(INDEX_TYPE)
                .add(new DateFieldMapper.Builder("dateTime"))
                .add(new FloatFieldMapper.Builder("temperature"))
                .add(new FloatFieldMapper.Builder("windSpeed"))
                .add(new FloatFieldMapper.Builder("stationPressure"))
                .add(new StringFieldMapper.Builder("skyCondition"))
                .add(new ObjectMapper.Builder("station")
                        .add(new StringFieldMapper.Builder("wban"))
                        .add(new StringFieldMapper.Builder("name"))
                        .add(new StringFieldMapper.Builder("state"))
                        .add(new StringFieldMapper.Builder("location"))
                        .add(new GeoPointFieldMapper.Builder("coordinates")
                                .enableLatLon(true)
                                .enableGeoHash(false))
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

        return mapping.toXContent(JsonXContent.contentBuilder().startObject(), ToXContent.EMPTY_PARAMS);
    }
}
