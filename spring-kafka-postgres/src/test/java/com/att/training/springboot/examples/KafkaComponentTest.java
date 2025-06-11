package com.att.training.springboot.examples;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

import java.math.BigDecimal;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest(properties = "spring.kafka.consumer.auto-offset-reset=earliest")
@WithKafka
@WithPostgres
class KafkaComponentTest {
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    @Autowired
    private ProductDao productDao;

    @Test
    void givenProductInDb_whenProductPriceChangedEvent_thenProductPriceIsUpdatedInDb() {
        var product = new Product("P1234", "some-product", new BigDecimal("29.99"));
        productDao.save(product);
        var newPrice = new BigDecimal("49.99");
        var event = new ProductPriceChangedEvent(product.code(), newPrice);

        kafkaTemplate.send(KafkaTopicNames.PRODUCT_PRICE_CHANGES, product.code(), event);

        await().atMost(3, SECONDS).untilAsserted(() -> {
            var updatedProduct = productDao.findByCodeOrNull(product.code());
            assertThat(updatedProduct)
                    .isNotNull()
                    .extracting(Product::price)
                    .isEqualTo(newPrice);
        });
    }
}
