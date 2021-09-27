/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alisoftclub.frameworks.qsb.dto;

/**
 *
 * @author Ali Imran
 * @param <JsonValue> JSON Value
 * @param <PropertyValue> Property Value
 */
public interface PropertyConverter<JsonValue, PropertyValue> {

    public JsonValue toJsonValue(PropertyValue propertyValue);

    public PropertyValue toPropertyValue(JsonValue jsonValue);
}
