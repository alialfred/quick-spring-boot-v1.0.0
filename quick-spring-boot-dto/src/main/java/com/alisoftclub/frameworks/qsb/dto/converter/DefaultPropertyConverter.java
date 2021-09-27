/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alisoftclub.frameworks.qsb.dto.converter;

/**
 *
 * @author usman
 */
public class DefaultPropertyConverter<F,R> implements DtoPropertyConverter<F,R>{

    @Override
    public F toJson(R r) {
        return (F) r;
    }

    @Override
    public R toProperty(F f) {
        return (R) f;
    }
    
}
