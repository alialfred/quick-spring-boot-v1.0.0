/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alisoftclub.frameworks.qsb.dto.converter;

import com.alisoftclub.frameworks.qsb.dto.Dto;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 *
 * @author usman
 */
public class DtoListConverter<E> implements DtoPropertyConverter<List<Map<String, Object>>, List<E>> {

    private final Supplier<Dto<E>> supplier;

    public DtoListConverter(Supplier<Dto<E>> supplier) {
        this.supplier = supplier;
    }

    @Override
    public List<Map<String, Object>> toJson(List<E> entity) {
        return entity.stream().map(this::toMap).collect(Collectors.toList());
    }

    @Override
    public List<E> toProperty(List<Map<String, Object>> f) {
        return f.stream().map(this::toE).collect(Collectors.toList());
    }

    private Map<String, Object> toMap(E entity) {
        Dto<E> dto = this.supplier.get();
        dto.setEntity(entity);
        return dto.getValues();
    }
    
    private E toE(Map<String,Object> map){
        Dto<E> dto = this.supplier.get();
        map.forEach(dto::setValue);
        return dto.getEntity();
    }
}
