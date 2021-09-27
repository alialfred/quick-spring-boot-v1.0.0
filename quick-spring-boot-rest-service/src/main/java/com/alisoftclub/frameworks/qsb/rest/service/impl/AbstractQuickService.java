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
package com.alisoftclub.frameworks.qsb.rest.service.impl;

import com.alisoftclub.frameworks.qsb.common.RequestParameters;
import com.alisoftclub.frameworks.qsb.common.exception.ApiException;
import com.alisoftclub.frameworks.qsb.dto.Dto;
import com.alisoftclub.frameworks.qsb.dto.DtoSupplier;
import com.alisoftclub.frameworks.qsb.rest.dao.QuickDao;
import com.alisoftclub.frameworks.qsb.rest.service.QuickService;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;

/**
 *
 * @author Ali Imran
 * @param <D>
 * @param <E>
 * @param <ID>
 */
@SuppressWarnings("WeakerAccess")
public abstract class AbstractQuickService<D extends Dto<E>, E, ID extends Serializable> implements QuickService<D, ID> {

    protected final QuickDao<E, ID> quickDao;
    protected final DtoSupplier<D, E> supplier;

    public AbstractQuickService(QuickDao<E, ID> quickDao, DtoSupplier<D, E> supplier) {
        this.quickDao = quickDao;
        this.supplier = supplier;
    }

    @Override
    public List<D> list(RequestParameters parameters) {
        return toDto(quickDao.list(parameters));
    }

    @Override
    public List<D> getById(List<ID> id, RequestParameters parameters) {
        return toDto(quickDao.getById(id, parameters));
    }

    @Override
    public List<D> save(List<D> entity) {
        return toDto(quickDao.save(toEntity(entity)));
    }

    @Override
    public List<D> update(List<D> entity) {
        return toDto(quickDao.update(toEntity(entity)));
    }

    @SafeVarargs
    @Override
    public final boolean delete(ID... id) {
        return quickDao.delete(id);
    }

    @Override
    public boolean delete(List<ID> id) {
        return quickDao.delete(id);
    }

    @Override
    public Resource report(String type, RequestParameters parameters) {
        throw new ApiException(HttpStatus.NOT_FOUND, "printing is not supported.");
    }

    protected List<D> toDto(List<E> entity) {
        return entity.stream().map(supplier::get).collect(Collectors.toList());
    }

    protected List<E> toEntity(List<D> data) {
        return data.stream().map(Dto::getEntity).collect(Collectors.toList());
    }
}
