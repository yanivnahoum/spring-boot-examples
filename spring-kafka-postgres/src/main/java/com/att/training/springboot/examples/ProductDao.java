package com.att.training.springboot.examples;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductDao {
    private final JdbcClient jdbcClient;

    public void save(Product product) {
        jdbcClient.sql("""
                        INSERT INTO products (code, name, price)
                        VALUES (:code, :name, :price)
                        """)
                .paramSource(product)
                .update();
    }

    // region findByCodeOrNull
    public Product findByCodeOrNull(String code) {
        return jdbcClient.sql("""
                        SELECT code, name, price
                        FROM products
                        WHERE code = ?
                        """)
                .param(code)
                .query(Product.class)
                .optional()
                .orElse(null);
    }
    // endregion

    public void updatePrice(String code, BigDecimal price) {
        int affectedRows = jdbcClient.sql("""
                        UPDATE products
                        SET price = :price
                        WHERE code = :code
                        """)
                .param("price", price)
                .param("code", code)
                .update();
        if (affectedRows == 0) {
            log.warn("#updatePrice >> Product {} not found and was not updated", code);
        }
    }
}

