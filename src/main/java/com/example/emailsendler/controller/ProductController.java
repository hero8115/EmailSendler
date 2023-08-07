package com.example.emailsendler.controller;


import com.example.emailsendler.entity.Product;
import com.example.emailsendler.servise.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;


    @PreAuthorize(value = "hasAnyAuthority('ADMIN','MANAGER','USER')")
    @GetMapping
    public List<Product> productList(){
        return productService.productList();
    }
    @PreAuthorize(value = "hasAnyAuthority('ADMIN','MANAGER','USER')")
    @GetMapping("{id}")
    public Product product(@PathVariable Integer id){
        return productService.findById(id);
    }
    @PreAuthorize(value = "hasAnyAuthority('ADMIN','MANAGER')")
    @PostMapping
    public ResponseEntity<String> add(@RequestBody Product product){
        return productService.add(product);
    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN','MANAGER')")
    @PutMapping("{id}")
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody Product product){
        return productService.update(id, product);
    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String > delete(@PathVariable Integer id){
        return productService.delete(id);
    }
}
