// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package elastic.exceptions;

public class IndicesExistsFailedException extends RuntimeException {
    public IndicesExistsFailedException(String indexName, Throwable cause) {
        super(String.format("Indices '%s' failed", indexName), cause);
    }
}
