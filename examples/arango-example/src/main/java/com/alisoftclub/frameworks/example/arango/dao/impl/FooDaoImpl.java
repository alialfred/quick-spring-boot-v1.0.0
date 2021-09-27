/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alisoftclub.frameworks.example.arango.dao.impl;

import com.alisoftclub.frameworks.example.arango.dao.FooDao;
import com.alisoftclub.frameworks.example.arango.maps.Foo;
import com.alisoftclub.frameworks.qsb.common.RequestParameters;
import com.alisoftclub.frameworks.qsb.data.arango.AbstractQuickDao;
import java.time.LocalDateTime;
import org.springframework.stereotype.Repository;

@Repository
public class FooDaoImpl extends AbstractQuickDao<Foo, Long> implements FooDao {

    @Override
    public long max(RequestParameters parameters) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long min(RequestParameters parameters) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long count(RequestParameters parameters) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean exist(RequestParameters parameters) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LocalDateTime getTimeStamp() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long reportCount(String reportName, RequestParameters parameters) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
