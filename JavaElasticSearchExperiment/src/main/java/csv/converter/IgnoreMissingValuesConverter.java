// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package csv.converter;

import de.bytefish.jtinycsvparser.typeconverter.ITypeConverter;

import java.lang.reflect.Type;

public class IgnoreMissingValuesConverter implements ITypeConverter<Float> {

    @Override
    public Float convert(String s) {
        if(s.equals("") || s.equalsIgnoreCase("m")) {
            return null;
        }
        return Float.parseFloat(s);
    }

    @Override
    public Type getTargetType() {
        return Float.class;
    }
}
