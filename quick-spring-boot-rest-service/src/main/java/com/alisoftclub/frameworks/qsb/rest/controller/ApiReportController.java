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
import com.alisoftclub.frameworks.qsb.dto.Dto;
import com.alisoftclub.frameworks.qsb.rest.service.QuickService;
import java.util.List;
import java.util.Map;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Ali Imran
 * @param <D>
 */
@RestController
public abstract class ApiReportController<D extends Dto> {

    protected final QuickService<D, Long> quickService;

    public ApiReportController(QuickService<D, Long> quickService) {
        this.quickService = quickService;
    }

    @ResponseBody
    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public ResponseEntity<List<D>> list(@RequestParam Map<String, Object> params) {
        return ResponseEntity.ok(quickService.list(toRequestParameters(params)));
    }

    @ResponseBody
    @RequestMapping(value = {"/print"}, method = RequestMethod.GET)
    public ResponseEntity<Resource> print(@RequestParam(defaultValue = "pdf") String type, @RequestParam Map<String, Object> params) {
        Resource resource = quickService.report(type, toRequestParameters(params));
        return ResponseEntity.ok()
                .header("Content-Type", resource.getDescription())
                .header("Content-Disposition", String.format("inline; filename=\"%s\"", resource.getFilename()))
                .body(resource);
    }

    protected RequestParameters toRequestParameters(Map<String, Object> params) {
        return new RequestParameters(params);
    }

}
