package com.ml.cetest.inventory.repository;

import com.ml.cetest.inventory.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByTicker(String ticker);
}