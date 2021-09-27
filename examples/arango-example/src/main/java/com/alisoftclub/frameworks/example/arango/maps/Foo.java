/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alisoftclub.frameworks.example.arango.maps;

import com.alisoftclub.frameworks.qsb.data.arango.maps.BaseEntity;
import com.arangodb.entity.DocumentField;
import com.arangodb.springframework.annotation.Document;

/**
 *
 * @author AI
 */
@Document("foo")
public class Foo extends BaseEntity {

    private Long id;
    private String title;

    public Foo() {
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

    @Override
    public String toString() {
        return String.format("%d: %s", id, title);
    }
}
