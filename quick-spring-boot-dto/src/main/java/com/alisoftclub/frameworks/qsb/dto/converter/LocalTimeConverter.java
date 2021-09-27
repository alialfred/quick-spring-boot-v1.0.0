/*
 */
package com.alisoftclub.frameworks.qsb.dto.converter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author mohammad
 */
public class LocalTimeConverter implements DtoPropertyConverter<String, LocalTime>{

    @Override
    public String toJson(LocalTime r) {
        return Objects.isNull(r) ? null : String.format("%d:%d:%d", r.getHour(), r.getMinute(), r.getSecond());
    }

    @Override
    public LocalTime toProperty(String f) {
        String time = f;
        if(Objects.isNull(time) || time.isEmpty()){
            return null;
        }
        int index = time.lastIndexOf('.');
        if(index >= 0 && index < time.length()){
            time = time.substring(0, index);
        }
        List<String> times = Arrays.asList(time.split(":"));
        return LocalTime.of(
                Integer.valueOf(times.get(0)),
                Integer.valueOf(times.get(1)),
                Integer.valueOf(times.get(2))
        );
    }
    
}
