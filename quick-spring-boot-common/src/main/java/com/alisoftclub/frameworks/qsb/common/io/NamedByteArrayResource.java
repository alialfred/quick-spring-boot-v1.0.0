/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alisoftclub.frameworks.qsb.common.io;

import org.springframework.core.io.ByteArrayResource;

/**
 *
 * @author AI
 */
public class NamedByteArrayResource extends ByteArrayResource {

    private String filename;
    private String description;
    private String type;

    public NamedByteArrayResource(String filename, byte[] byteArray) {
        super(byteArray);
        this.filename = filename;
    }

    public NamedByteArrayResource(String filename, byte[] byteArray, String description, String type) {
        super(byteArray, description);
        this.filename = filename;
        this.description = description;
        this.type = type;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getFilename() {
        return this.filename + "." + this.type;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
