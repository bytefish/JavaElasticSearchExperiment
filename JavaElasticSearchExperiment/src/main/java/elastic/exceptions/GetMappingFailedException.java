// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package elastic.exceptions;

public class GetMappingFailedException extends RuntimeException {
    public GetMappingFailedException(String indexName, Throwable cause) {
        super(String.format("Create Mapping failed for Index '%s'", indexName), cause);
    }
}
