/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alisoftclub.frameworks.example.arango.config;

import com.alisoftclub.frameworks.example.arango.Main;
import com.alisoftclub.frameworks.qsb.data.arango.QuickSpringBootArangoConfiguration;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDatabase;
import com.arangodb.springframework.annotation.EnableArangoRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author AI
 */
@Configuration
@EnableArangoRepositories(basePackageClasses = {Main.class})
public class ArangoConfig extends QuickSpringBootArangoConfiguration {

    @Override
    public String database() {
        return "eval";
    }

    @Bean
    public ArangoDatabase arangoDatabase() {
        return arango().build().db(database());
    }

}
