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
//package com.alisoftclub.frameworks.qsb.config.server.config;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.config.environment.Environment;
//import org.springframework.cloud.config.environment.PropertySource;
//import org.springframework.cloud.config.server.environment.EnvironmentRepository;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Component;
//
///**
// *
// * @author Ali Imran
// */
//@Component
//public class EnvConfigRepository implements EnvironmentRepository {
//
//    private static final String DEFAULT_LABEL = "master";
//
//    @Autowired
//    private JdbcTemplate template;
//
//    @Override
//    public Environment findOne(String application, String profile, String label) {
//        label = StringUtils.isEmpty(label) ? DEFAULT_LABEL : label;
//        System.out.printf(">>>> findOne: %s, %s, %s%n", application, profile, label);
//
//        Map<String, String> values = getProperties("global", "global", "global");
//        values.putAll(getProperties(application, profile, label));
//        System.out.println(values);
//        Environment env = new Environment(application, new String[]{profile}, label, null, null);
//        env.add(new PropertySource(application, values));
//        return env;
//    }
//
//    private Map<String, String> getProperties(String application, String profile, String label){
//        List<Object[]> list = template.query(" select app.app_key, app.app_value "
//                + " from app_props app "
//                + " where app.app_name = ? "
//                + " and app.app_profile = ? "
//                + " and app.app_label = ? ",
//                (rs, rowNum) -> new Object[]{rs.getString(1), rs.getString(2)},
//                application, profile, label);
//        return list.stream().collect(Collectors.toMap((Object[] o) -> String.valueOf(o[0]), (Object[] o) -> String.valueOf(o[1])));
//    }
//
//}
