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
package com.alisoftclub.frameworks.qsb.rest.dao;

import com.alisoftclub.frameworks.qsb.common.RequestParameters;
import com.alisoftclub.frameworks.qsb.common.exception.ApiException;
import com.alisoftclub.frameworks.qsb.dto.Dto;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 *
 * @author Ali Imran
 * @param <E>
 * @param <ID>
 */
public interface QuickDao<E, ID extends Serializable> {

    public List<E> list(RequestParameters parameters);

    public List<E> getById(List<ID> id, RequestParameters parameters);

    public List<E> save(List<E> entity);

    public List<E> update(List<E> entity);

    public boolean delete(ID... id);

    public boolean delete(List<ID> id);

    public long max(RequestParameters parameters);

    public long min(RequestParameters parameters);

    public long count(RequestParameters parameters);

    public boolean exist(RequestParameters parameters);

    public LocalDateTime getTimeStamp();

    public long reportCount(String reportName, RequestParameters parameters);

    public <T> List<T> report(String reportName, RequestParameters parameters, Class<T> clazz) throws ApiException;

    public <T extends Dto> List<T> report(String reportName, RequestParameters parameters, Supplier<T> supplier) throws ApiException;

    public <T extends Dto> List<T> report(String reportName, Function parameterFunction, Supplier<T> supplier) throws ApiException;

    public List reportRaw(String reportName, Function parameterFunction) throws ApiException;
}
