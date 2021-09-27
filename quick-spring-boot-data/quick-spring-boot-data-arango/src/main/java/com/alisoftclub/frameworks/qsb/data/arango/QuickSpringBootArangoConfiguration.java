package com.alisoftclub.frameworks.qsb.data.arango;

import static com.arangodb.ArangoDB.Builder;
import com.arangodb.springframework.config.ArangoConfiguration;

//@Configuration
//@EnableArangoRepositories(basePackages = { "com.arangodb.spring.demo" })
public abstract class QuickSpringBootArangoConfiguration implements ArangoConfiguration {

    @Override
    public Builder arango() {
        return new Builder().host("localhost", 8529).user("root").password(null);
    }
    
}
