package com.example.chatservice.api1.repository;

import com.example.chatservice.api1.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}