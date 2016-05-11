// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package elastic.mapping;

import org.elasticsearch.common.xcontent.XContentBuilder;

public interface IObjectMapping {

    XContentBuilder getMapping();

    String getIndexType();

}
