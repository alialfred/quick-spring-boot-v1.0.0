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
public interface DtoPropertyConverter<F, R> {

    public F toJson(R r);

    public R toProperty(F f);
}
