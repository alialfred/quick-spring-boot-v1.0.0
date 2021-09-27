/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alisoftclub.frameworks.qsb.dto.converter;

import com.alisoftclub.frameworks.qsb.dto.DefaultDto;
import com.alisoftclub.frameworks.qsb.dto.Dto;
import com.alisoftclub.frameworks.qsb.dto.DtoPropertyBuilder;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 *
 * @author usman
 */
public class DefaultDtoConverter<E> implements DtoPropertyConverter<Map<String, Object>, E> {

    private final Supplier<E> supplier;
    private final DtoPropertyBuilder builder;

    public DefaultDtoConverter(Supplier<E> supplier, DtoPropertyBuilder builder) {
        this.supplier = supplier;
        this.builder = builder;
    }

    @Override
    public Map<String, Object> toJson(E entity) {
        if(Objects.isNull(entity)){
            return null;
        }
        Dto<E> dto = new DefaultDto<>(entity, this.builder);
        dto.setEntity(entity);
        return dto.getValues();
    }

    @Override
    public E toProperty(Map<String, Object> f) {
        E entity = this.supplier.get();
        Dto<E> dto = new DefaultDto<>(entity, this.builder);
        f.forEach(dto::setValue);
        return dto.getEntity();
    }

}
