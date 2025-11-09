package com.cache.service;

import com.cache.entity.Product;
import com.cache.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Cacheable(value = "products", key = "#id")
    public Product getProductById(Long id) {
        System.out.println("➡ Fetching from DB for ID: " + id);
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }
    @CachePut(value = "products", key = "#p.id")
    public Product update(Product p) {
        return productRepository.findById(p.getId())
                .map(existing -> {
                    existing.setPrice(p.getPrice());
                    existing.setName(p.getName());
                    return productRepository.save(existing); // returns Product
                })
                .orElseThrow(() -> new RuntimeException("Product not found with id " + p.getId()));
    }
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    public void save(Product p){
        productRepository.save(p);
    }
    @CacheEvict(value = "products", key = "id")
    public void delete(Long id){
        productRepository.deleteById(id);
    }
}
