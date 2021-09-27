/*
 * Copyright 2018 Ali Imran.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alisoftclub.frameworks.qsb.dto;

import com.alisoftclub.frameworks.qsb.dto.converter.DtoPropertyConverter;

import java.util.*;

import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * @author Ali Imran
 */
public class DtoPropertyBuilder {

    private final SpelParserConfiguration config = new SpelParserConfiguration(true, true);
    private final SpelExpressionParser parser = new SpelExpressionParser(config);
    private final Map<String, DtoProperty> properties = new LinkedHashMap(0);

    private DtoPropertyBuilder(String... properties) {
        for (String s : properties) {
            String splats[] = s.split(":");
            DtoPropertyBuilder.this.add(splats[0], splats.length > 1 ? splats[1] : splats[0]);
        }
    }

    public DtoPropertyBuilder add(String name) {
        return this.add(name, name);
    }

    public DtoPropertyBuilder add(String name, DtoPropertyConverter converter) {
        return this.add(name, name, converter);
    }

    public DtoPropertyBuilder add(String name, String jsonName) {
        return this.add(name, jsonName, null);
    }

    public DtoPropertyBuilder add(String name, String jsonName, DtoPropertyConverter converter) {
        try {
            DtoProperty property = new DtoProperty(name, parser.parseExpression(name), converter);
            property.setJsonName(jsonName);
            properties.put(jsonName, property);
        } catch (Exception exc) {
        }
        return this;
    }

    public DtoPropertyBuilder id() {
        if(this.properties.isEmpty()){
            return this;
        }
        this.properties.values().forEach(p -> p.setId(false));
        Collection<DtoProperty> values = this.properties.values();
        DtoProperty[] dtoProperties = values.toArray(new DtoProperty[0]);
        DtoProperty dtoProperty = dtoProperties[this.properties.size()-1];
        dtoProperty.setId(true);
        return this;
    }

    public DtoPropertyBuilder id(String name) {
        return this.id(true, name);
    }

    public DtoPropertyBuilder id(boolean isId, String name) {
        this.properties.values().forEach(p -> p.setId(false));
        DtoProperty property = this.properties.get(name);
        if (Objects.nonNull(property)) {
            property.setId(isId);
        }
        return this;
    }

    public Map<String, DtoProperty> build() {
        return Collections.unmodifiableMap(properties);
    }

    public static DtoPropertyBuilder create(String... properties) {
        return new DtoPropertyBuilder(properties);
    }
}
