package com.example.rin.demo.database.repository;

import com.example.rin.demo.database.dao.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findById(int id);
    Boolean deleteById(int id);
    List<Product> findAllByActive(int i);
    List<Product> findAllByNameContaining(String str);
    List<Product> findAllByDescriptionContaining(String str);
    List<Product> findAllByPriceLessThan(int max);
    List<Product> findAllByPriceGreaterThan(int min);
    List<Product> findAllByCategory(int category);
    List<Product> findAllByOblasty(int oblasty);
    List<Product> findAllByOrderByIdDesc();
}
