package com.att.training.springboot.examples.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

@Slf4j
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        var dbRegion = DbContext.getRegion();
        log.info("#determineCurrentLookupKey - Datasource lookup key = {}", dbRegion);
        return dbRegion;
    }
}

