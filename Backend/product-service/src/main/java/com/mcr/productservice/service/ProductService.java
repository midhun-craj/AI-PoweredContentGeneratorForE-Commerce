package com.mcr.productservice.service;

import com.mcr.productservice.model.Product;
import com.mcr.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Boolean deleteProduct(Long id) {
        Optional<Product> product1 = productRepository.findById(id);
        if(product1.isPresent()) {
            productRepository.delete(product1.get());
            return true;
        }
        return false;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product updateDescription(Long id, String description) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found."));

        product.setAiGeneratedDescription(description);
        return productRepository.save(product);
    }
}
