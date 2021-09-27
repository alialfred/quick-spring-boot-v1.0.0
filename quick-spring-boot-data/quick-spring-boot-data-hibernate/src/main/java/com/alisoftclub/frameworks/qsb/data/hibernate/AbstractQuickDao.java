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
package com.alisoftclub.frameworks.qsb.data.hibernate;

import com.alisoftclub.frameworks.qsb.common.RequestParameters;
import com.alisoftclub.frameworks.qsb.common.exception.ApiException;
import com.alisoftclub.frameworks.qsb.common.params.ParameterField;
import com.alisoftclub.frameworks.qsb.common.params.ParameterGroup;
import com.alisoftclub.frameworks.qsb.dto.Dto;
import com.alisoftclub.frameworks.qsb.rest.dao.QuickDao;
import org.apache.commons.io.IOUtils;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.ParameterMetadata;
import org.hibernate.query.Query;
import org.hibernate.query.QueryParameter;
import org.hibernate.transform.BasicTransformerAdapter;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@SuppressWarnings({"WeakerAccess", "UnusedReturnValue"})
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public abstract class AbstractQuickDao<E, ID extends Serializable> implements QuickDao<E, ID> {

    @Autowired
    protected EntityManager entityManager;
    protected final Class<E> entityClass;
    protected final Map<String, Function<Object, Object>> fixes = new HashMap<>(0);

    @SuppressWarnings("unchecked")
    public AbstractQuickDao() {
        this.entityClass = (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        Function<Object, Object> idFixer = o -> Long.valueOf(o.toString().trim());
        AbstractQuickDao.this.addFixer("id", idFixer);
    }

    protected Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    public AbstractQuickDao addFixer(String key, Function<Object, Object> fixer) {
        this.fixes.put(key, fixer);
        return this;
    }

    protected String formatter(String name) {
        return "".concat(name.replaceAll("\\.", "_"));
    }

    protected String toClaus(ParameterField field) {
        field.fix(this::formatter, this.fixes.get(field.getField()));
        return field.toClaus("ent.", ":");
    }

    protected String createWhere(RequestParameters parameters) {
        String claus = "";
        if (parameters.hasParameters()) {
            claus = " where ";
            List<ParameterField> and = parameters.getFields(ParameterGroup.AND);
            if (!and.isEmpty()) {
                claus += and.stream().map(this::toClaus).collect(Collectors.joining(" and ", "(", ")"));
            }
            List<ParameterField> or = parameters.getFields(ParameterGroup.OR);
            if (!and.isEmpty() && !or.isEmpty()) {
                claus += " and ";
            }
            if (!or.isEmpty()) {
                claus += or.stream().map(this::toClaus).collect(Collectors.joining(" or ", "(", ")"));
            }
        }
        return claus;
    }

    protected void setParameters(Query query, RequestParameters parameters) {
        ParameterMetadata pm = query.getParameterMetadata();
        Set<QueryParameter<?>> params = pm.collectAllParameters();
        params.forEach((QueryParameter<?> qp) -> query.setParameter(qp, null));
        Set<String> names = pm.getNamedParameterNames();
        for (ParameterField field : parameters.getFields()) {
            field.fix(this::formatter, this.fixes.get(field.getField()));
            for (Map.Entry<String, Object> param : field.getValueMap().entrySet()) {
                if (names.contains(param.getKey())) {
                    if (param.getValue() instanceof List) {
                        query.setParameterList(param.getKey(), (List) param.getValue());
                    } else {
                        query.setParameter(param.getKey(), param.getValue());
                    }
                }
            }
        }
    }

    private String createQuery(RequestParameters parameters) {
        return this.createQuery("", parameters);
//        String claus = createWhere(parameters);
//        return String.format("from %s ent %s ", entityClass.getSimpleName(), claus);
    }

    private String createQuery(String select, RequestParameters parameters) {
        String claus = createWhere(parameters);
        String q = String.format("%s from %s ent %s ", select, entityClass.getSimpleName(), claus);
        if (parameters.hasGroupBy()) {
            q += "group by " + String.join(", ", parameters.getGroupBy());
        }
        return q;
    }

    @Override
    public List<E> list(RequestParameters parameters) {
        String q = createQuery(parameters);
        if (parameters.hasOrderBy()) {
            q += "order by " + String.join(", ", parameters.getOrderBy());
        }
//        System.out.println(q);
        Query<E> query = getSession().createQuery(q, entityClass);

        setParameters(query, parameters);
        if (parameters.getPageSize() != 0) {
            query
                    .setFirstResult(parameters.getOffset())
                    .setMaxResults(parameters.getPageSize());
        }
        return query.list();
    }

    @Override
    public long max(RequestParameters parameters) {
        String q = createQuery("select max(ent.id) ", parameters);
        Query query = getSession().createQuery(q);

        setParameters(query, parameters);
        return (long) query.getSingleResult();
    }

    @Override
    public long min(RequestParameters parameters) {
        String q = createQuery("select min(ent.id) ", parameters);
        Query query = getSession().createQuery(q);

        setParameters(query, parameters);
        return (long) query.getSingleResult();
    }

    @Override
    public long count(RequestParameters parameters) {
        String q = createQuery("select count(ent.id) ", parameters);
        Query query = getSession().createQuery(q);

        setParameters(query, parameters);
        return (long) query.getSingleResult();
    }

    @Override
    public boolean exist(RequestParameters parameters) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LocalDateTime getTimeStamp() {
        Query<LocalDateTime> query = getSession().createNativeQuery("SELECT CURRENT_TIMESTAMP", LocalDateTime.class);
        return query.uniqueResult();
    }

    @Override
    public List<E> getById(List<ID> id, RequestParameters parameters) {
        return list(parameters.add("id:in", id));
    }

    @Transactional
    @Override
    public List<E> save(List<E> entity) {
        Session session = getSession();
        for (E e : entity) {
            session.save(e);
        }
        return entity;
    }

    @Transactional
    @Override
    public List<E> update(List<E> entity) {
        Session session = getSession();
        for (E e : entity) {
            session.update(e);
        }
        return entity;
    }

    @SuppressWarnings("unchecked")
    @Transactional
    @Override
    public boolean delete(ID... id) {
        return this.delete(Arrays.asList(id));
    }

    @Transactional
    @Override
    public boolean delete(List<ID> ids) {
        Session session = getSession();
        for (E e : getById(ids, new RequestParameters())) {
            session.delete(e);
        }
        return true;
    }

    @Override
    public long reportCount(String reportName, RequestParameters parameters) {
        try {
            Session session = getSession();
            NativeQuery query = session.createNativeQuery(String.format("select count(*) from (%s) rpt", readQuery(reportName)));
            this.setParameters(query, parameters);
            return (long) Long.valueOf(query.getSingleResult().toString());
        } catch (Exception exc) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, exc.getMessage(), exc);
        }
    }

    @SuppressWarnings({"unchecked", "DuplicatedCode", "deprecation"})
    @Override
    public <T extends Dto> List<T> report(String reportName, RequestParameters parameters, Supplier<T> supplier) throws ApiException {
        try {
            Session session = getSession();
            NativeQuery query = session.createNativeQuery(readQuery(reportName));
            query.setResultTransformer(new BasicTransformerAdapter() {
                @Override
                public Object transformTuple(Object[] tuple, String[] aliases) {
                    Dto result = supplier.get();
                    for (int i = 0; i < tuple.length; i++) {
                        String alias = aliases[i];
                        if (alias != null) {
                            result.setValue(alias, tuple[i]);
                        }
                    }
                    return result;
                }
            });
            this.setParameters(query, parameters);
            if (parameters.getPageSize() != 0) {
                query
                        .setFirstResult(parameters.getOffset())
                        .setMaxResults(parameters.getPageSize());
            }
            return query.getResultList();
        } catch (Exception exc) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, exc.getMessage(), exc);
        }
    }

    @SuppressWarnings({"DuplicatedCode", "deprecation", "unchecked"})
    @Override
    public <T extends Dto> List<T> report(String reportName, Function parameterFunction, Supplier<T> supplier) throws ApiException {
        try {
            Session session = getSession();
            NativeQuery query = session.createNativeQuery(readQuery(reportName));
            query.setResultTransformer(new BasicTransformerAdapter() {
                @Override
                public Object transformTuple(Object[] tuple, String[] aliases) {
                    Dto result = supplier.get();
                    for (int i = 0; i < tuple.length; i++) {
                        String alias = aliases[i];
                        if (alias != null) {
                            result.setValue(alias, tuple[i]);
                        }
                    }
                    return result;
                }
            });
            if (parameterFunction != null) {
                parameterFunction.apply(query);
            }
//            this.setParameters((Query) query, parameters);
            return query.getResultList();
        } catch (Exception exc) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, exc.getMessage(), exc);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List reportRaw(String reportName, Function parameterFunction) throws ApiException {
        try {
            Session session = getSession();
            NativeQuery query = session.createNativeQuery(readQuery(reportName));
            if (parameterFunction != null) {
                parameterFunction.apply(query);
            }
//            this.setParameters((Query) query, parameters);
//            query.list()
            return query.list();
        } catch (Exception exc) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, exc.getMessage(), exc);
        }
    }

    @SuppressWarnings({"unchecked", "deprecation"})
    @Override
    public <T> List<T> report(String reportName, RequestParameters parameters, Class<T> clazz) throws ApiException {
        try {
            Session session = getSession();
            NativeQuery query = session.createNativeQuery(readQuery(reportName));
            if (Map.class.isAssignableFrom(clazz)) {
                query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            } else {
                query.setResultTransformer(Transformers.aliasToBean(clazz));
            }
            if (parameters.hasParameters()) {
                this.setParameters(query, parameters);
            }
            if (parameters.getPageSize() != 0) {
                query
                        .setFirstResult(parameters.getOffset())
                        .setMaxResults(parameters.getPageSize());
            }
            return query.getResultList();
        } catch (Exception exc) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, exc.getMessage(), exc);
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    protected String readQuery(String reportName) throws Exception {
        if (reportName.startsWith("file://")) {
            FileInputStream fis = new FileInputStream(reportName.substring("file://".length()));
            return IOUtils.toString(fis, "UTF-8");
        }

        byte[] b;
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(reportName)) {
            assert is != null;
            b = new byte[is.available()];
            is.read(b);
        }
        return new String(b);
    }
}
