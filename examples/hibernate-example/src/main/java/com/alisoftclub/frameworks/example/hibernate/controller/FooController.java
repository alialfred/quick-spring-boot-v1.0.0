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
package com.alisoftclub.frameworks.example.hibernate.controller;

import com.alisoftclub.frameworks.example.hibernate.dto.FooDto;
import com.alisoftclub.frameworks.example.hibernate.service.FooService;
import com.alisoftclub.frameworks.qsb.rest.controller.ApiController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Ali Imran
 */
@RestController
@RequestMapping("/api/foo")
public class FooController extends ApiController<FooDto,Long>
{
    
    @Autowired
    public FooController(FooService quickService) {
        super(quickService);
        System.out.println("#FooController.quickService: " + quickService);
    }

    @ResponseBody
    @GetMapping(value = "/hello")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Foo World!");
    }    
}
