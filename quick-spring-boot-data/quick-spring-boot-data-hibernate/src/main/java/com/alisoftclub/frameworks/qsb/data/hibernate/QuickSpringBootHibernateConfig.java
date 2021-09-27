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
//package com.alisoftclub.frameworks.qsb.data.hibernate;
//
//import java.util.Properties;
//
//import javax.sql.DataSource;
//
//import org.hibernate.SessionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
//import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import org.springframework.orm.hibernate5.HibernateTransactionManager;
//import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
//
///**
// *
// * @author Ali Imran
// */
////@Configuration
//public class QuickSpringBootHibernateConfig {
//
//    @Autowired
//    private Environment env;
//
//    @Bean(name = "dataSource")
//    public DataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//
//        // See: application.properties
//        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
//        dataSource.setUrl(env.getProperty("spring.datasource.url"));
//        dataSource.setUsername(env.getProperty("spring.datasource.username"));
//        dataSource.setPassword(env.getProperty("spring.datasource.password"));
//
//        System.out.println("## getDataSource: " + dataSource);
//
//        return dataSource;
//    }
//
//    @Autowired
//    @Bean(name = "sessionFactory")
//    public SessionFactory sessionFactory(DataSource dataSource) throws Exception {
//        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
//        System.out.println("============================");
//        System.out.println("sessionFactor() called...");
//        System.out.println("============================");
//        // Package contain entity classes
////        factoryBean.setPackagesToScan(new String[]{""});
//        factoryBean.setDataSource(dataSource);
//        factoryBean.setHibernateProperties(hibernateProperties());
//        factoryBean.afterPropertiesSet();
//        //
//        SessionFactory sf = factoryBean.getObject();
//        System.out.println("## getSessionFactory: " + sf);
//        return sf;
//    }
//
//    /**
//     * Configure Properties for Hibernate
//     *
//     * @return Properties
//     */
//    public Properties hibernateProperties() {
//        Properties properties = new Properties();
//
//        // See: application.properties
//        properties.put("hibernate.dialect", env.getProperty("spring.jpa.hibernate.dialect"));
//        properties.put("hibernate.show_sql", env.getProperty("spring.jpa.hibernate.show-sql"));
//        properties.put("hibernate.format_sql", env.getProperty("spring.jpa.hibernate.format_sql"));
//        properties.put("hibernate.hbm2ddl.auto", env.getRequiredProperty("spring.jpa.hibernate.ddl-auto"));
//        properties.put("current_session_context_class", //
//                env.getProperty("spring.jpa.hibernate.current_session_context_class"));
//
//        // Fix Postgres JPA Error:
//        // Method org.postgresql.jdbc.PgConnection.createClob() is not yet implemented.
//        // properties.put("hibernate.temp.use_jdbc_metadata_defaults",false);
//
//        return properties;
//    }
//
//    @Autowired
//    @Bean(name = "transactionManager")
//    public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
//        HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
//        return transactionManager;
//    }
//
//}
