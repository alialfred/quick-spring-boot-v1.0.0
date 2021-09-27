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
package com.alisoftclub.frameworks.qsb.common;

import com.alisoftclub.frameworks.qsb.common.params.ParameterBuilder;
import com.alisoftclub.frameworks.qsb.common.params.ParameterField;
import com.alisoftclub.frameworks.qsb.common.params.ParameterGroup;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.lang.NonNull;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Ali Imran
 */
@SuppressWarnings({"unused", "WeakerAccess", "UnusedReturnValue"})
@JsonIgnoreProperties
public class RequestParameters {

    private int offset;
    private int pageSize;
    private boolean all;
    private String search;
    //    private Map<String, Object> params = new HashMap(0);
    private Map<String, ParameterField> paramFields = new HashMap<>(0);
    private List<String> orderBy = new ArrayList<>(0);
    private List<String> groupBy = new ArrayList<>(0);

    public RequestParameters() {
        this(new HashMap<>(0));
    }

    public RequestParameters(Map<String, Object> params) {
        RequestParameters.this.setParams(params);
    }

    public int getOffset() {
        return this.offset;
    }

    public RequestParameters setOffset(int offset) {
        this.offset = offset;
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public RequestParameters setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public boolean isAll() {
        return all;
    }

    public RequestParameters setAll(boolean all) {
        this.all = all;
        return this;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Map<String, ParameterField> getParams() {
        return paramFields;
    }

    public RequestParameters setParams(Map<String, Object> params) {
        this.paramFields = ParameterBuilder.createParameterFields(params);
        Object orderBy = params.get("orderBy");
        if (orderBy instanceof List) {
            this.setOrderBy((List<String>) orderBy);
        } else {
            this.setOrderBy(ValidationUtil.ifNull(orderBy, "").toString());
        }
        Object groupBy = params.get("groupBy");
        if (groupBy instanceof List) {
            this.setGroupBy((List<String>) groupBy);
        } else {
            this.setGroupBy(ValidationUtil.ifNull(groupBy, "").toString());
        }
        this.all = Boolean.parseBoolean(getOrDefault("all", "false"));
        this.offset = Integer.parseInt(getOrDefault("offset", "0"));
        this.pageSize = Integer.parseInt(getOrDefault("pageSize", "0"));
        this.search = getOrDefault("search", null);
        return this;
    }

    public List<String> getOrderBy() {
        return orderBy;
    }

    public RequestParameters setOrderBy(List<String> orderBy) {
        this.orderBy = orderBy;
        this.orderBy.removeIf(ValidationUtil::isEmpty);
        return this;
    }

    public RequestParameters setOrderBy(@NonNull String... orderBy) {
        for (String s : orderBy) {
            this.orderBy.add(s.replaceAll(":", " "));
        }
        this.orderBy.removeIf(ValidationUtil::isEmpty);
        return this;
    }

    public List<String> getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(List<String> groupBy) {
        this.groupBy = groupBy;
        this.groupBy.removeIf(ValidationUtil::isEmpty);
    }

    public void setGroupBy(String... groupBy) {
        for (String s : groupBy) {
            this.groupBy.add(s.replaceAll(":", " "));
        }
        this.groupBy.removeIf(ValidationUtil::isEmpty);
    }

    @SuppressWarnings("unchecked")
    public <T> T getOrDefault(String key, T defaultValue) {
        return (T) paramFields.getOrDefault(key, ParameterBuilder.createField(key, defaultValue)).getValue();
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) (Objects.nonNull(paramFields.get(key)) ? paramFields.get(key).getValue() : null);
    }

    public ParameterField getField(String key) {
        return this.paramFields.get(key);
    }

    public List<String> getKeys() {
        return new ArrayList<>(this.paramFields.keySet());
    }

    public Collection<ParameterField> getFields() {
        return this.paramFields.values();
    }

    public List<ParameterField> getFields(ParameterGroup group) {
        return this.paramFields.values().stream().filter(f -> f.getGroup().equals(group)).collect(Collectors.toList());
    }

    public Map<String, Object> getValueMap() {
        Map<String, Object> values = new LinkedHashMap<>(0);
        this.paramFields.values().stream().map(ParameterField::getValueMap).forEachOrdered(values::putAll);
        return values;
    }

    public RequestParameters add(String key, Object value) {
        return put(key, value);
    }

    public RequestParameters addIf(boolean condition, String key, Object value) {
        return condition ? put(key, value) : this;
    }

    public RequestParameters putIfAbsent(String key, Object value) {
        paramFields.putIfAbsent(key, ParameterBuilder.createField(key, value));
        return this;
    }

    public RequestParameters put(Map<String, Object> m) {
        paramFields.putAll(ParameterBuilder.createParameterFields(m));
        return this;
    }

    public RequestParameters put(String key, Object value) {
        paramFields.put(key, ParameterBuilder.createField(key, value));
        return this;
    }

    public RequestParameters removeUnknown(List<String> known) {
        String[] keys = paramFields.keySet().toArray(new String[0]);
        Arrays.stream(keys).filter(k -> !known.contains(k)).forEach(paramFields::remove);
        return this;
    }

    public RequestParameters remove(String key) {
        paramFields.remove(key);
        return this;
    }

    public RequestParameters replaceKey(String oldKey, String newKey) {
        return this.replaceKey(oldKey, newKey, null);
    }

    public RequestParameters replaceKey(String oldKey, String newKey, Function<String, Object> fixer) {
        fixer = Objects.isNull(fixer) ? s -> s : fixer;
        this.add(newKey, fixer.apply(String.valueOf(paramFields.remove(oldKey))));
        return this;
    }

    public boolean hasOrderBy() {
        return !this.orderBy.isEmpty();
    }

    public boolean hasGroupBy() {
        return !this.groupBy.isEmpty();
    }

    public boolean hasParameters() {
        return !this.paramFields.isEmpty();
    }

    public boolean hasParameters(String field) {
        return this.paramFields.get(field) != null;
    }

    public boolean hasSearch() {
        return ValidationUtil.notEmpty(search);
    }

}
