package com.example.emailsendler.servise;


import com.example.emailsendler.entity.Product;
import com.example.emailsendler.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public List<Product> productList(){
        return productRepository.findAll();
    }

    public ResponseEntity<String> add(Product product){
        Product save = productRepository.save(product);
        return new ResponseEntity<>("Mahsulot saqlandi", HttpStatus.ACCEPTED);
    }

    public ResponseEntity<String> update(Integer id, Product product){
        Optional<Product> byId = productRepository.findById(id);
        if (byId.isPresent()){
            Product newProduct = byId.get();
            newProduct.setName(product.getName());
            newProduct.setDescription(product.getDescription());
            Product save = productRepository.save(newProduct);
            return new ResponseEntity<>("Mahsulot tahrirlandi", HttpStatus.OK);
        }
        return new ResponseEntity<>("Mahsulot topilmadi", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> delete(Integer id){
        Optional<Product> byId = productRepository.findById(id);
        if (byId.isPresent()){
            productRepository.deleteById(id);
            return new ResponseEntity<>("Mahsulot o'chirildi", HttpStatus.OK);
        }
        return new ResponseEntity<>("Mahsulot topilmadi", HttpStatus.NOT_FOUND);
    }

    public Product findById(Integer id) {
        Optional<Product> byId = productRepository.findById(id);
        if (byId.isPresent()){
            return byId.get();
        }
        return null;
    }
}
