package com.att.training.springboot.examples;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("upper")
@RequiredArgsConstructor
public class AsyncController {
    private final AsyncService asyncService;

    @GetMapping("sync")
    public List<String> toUpperCaseSync(@RequestParam List<String> inputs) {
        return asyncService.toUpperCaseSync(inputs);
    }
    @GetMapping("async1")
    public List<String> toUpperCaseWithCfOnCommonPool(@RequestParam List<String> inputs) {
        return asyncService.toUpperCaseWithCfOnCommonPool(inputs);
    }
    @GetMapping("async2")
    public List<String> toUpperCaseWithCfOnSpringPool(@RequestParam List<String> inputs) {
        return asyncService.toUpperCaseWithCfOnSpringPool(inputs);
    }
    @GetMapping("async3")
    public List<String> toUpperCaseWithSpringAsync(@RequestParam List<String> inputs) {
        return asyncService.toUpperCaseWithSpringAsync(inputs);
    }
    @GetMapping("async4")
    public CompletableFuture<Void> toUpperCaseWithSpringAsyncOnIoPool(@RequestParam List<String> inputs) {
        return asyncService.toUpperCaseWithSpringAsyncOnIoPool(inputs);
    }
}
