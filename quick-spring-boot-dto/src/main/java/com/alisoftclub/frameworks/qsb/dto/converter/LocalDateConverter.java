/*
 */
package com.alisoftclub.frameworks.qsb.dto.converter;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author mohammad
 */
public class LocalDateConverter implements DtoPropertyConverter<String, LocalDate>{

    @Override
    public String toJson(LocalDate r) {
        return Objects.isNull(r) ? null : String.format("%d-%d-%d", r.getYear(), r.getMonthValue(), r.getDayOfMonth());
    }

    @Override
    public LocalDate toProperty(String f) {
        if(Objects.isNull(f) || f.isEmpty()){
            return null;
        }
        List<String> list = Arrays.asList(f.split("T")[0].split("-"));
        return LocalDate.of(Integer.valueOf(list.get(0)), Integer.valueOf(list.get(1)), Integer.valueOf(list.get(2)));
    }
    
}
