/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alisoftclub.frameworks.example.arango.service.impl;

import com.alisoftclub.frameworks.example.arango.dao.FooDao;
import com.alisoftclub.frameworks.example.arango.dto.FooDto;
import com.alisoftclub.frameworks.example.arango.maps.Foo;
import com.alisoftclub.frameworks.example.arango.service.FooService;
import com.alisoftclub.frameworks.qsb.rest.service.impl.AbstractQuickService;
import org.springframework.stereotype.Service;

@Service
public class FooServiceImpl extends AbstractQuickService<FooDto, Foo, Long> implements FooService {

    public FooServiceImpl(FooDao quickDao) {
        super(quickDao, FooDto::new);
    }
}
