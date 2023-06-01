package com.att.training.springboot.examples.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor
@Validated
public class ProductController2 {
    private final ProductService productService;

    @GetMapping("{id}")
    public ProductDto getById(@PathVariable int id) {
        return productService.getById(id);
    }

    @GetMapping
    public List<ProductDto> getAllByName(@RequestParam String name) {
        return productService.getAllByName(name);
    }


    @PostMapping
    @ResponseStatus(CREATED)
    @Validated(OnCreate.class)
    public void create(@Valid @RequestBody ProductDto productDto) {
        productService.create(productDto);
    }

    @PatchMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @Validated(OnUpdate.class)
    public void updateById(@Positive @PathVariable int id, @Valid @RequestBody ProductDto productDto) {
        productService.updateById(id, productDto);
    }
}

//    @PutMapping("{id}")
//    @ResponseStatus(NO_CONTENT)
//    public void updateById(@PathVariable int id, @RequestBody ProductDto productDto) {
//        productService.updateById(id, productDto);
//    }
//    @PutMapping("{id}")
//    @ResponseStatus(NO_CONTENT)
//    public void updateById2(@Positive @PathVariable int id, @Valid @RequestBody ProductDto productDto) {
//        productService.updateById(id, productDto);
//    }
