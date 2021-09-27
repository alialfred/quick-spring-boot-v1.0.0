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
package com.alisoftclub.frameworks.qsb.rest.controller;

import com.alisoftclub.frameworks.qsb.common.RequestParameters;
import com.alisoftclub.frameworks.qsb.common.exception.ApiException;
import com.alisoftclub.frameworks.qsb.dto.Dto;
import com.alisoftclub.frameworks.qsb.rest.service.QuickService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Ali Imran
 * @param <D>
 * @param <ID>
 */
@RestController
public abstract class ApiController<D extends Dto, ID extends Serializable> {

    protected final QuickService<D, ID> quickService;
    protected final List<String> allowedParametersList;

    public ApiController(QuickService<D, ID> quickService) {
        this.quickService = quickService;
        this.allowedParametersList = new ArrayList(0);
    }

    public ApiController(QuickService<D, ID> quickService, String... allowedParameters) {
        this(quickService);
        this.allowedParametersList.addAll(Arrays.asList(allowedParameters));
    }

    @ResponseBody
    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public ResponseEntity<List<D>> list(@RequestParam Map<String, Object> params) {
        return ResponseEntity.ok(quickService.list(toRequestParameters(params)));
    }

    @ResponseBody
    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST)
    public ResponseEntity<List<D>> save(@RequestBody List<D> entity) {
        return ResponseEntity.ok(quickService.save(entity));
    }

    @ResponseBody
    @RequestMapping(value = {"", "/"}, method = RequestMethod.PUT)
    public ResponseEntity<List<D>> update(@RequestBody List<D> entity) {
        return ResponseEntity.ok(quickService.update(entity));
    }

    @ResponseBody
    @RequestMapping(value = {"/{id}"}, method = RequestMethod.GET)
    public ResponseEntity<List<D>> getById(@PathVariable List<ID> id, @RequestParam Map<String, Object> params) {
        return ResponseEntity.ok(quickService.getById(id, toRequestParameters(params)));
    }

    @ResponseBody
    @RequestMapping(value = {"/{id}"}, method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> delete(@PathVariable List<ID> id) {
        return ResponseEntity.ok(quickService.delete(id));
    }

    @ResponseBody
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Object> apiExceptionHandler(HttpServletRequest req, ApiException exception) {
        return new ResponseEntity(getExceptionObject(req, exception, exception.getStatus()), exception.getStatus());
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exceptionHandler(HttpServletRequest req, Exception exception) {
        return new ResponseEntity(getExceptionObject(req, exception, HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Map<String, Object> getExceptionObject(HttpServletRequest req, Exception exception, HttpStatus status) {
        Map<String, Object> response = new HashMap();
        response.put("timestamp", System.currentTimeMillis());
        response.put("status", status.value());
        response.put("error", status.toString());
        response.put("message", exception.getMessage());
        response.put("path", req.getRequestURI());
        response.put("method", req.getMethod());
        exception.printStackTrace(System.out);
        return response;
    }

    protected RequestParameters toRequestParameters(Map<String, Object> params) {
        return new RequestParameters(params).removeUnknown(allowedParametersList);
    }

}
