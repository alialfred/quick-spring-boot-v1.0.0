/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alisoftclub.frameworks.qsb.dto.converter;

import com.alisoftclub.frameworks.qsb.dto.Dto;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 *
 * @author usman
 */
public class DtoConverter<E> implements DtoPropertyConverter<Map<String, Object>, E> {

    private final Supplier<Dto<E>> supplier;

    public DtoConverter(Supplier<Dto<E>> supplier) {
        this.supplier = supplier;
    }

    @Override
    public Map<String, Object> toJson(E entity) {
        if(Objects.isNull(entity)){
            return null;
        }
        Dto<E> dto = this.supplier.get();
        dto.setEntity(entity);
        return dto.getValues();
    }

    @Override
    public E toProperty(Map<String, Object> f) {
        Dto<E> dto = this.supplier.get();
        f.forEach(dto::setValue);
        return dto.getEntity();
    }

}
