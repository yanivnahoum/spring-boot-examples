package com.att.training.springboot.examples.config;

import com.att.training.springboot.examples.db.DbRegion;
import com.att.training.springboot.examples.db.DynamicRoutingDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.sql.init.SqlDataSourceScriptDatabaseInitializer;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;
import static org.springframework.boot.sql.init.DatabaseInitializationMode.ALWAYS;

@Configuration
public class DataSourceConfig {
    private final SqlInitializationProperties sqlInitializationProperties = buildSqlInitializationProperties();

    @Primary
    @Bean
    public DynamicRoutingDataSource dynamicRoutingDataSource() {
        var dynamicRoutingDataSource = new DynamicRoutingDataSource();
        dynamicRoutingDataSource.setTargetDataSources(Map.ofEntries(
                entry(DbRegion.EAST, eastDataSource()),
                entry(DbRegion.WEST, westDataSource())
        ));
        dynamicRoutingDataSource.setDefaultTargetDataSource(eastDataSource());
        return dynamicRoutingDataSource;
    }

    @Bean
    @ConfigurationProperties("app.datasource.east")
    public DataSourceProperties eastDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("app.datasource.east.hikari")
    public DataSource eastDataSource() {
        return buildDataSource(eastDataSourceProperties());
    }

    @Bean
    public SqlDataSourceScriptDatabaseInitializer eastScriptDatabaseInitializer() {
        return new SqlDataSourceScriptDatabaseInitializer(eastDataSource(), sqlInitializationProperties);
    }

    @Bean
    @ConfigurationProperties("app.datasource.west")
    public DataSourceProperties westDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("app.datasource.west.hikari")
    public DataSource westDataSource() {
        return buildDataSource(westDataSourceProperties());
    }

    @Bean
    public SqlDataSourceScriptDatabaseInitializer westScriptDatabaseInitializer() {
        return new SqlDataSourceScriptDatabaseInitializer(westDataSource(), sqlInitializationProperties);
    }

    private HikariDataSource buildDataSource(DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    private SqlInitializationProperties buildSqlInitializationProperties() {
        var properties = new SqlInitializationProperties();
        properties.setSchemaLocations(List.of("classpath:schema.sql"));
        properties.setDataLocations(List.of("classpath:data.sql"));
        properties.setMode(ALWAYS);
        return properties;
    }
}
