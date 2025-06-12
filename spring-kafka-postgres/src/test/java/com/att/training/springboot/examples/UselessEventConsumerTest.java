package com.att.training.springboot.examples;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UselessEventConsumerTest {
    @Mock
    private ProductDao productDao;
    private EventConsumer eventConsumer;

    @BeforeEach
    void setUp() {
        var eventProcessor = new EventProcessor(productDao);
        eventConsumer = new EventConsumer(eventProcessor);
    }

    @Test
    void whenConsume_thenUpdatePriceOnProductDao() {
        var event = new ProductPriceChangedEvent("P123", new BigDecimal("19.99"));

        eventConsumer.consume(event);

        verify(productDao).updatePrice("P123", new BigDecimal("19.99"));
    }
}
