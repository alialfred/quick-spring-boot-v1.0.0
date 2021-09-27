/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alisoftclub.frameworks.qsb.dto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.lang.NonNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @param <E>
 * @author Ali Imran
 */
@SuppressWarnings("unused")
@JsonIgnoreProperties
public abstract class Dto<E> {

    @JsonIgnore
    protected E entity;
    @JsonIgnore
    protected Map<String, DtoProperty> properties;
    @JsonIgnore
    private Map<String, Object> extraValues;
    @JsonIgnore
    private DtoProperty idProperty;

    public Dto(@NonNull E entity, @NonNull DtoPropertyBuilder builder) {
        this.entity = entity;
        this.properties = builder.build();
        Optional<DtoProperty> first = this.properties.values().stream().filter(DtoProperty::isId).findFirst();
        first.ifPresent(p -> this.idProperty = p);
        this.extraValues = new HashMap<>(0);
    }

    public E getEntity() {
        return entity;
    }

    public void setEntity(E entity) {
        this.entity = entity;
    }

    @JsonIgnore()
    public <T> T getId() {
        return Objects.isNull(this.idProperty) ? null : this.idProperty.getValue(entity);
    }

    @SuppressWarnings("unchecked")
    public <T> T getValue(String jsonName) {
        if (properties.containsKey(jsonName)) {
            return properties.get(jsonName).getValue(entity);
        }
        return (T) this.extraValues.get(jsonName);
    }

    public boolean hasValue(String jsonName) {
        if (properties.containsKey(jsonName)) {
            return Objects.nonNull(properties.get(jsonName).getValue(entity));
        }
        return this.extraValues.containsKey(jsonName);
    }

    @JsonAnySetter
    public void setValue(String jsonName, Object value) {
        if (!handleJsonValue(jsonName, value)) {
            if (properties.containsKey(jsonName)) {
                properties.get(jsonName).setValue(entity, value);
            } else {
                this.extraValues.put(jsonName, value);
            }
        }
    }

    public void copyValues(Dto dto) {
        this.copyValues(dto, false);
    }

    public void copyValues(Dto dto, boolean copyExtra) {
        this.copyValues(dto.getValues());
        if (copyExtra) {
            this.copyValues(dto.extraValues);
        }
    }

    public void copyValues(Map<String, Object> values) {
        for (String jsonName : values.keySet()) {
            Object value = values.get(jsonName);
            if (!handleJsonValue(jsonName, value)) {
                if (properties.containsKey(jsonName)) {
                    properties.get(jsonName).setValue(entity, value);
                } else {
                    this.extraValues.put(jsonName, value);
                }
            }
        }

    }

    @JsonAnyGetter
    public Map<String, Object> getValues() {
        return this.properties.entrySet().stream()
                .filter(e -> e.getValue().isNonNull(entity))
                .collect(Collectors.toMap(Map.Entry::getKey,
                                this::toValue, (o, n) -> n, LinkedHashMap::new));
    }

    private Object toValue(Map.Entry<String, DtoProperty> entry) {
        DtoProperty property = entry.getValue();
        return property.getValue(entity);
    }

    protected abstract boolean handleJsonValue(String jsonName, Object value);

    public static <E, D extends Dto<E>> List<D> toDto(DtoSupplier<D, E> supplier, List<E> data) {
        return data.stream().map(supplier::get).collect(Collectors.toList());
    }

    public static <E, D extends Dto<E>> List<E> toEntity(List<D> dtoList) {
        return dtoList.stream().map(Dto::getEntity).collect(Collectors.toList());
    }
}
