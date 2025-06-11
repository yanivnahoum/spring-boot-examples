package com.att.training.springboot.examples;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventProcessor {
    private final ProductDao productDao;

    public void process(ProductPriceChangedEvent event) {
        productDao.updatePrice(event.code(), event.price());
    }
}
