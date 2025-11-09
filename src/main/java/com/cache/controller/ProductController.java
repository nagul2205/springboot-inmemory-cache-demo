package com.cache.controller;

import com.cache.entity.Product;
import com.cache.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CacheManager cacheManager;

    @GetMapping
    public List<Product> getAll() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return product;
    }
    @PostMapping
    public String save(@RequestBody Product product){
        productService.save(product);
        return "saved !";
    }
    @PutMapping
    public String update(@RequestBody Product product){
        productService.update(product);
        return "Updated !";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){
        productService.delete(id);
        return "Deleted";
    }
    @GetMapping("/debug/cache/{name}")
    public Object viewCache(@PathVariable String name) {
        var cache = cacheManager.getCache(name);
        if (cache == null) return "Cache not found";
        var nativeCache = cache.getNativeCache(); // This is a ConcurrentMap
        return nativeCache.toString();
    }
}
