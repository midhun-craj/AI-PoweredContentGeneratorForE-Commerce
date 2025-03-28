package com.mcr.aicontentservice.controller;

import com.mcr.aicontentservice.model.ProductRequest;
import com.mcr.aicontentservice.service.AiContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiContentController {
    private final AiContentService contentService;

    @PostMapping("/generate")
    public ResponseEntity<String> generateDescription(@RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(contentService.generateDescription(productRequest.getProductName(),
                productRequest.getCategory()));
    }
}
