package com.flipkart.fdsg.planning.ip.core.config;

import org.apache.tomcat.jdbc.pool.PoolProperties;

public interface IFCommonConfiguration {

    PoolProperties getDatabase();

    void setDatabase(PoolProperties poolProperties);
}
