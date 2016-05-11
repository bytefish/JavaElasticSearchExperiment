// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package elastic.exceptions;

import elastic.mapping.IObjectMapping;

public class CreateIndexFailedException extends RuntimeException {
    public CreateIndexFailedException(String indexName, Throwable cause) {
        super(String.format("Creating Index '%s' failed", indexName), cause);
    }
}
