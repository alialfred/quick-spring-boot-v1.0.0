/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alisoftclub.frameworks.example.arango.controller;

import com.alisoftclub.frameworks.example.arango.dto.FooDto;
import com.alisoftclub.frameworks.example.arango.service.FooService;
import com.alisoftclub.frameworks.qsb.rest.controller.ApiController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author AI
 */
@RestController
@RequestMapping("/api/foo")
public class FooController extends ApiController<FooDto, Long> {

    public FooController(FooService quickService) {
        super(quickService, "_id", "id", "title");
    }

    @ResponseBody
    @GetMapping(value = "/hello")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Foo World!");
    }

}
