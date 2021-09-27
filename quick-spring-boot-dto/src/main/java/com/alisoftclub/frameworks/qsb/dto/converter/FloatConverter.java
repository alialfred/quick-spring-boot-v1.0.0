package com.alisoftclub.frameworks.qsb.dto.converter;

import java.util.Objects;

public class FloatConverter implements DtoPropertyConverter<Object, Float> {
    @Override
    public Float toJson(Float aFloat) {
        return toProperty(aFloat);
    }

    @Override
    public Float toProperty(Object object) {
        return Float.valueOf(String.format("%.04f", toFloat(object)));
    }

    private float toFloat(Object object) {
        return Objects.isNull(object) ? 0F : Float.valueOf(object.toString());
    }

}
