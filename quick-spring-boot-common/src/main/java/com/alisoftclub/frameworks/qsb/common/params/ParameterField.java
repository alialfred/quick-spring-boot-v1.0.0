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

import static com.alisoftclub.frameworks.qsb.common.ValidationUtil.notNull;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * @author Ali Imran
 */
public class ParameterField {

    private final ParameterGroup group;
    private final String field;
    private final ParameterOperator operator;
    private final Object value;
    private final Map<String, Object> valueMap = new LinkedHashMap(0);
    private boolean is_list = false;
    private boolean is_fixed = false;

    public ParameterField(ParameterGroup group, String field, ParameterOperator operator, Object value) {
        this.group = group;
        this.field = field;
        this.operator = operator;
        this.value = value;
    }

    public void fix(Function<String, String> paramNameFixer, Function<Object, Object> valueFixer) {
        paramNameFixer = Objects.isNull(paramNameFixer) ? s -> s : paramNameFixer;
        valueFixer = Objects.isNull(valueFixer) ? v -> v : valueFixer;
        if (null == this.operator) {

        } else if (!this.is_fixed) {
            this.is_list = value instanceof List;
            switch (this.operator) {
                case BETWEEN: {
                    List list = (List) (this.is_list ? value : Arrays.asList(value.toString().split(",")));
                    if (list.size() >= 2) {
                        this.valueMap.put(paramNameFixer.apply(field).concat("_from"), valueFixer.apply(list.get(0)));
                        this.valueMap.put(paramNameFixer.apply(field).concat("_to"), valueFixer.apply(list.get(1)));
                    }
                    break;
                }
                case NOT_IN:
                case IN: {
                    List list = (List) (this.is_list ? value : Arrays.asList(value.toString().split(",")));
//                    List list = Arrays.stream(value.toString()
//                            .replace("[", "")
//                            .replace("]", "")
//                            .split(",")).map(String::trim).map(valueFixer).collect(Collectors.toList());
                    this.valueMap.put(paramNameFixer.apply(field), list.stream().map(valueFixer).collect(Collectors.toList()));
                    is_list = true;
                    break;
                }
                case LIKE: {
                    this.valueMap.put(paramNameFixer.apply(field), String.format("%%%s%%", value));
                    break;
                }
                case START_WITH: {
                    this.valueMap.put(paramNameFixer.apply(field), String.format("%s%%", value));
                    break;
                }
                case END_WITH: {
                    this.valueMap.put(paramNameFixer.apply(field), String.format("%%%s", value));
                    break;
                }
                default:
                    this.valueMap.put(paramNameFixer.apply(field), notNull(value) ? valueFixer.apply(value) : null);
                    break;
            }
        }
        this.is_fixed = true;
    }

    public ParameterGroup getGroup() {
        return group;
    }

    public String getField() {
        return field;
    }

    public ParameterOperator getOperator() {
        return operator;
    }

    public Object getValue() {
        return value;
    }

    public String toClaus(String prefix, String paramKey) {
        return toClaus(prefix, paramKey, p -> p.toString());
    }

    public String toClaus(String prefix, String paramKey, Function<ParameterOperator, String> operatorSupplier) {
        return String.format("(%s%s %s (%s))",
                prefix,
                this.field,
                notNull(operatorSupplier) ? operatorSupplier.apply(this.operator) : this.operator,
                paramKey.concat(this.valueMap.keySet()
                        .stream()
                        .collect(Collectors.joining(") and (".concat(paramKey))))
        );
    }

    public Map<String, Object> getValueMap() {
        return this.valueMap;
    }

    public boolean isList() {
        return is_list;
    }

}
