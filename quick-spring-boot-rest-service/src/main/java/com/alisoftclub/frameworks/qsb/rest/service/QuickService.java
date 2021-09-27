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
package com.alisoftclub.frameworks.qsb.rest.service;

import com.alisoftclub.frameworks.qsb.common.RequestParameters;
import java.io.Serializable;
import java.util.List;
import org.springframework.core.io.Resource;

/**
 *
 * @author Ali Imran
 * @param <D>
 * @param <ID>
 */
public interface QuickService<D, ID extends Serializable> {

    public List<D> list(RequestParameters parameters);

    public List<D> getById(List<ID> id, RequestParameters parameters);

    public List<D> save(List<D> entity);

    public List<D> update(List<D> entity);

    public boolean delete(ID... id);

    public boolean delete(List<ID> id);

    public Resource report(String type, RequestParameters parameters);
}
