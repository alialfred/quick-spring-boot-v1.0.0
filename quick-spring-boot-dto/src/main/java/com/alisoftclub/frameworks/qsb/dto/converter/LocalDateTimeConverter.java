/*
 */
package com.alisoftclub.frameworks.qsb.dto.converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author mohammad
 */
public class LocalDateTimeConverter implements DtoPropertyConverter<String, LocalDateTime>{

    @Override
    public String toJson(LocalDateTime r) {
        return Objects.isNull(r) ? null : String.format("%d-%d-%dT%d:%d:%d", r.getYear(), r.getMonthValue(), r.getDayOfMonth(), r.getHour(), r.getMinute(), r.getSecond());
    }

    @Override
    public LocalDateTime toProperty(String f) {
        if(Objects.isNull(f) || f.isEmpty()){
            return null;
        }
        List<String> list = Arrays.asList(f.split("T")[0].split("-"));
        List<String> times = Arrays.asList(f.split("T")[1].split(":"));
        return LocalDateTime.of(
                Integer.valueOf(list.get(0)),
                Integer.valueOf(list.get(1)),
                Integer.valueOf(list.get(2)),
                Integer.valueOf(times.get(0)),
                Integer.valueOf(times.get(1)),
                Integer.valueOf(times.get(2))
        );
    }
    
}
