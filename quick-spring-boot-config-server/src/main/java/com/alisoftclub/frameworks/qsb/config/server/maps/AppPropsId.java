///*
// * Copyright 2018 Ali Imran.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package com.alisoftclub.frameworks.qsb.config.server.maps;
//
//import java.io.Serializable;
//import java.util.Objects;
//import javax.persistence.Column;
//import javax.persistence.Embeddable;
//import javax.persistence.Id;
//
///**
// *
// * @author Ali Imran
// */
//@Embeddable
//public class AppPropsId implements Serializable {
//
//    @Id
//    @Column(name = "app_name")
//    private String application;
//    @Id
//    @Column(name = "app_profile")
//    private String pofile;
//    @Id
//    @Column(name = "app_label")
//    private String label;
//
//    public AppPropsId() {
//    }
//
//    public AppPropsId(String application, String pofile, String label) {
//        this.application = application;
//        this.pofile = pofile;
//        this.label = label;
//    }
//
//    public String getApplication() {
//        return application;
//    }
//
//    public void setApplication(String application) {
//        this.application = application;
//    }
//
//    public String getPofile() {
//        return pofile;
//    }
//
//    public void setPofile(String pofile) {
//        this.pofile = pofile;
//    }
//
//    public String getLabel() {
//        return label;
//    }
//
//    public void setLabel(String label) {
//        this.label = label;
//    }
//
//    @Override
//    public int hashCode() {
//        int hash = 7;
//        hash = 37 * hash + Objects.hashCode(this.application);
//        hash = 37 * hash + Objects.hashCode(this.pofile);
//        hash = 37 * hash + Objects.hashCode(this.label);
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) {
//            return true;
//        }
//        if (obj == null) {
//            return false;
//        }
//        if (getClass() != obj.getClass()) {
//            return false;
//        }
//        final AppPropsId other = (AppPropsId) obj;
//        if (!Objects.equals(this.application, other.application)) {
//            return false;
//        }
//        if (!Objects.equals(this.pofile, other.pofile)) {
//            return false;
//        }
//        return Objects.equals(this.label, other.label);
//    }
//
//}
