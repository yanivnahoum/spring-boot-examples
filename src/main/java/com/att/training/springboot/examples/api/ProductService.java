package com.att.training.springboot.examples.api;

import com.att.training.springboot.examples.aop.Timed;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Collections.emptyList;

@Timed
@Component
public class ProductService {
    public ProductDto getById(int id) {
        return null;
    }

    public List<ProductDto> getAllByName(String name) {
        return emptyList();
    }

    public void updateById(int id, ProductDto productDto) {}

    public void create(ProductDto productDto) {
    }
}


