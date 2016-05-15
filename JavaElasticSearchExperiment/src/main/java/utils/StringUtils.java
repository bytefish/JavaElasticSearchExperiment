// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package utils;

public class StringUtils {

    private StringUtils() {}

    public static String StringEmpty = "";

    public static boolean isNullOrWhiteSpace(String input) {
        return  input == null || input.equals(StringEmpty) || input.trim().length() == 0;
    }

}
