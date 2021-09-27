/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alisoftclub.frameworks.qsb.data.arango.maps;

import com.arangodb.entity.DocumentField;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author AI
 */
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("_id")
    @DocumentField(DocumentField.Type.ID)
    protected String documentHandle;
    @JsonProperty("_key")
    @DocumentField(DocumentField.Type.KEY)
    protected String documentKey;
    @JsonProperty("_rev")
    @DocumentField(DocumentField.Type.REV)
    protected String documentRevision;

    public BaseEntity() {
    }

    public String getDocumentHandle() {
        return documentHandle;
    }

    public void setDocumentHandle(String _documentHandle) {
        this.documentHandle = _documentHandle;
    }

    public String getDocumentKey() {
        return documentKey;
    }

    public void setDocumentKey(String _documentKey) {
        this.documentKey = _documentKey;
    }

    public String getDocumentRevision() {
        return documentRevision;
    }

    public void setDocumentRevision(String _documentRevision) {
        this.documentRevision = _documentRevision;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + Objects.hashCode(this.documentHandle);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BaseEntity other = (BaseEntity) obj;
        return Objects.equals(this.documentHandle, other.documentHandle);
    }
}
