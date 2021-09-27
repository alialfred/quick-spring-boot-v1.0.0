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
public class Bar {
    private Long id;
    private String title;
    private float rate;
    private float qty;
    private float amount;

    public Bar() {
    }

    public Bar(Long id) {
        this.id = id;
    }

    public Bar(Long id, String title) {
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

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public float getQty() {
        return qty;
    }

    public void setQty(float qty) {
        this.qty = qty;
    }

    public float getAmount() {
        return amount = this.rate * this.qty;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
    
    
}
