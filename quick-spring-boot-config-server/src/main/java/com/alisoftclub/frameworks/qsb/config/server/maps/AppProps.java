/*
 * Copyright 2018 Ali Imran.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alisoftclub.frameworks.qsb.config.server.maps;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Ali Imran
 */
public class AppProps implements Serializable {

    private String application;
    private String pofile;
    private String label;
    private String key;
    private String value;

    public AppProps() {
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getPofile() {
        return pofile;
    }

    public void setPofile(String pofile) {
        this.pofile = pofile;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.application);
        hash = 37 * hash + Objects.hashCode(this.pofile);
        hash = 37 * hash + Objects.hashCode(this.label);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AppProps other = (AppProps) obj;
        if (!Objects.equals(this.application, other.application)) {
            return false;
        }
        if (!Objects.equals(this.pofile, other.pofile)) {
            return false;
        }
        return Objects.equals(this.label, other.label);
    }

}
