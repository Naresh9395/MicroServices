package com.programming.techie.productservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.programming.techie.productservice.model.Product;
@Repository

public interface ProductRepository extends MongoRepository<Product, String>{

}
