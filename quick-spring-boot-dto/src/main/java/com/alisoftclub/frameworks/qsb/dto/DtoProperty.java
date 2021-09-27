/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alisoftclub.frameworks.qsb.dto;

import com.alisoftclub.frameworks.qsb.dto.converter.DefaultPropertyConverter;
import com.alisoftclub.frameworks.qsb.dto.converter.DtoPropertyConverter;
import java.util.Objects;
import org.springframework.expression.Expression;

/**
 *
 * @author Ali Imran
 */
public class DtoProperty {

    private String name;
    private String jsonName;
    private final Expression expression;
    private final DtoPropertyConverter converter;
    private boolean id;

    public DtoProperty(String name, Expression expression, DtoPropertyConverter converter) {
        this.name = name;
        this.expression = expression;
        this.converter = Objects.isNull(converter) ?  new DefaultPropertyConverter(): converter;
    }

    public boolean isId() {
        return id;
    }

    public void setId(boolean id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJsonName() {
        return jsonName;
    }

    public void setJsonName(String jsonName) {
        this.jsonName = jsonName;
    }

    public <T> T getValue(Object entity) {
        return (T) converter.toJson(expression.getValue(entity));
    }

    public void setValue(Object entity, Object value) {
        if (expression.isWritable(entity)) {
            expression.setValue(entity, converter.toProperty(value));
        }
    }

    public boolean isNonNull(Object entity) {
        return Objects.nonNull(entity) && Objects.nonNull(getValue(entity));
    }

}
