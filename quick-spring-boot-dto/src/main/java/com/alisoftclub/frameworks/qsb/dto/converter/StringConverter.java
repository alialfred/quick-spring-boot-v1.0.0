package com.alisoftclub.frameworks.qsb.dto.converter;

import java.util.Objects;

public class StringConverter implements DtoPropertyConverter<Object, String> {
    @Override
    public String toJson(String aFloat) {
        return toProperty(aFloat);
    }

    @Override
    public String toProperty(Object object) {
        return toString(object);
    }

    private String toString(Object object) {
        return Objects.isNull(object) ? "" : object.toString();
    }

}
