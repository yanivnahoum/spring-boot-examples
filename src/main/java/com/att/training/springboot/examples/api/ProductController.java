package com.att.training.springboot.examples.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("{id}")
    public ProductDto getById(@PathVariable int id) {
        return productService.getById(id);
    }

    @GetMapping
    public List<ProductDto> getAllByName(@RequestParam String name) {
        return productService.getAllByName(name);
    }

    @PatchMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateById(@PathVariable int id, @RequestBody ProductDto productDto) {
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
