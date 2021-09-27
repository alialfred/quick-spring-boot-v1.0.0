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
package com.alisoftclub.frameworks.qsb.data.arango;

import com.alisoftclub.frameworks.qsb.common.RequestParameters;
import static com.alisoftclub.frameworks.qsb.common.ValidationUtil.notNull;
import com.alisoftclub.frameworks.qsb.common.exception.ApiException;
import com.alisoftclub.frameworks.qsb.common.params.ParameterField;
import com.alisoftclub.frameworks.qsb.common.params.ParameterGroup;
import com.alisoftclub.frameworks.qsb.common.params.ParameterOperator;
import com.alisoftclub.frameworks.qsb.dto.Dto;
import com.alisoftclub.frameworks.qsb.rest.dao.QuickDao;
import com.arangodb.ArangoCollection;
import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDatabase;
import com.arangodb.model.TransactionOptions;
import com.arangodb.springframework.annotation.Document;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public abstract class AbstractQuickDao<E, ID extends Serializable> implements QuickDao<E, ID> {

    @Autowired
    protected ArangoDatabase database;
    protected final Class<E> entityClass;
    protected Document document;
    protected final String collectionName;
    protected ArangoCollection collection;
    protected final Map<String, Function<Object, Object>> fixes = new HashMap<>(0);
    @Autowired
    protected ObjectMapper mapper;

    public AbstractQuickDao() {
        this.entityClass = (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        document = this.entityClass.getAnnotation(Document.class);
        this.collectionName = notNull(document) ? document.value() : this.entityClass.getSimpleName();
    }

    @PostConstruct
    private void init() {
        collection = this.database.collection(collectionName);
        if (!collection.exists()) {
            this.collection.create();
        }
        Function<Object,Object> idFixer = o -> Long.valueOf(o.toString().trim());
        AbstractQuickDao.this.addFixer("id", idFixer);
    }

    protected ArangoCollection getSession() {
        return this.collection;
    }

    public AbstractQuickDao addFixer(String key, Function<Object, Object> fixer) {
        this.fixes.put(key, fixer);
        return this;
    }
    
    protected String getOperator(ParameterOperator operator){
        switch(operator){
            case EQUAL:
                return " == ";
        }
        return operator.toString();
    }

    protected String formatter(String name) {
        return "".concat(name.replaceAll("\\.", "_"));
    }

    protected String toClaus(ParameterField field) {
        field.fix(this::formatter, this.fixes.get(field.getField()));
        return field.toClaus("ent.", "@", this::getOperator);
    }

    protected String createWhere(RequestParameters parameters) {
        String claus = "";
        if (parameters.hasParameters()) {
            claus = " FILTER ";
            List<ParameterField> and = parameters.getFields(ParameterGroup.AND);
            if (!and.isEmpty()) {
                claus += and.stream().map(this::toClaus).collect(Collectors.joining(" AND ", "(", ")"));
            }
            List<ParameterField> or = parameters.getFields(ParameterGroup.OR);
            if (!and.isEmpty() && !or.isEmpty()) {
                claus += " AND ";
            }
            if (!or.isEmpty()) {
                claus += or.stream().map(this::toClaus).collect(Collectors.joining(" OR ", "(", ")"));
            }
        }
        return claus;
    }

    protected void setParameters(Map<String, Object> params, RequestParameters parameters) {
        parameters.getFields().stream().map((field) -> {
            field.fix(this::formatter, this.fixes.get(field.getField()));
            return field;
        }).forEach((field) -> {
            params.putAll(field.getValueMap());
        });
    }

    private String createQuery(RequestParameters parameters) {
        String claus = createWhere(parameters);
        return String.format("FOR ent IN %s %s RETURN ent", this.collectionName, claus);
    }

    @Override
    public List<E> list(RequestParameters parameters) {
        Map<String, Object> params = new HashMap<>();
        String q = createQuery(parameters);
        setParameters(params, parameters);
        System.out.println(q);
//        AqlQueryOptions options = new AqlQueryOptions();
        ArangoCursor<E> query = this.database.query(q, params, entityClass);
        return query.asListRemaining();
    }

    @Override
    public List<E> getById(List<ID> id, RequestParameters parameters) {
        return list(parameters.add("id:in", id));
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public List<E> save(List<E> entity) {

        TransactionOptions options = new TransactionOptions();
        options.params(entity)
                .readCollections(this.collectionName, "key_indexes")
                .writeCollections(this.collectionName, "key_indexes", "logs");
        String response = this.database.transaction(
                String.format(this.readQuery("transaction_function.txt"), this.collectionName), String.class, options);
        try {
            return this.mapper.readValue(response, this.mapper.getTypeFactory().constructCollectionType(List.class, entityClass));
        } catch (IOException ex) {
            return Collections.EMPTY_LIST;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public List<E> update(List<E> entity) {
        return this.save(entity);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public boolean delete(ID... id) {
        return this.delete(Arrays.asList(id));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean delete(List<ID> ids) {
//        this.collection.de
//        Session session = getSession();
//        for (ID id : ids) {
//            session.delete(getById(ids, new RequestParameters()));
//        }
        return true;
    }

    @Override
    public <T extends Dto> List<T> report(String reportName, RequestParameters parameters, Supplier<T> supplier) throws ApiException {
//        try {
//            Session session = getSession();
//            NativeQuery<T> query = session.createNativeQuery(readQuery(reportName));
//            query.setResultTransformer(new BasicTransformerAdapter() {
//                @Override
//                public Object transformTuple(Object[] tuple, String[] aliases) {
//                    Dto result = supplier.get();
//                    for (int i = 0; i < tuple.length; i++) {
//                        String alias = aliases[i];
//                        if (alias != null) {
//                            result.setValue(alias, tuple[i]);
//                        }
//                    }
//                    return result;
//                }
//            });
//            if (parameters.hasParameters()) {
//                this.setParameters((Query) query, parameters);
//            }
//            return query.getResultList();
//        } catch (Exception exc) {
//            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, exc.getMessage(), exc);
//        }
        throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Not implemented yet");
    }

    @Override
    public <T> List<T> report(String reportName, RequestParameters parameters, Class<T> clazz) throws ApiException {
//        try {
//            Session session = getSession();
//            NativeQuery<T> query = session.createNativeQuery(readQuery(reportName));
//            if (Map.class.isAssignableFrom(clazz)) {
//                query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
//            } else {
//                query.setResultTransformer(Transformers.aliasToBean(clazz));
//            }
//            if (parameters.hasParameters()) {
//                this.setParameters((Query) query, parameters);
//            }
//            return query.getResultList();
//        } catch (Exception exc) {
//            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, exc.getMessage(), exc);
//        }
        throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Not implemented yet");
    }

    @Override
    public <T extends Dto> List<T> report(String reportName, Function parameterFunction, Supplier<T> supplier) throws ApiException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List reportRaw(String reportName, Function parameterFunction) throws ApiException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    protected String readQuery(String reportName){
        byte[] b;
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(reportName)) {
            b = new byte[is.available()];
            is.read(b);
        }catch(Exception exc){
            b = new byte[0];
        }
        return new String(b);
    }

    public class ListTypeReference extends TypeReference<List<E>> {

        @Override
        public Type getType() {
            return entityClass;
        }
    }
}
