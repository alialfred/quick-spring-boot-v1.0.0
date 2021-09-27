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
package com.alisoftclub.frameworks.qsb.common.params;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Ali Imran
 */
public class ParameterBuilder {

    public static Map<String, ParameterField> createParameterFields(Map<String, Object> map) {
        return map.entrySet().stream().map(ParameterBuilder::createField).collect(Collectors.toMap(ParameterField::getField, pm -> pm));
    }

    public static ParameterField createField(Map.Entry<String, Object> entry) {
        return createField(entry.getKey(), entry.getValue());
    }

    public static ParameterField createField(String key, Object value) {
        Iterator<String> it = Arrays.asList(key.split(":")).iterator();
        String field = it.next();
        ParameterGroup group = ParameterGroup.AND;
        ParameterOperator operator = ParameterOperator.EQUAL;
        if (it.hasNext()) {
            operator = ParameterOperator.getOperator(it.next());
        }
        if (it.hasNext()) {
            group = ParameterGroup.getGroup(it.next());
        }

        return new ParameterField(group, field, operator, value);
    }
}
