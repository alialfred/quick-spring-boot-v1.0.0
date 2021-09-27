/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alisoftclub.frameworks.qsb.dto.pojo;

/**
 *
 * @author Ali Imran
 */
public class Foo {
    private Long id;
    private String title;
    private Bar bar;
    
    public Foo() {
    }

    public Foo(Long id) {
        this.id = id;
    }

    public Foo(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bar getBar() {
        return bar;
    }

    public void setBar(Bar bar) {
        System.out.println("Set Bar Called: " + bar);
        this.bar = bar;
    }
    
    
}
