package com.att.training.springboot.examples.db;

import com.github.gavlyukovskiy.boot.jdbc.decorator.DecoratedDataSource;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class MultiDataSourceHealthIndicator extends AbstractHealthIndicator {
    private final List<DataSource> dataSources;

    @Override
    protected void doHealthCheck(Health.Builder builder) {
        boolean allValid = true;
        for (var dataSource : dataSources) {
            if (dataSource instanceof AbstractRoutingDataSource) continue;
            var valid = validateDataSource(dataSource);
            builder.withDetail(getPoolName(dataSource), toStatus(valid).getCode());
            allValid = allValid && valid;
        }
        builder.status(toStatus(allValid));
    }

    private boolean validateDataSource(DataSource dataSource) {
        boolean valid;
        try (var connection = dataSource.getConnection()) {
            valid = connection.isValid(1);
        } catch (Exception e) {
            log.warn("Exception while validating {} datasource", getPoolName(dataSource), e);
            valid = false;
        }
        return valid;
    }

    private String getPoolName(DataSource dataSource) {
        return switch (dataSource) {
            case HikariDataSource hikariDataSource -> hikariDataSource.getPoolName();
            case DecoratedDataSource decoratedDataSource
                    when decoratedDataSource.getRealDataSource() instanceof HikariDataSource hikariDataSource ->
                    hikariDataSource.getPoolName();
            case null, default -> "unknown";
        };
    }

    private static Status toStatus(boolean valid) {
        return valid ? Status.UP : Status.DOWN;
    }
}
