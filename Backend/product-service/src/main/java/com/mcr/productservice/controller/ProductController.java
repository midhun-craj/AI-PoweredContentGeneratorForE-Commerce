package com.mcr.productservice.controller;

import com.mcr.productservice.model.Product;
import com.mcr.productservice.model.apirequest.UpdateProductDescriptionRequest;
import com.mcr.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.createProduct(product));
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteProduct(@PathVariable("id") Long id) {
        Boolean deleted = productService.deleteProduct(id);
        if(deleted) {
            return ResponseEntity.ok(Map.of("message", "Product deleted successfully."));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Product not found."));
        }
    }

    @PutMapping("/{id}/description")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id,
                                                 @RequestBody UpdateProductDescriptionRequest
                                                         request) {
        return ResponseEntity.ok(productService.updateDescription(id, request.getDescription()));
    }
}
