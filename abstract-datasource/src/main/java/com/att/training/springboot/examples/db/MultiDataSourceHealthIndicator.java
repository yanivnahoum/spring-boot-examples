package com.att.training.springboot.examples.db;

import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class MultiDataSourceHealthIndicator extends AbstractHealthIndicator {
    private final List<HikariDataSource> dataSources;

    @Override
    protected void doHealthCheck(Health.Builder builder) {
        boolean allValid = true;
        for (var dataSource : dataSources) {
            var valid = validateDataSource(dataSource);
            builder.withDetail(dataSource.getPoolName(), toStatus(valid).getCode());
            allValid = allValid && valid;
        }
        builder.status(toStatus(allValid));
    }

    private boolean validateDataSource(HikariDataSource dataSource) {
        boolean valid;
        try (var connection = dataSource.getConnection()) {
            valid = connection.isValid(1);
        } catch (Exception e) {
            log.warn("Exception while validating {} datasource", dataSource.getPoolName(), e);
            valid = false;
        }
        return valid;
    }

    private static Status toStatus(boolean valid) {
        return valid ? Status.UP : Status.DOWN;
    }
}
