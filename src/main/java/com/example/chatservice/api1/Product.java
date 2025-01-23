package com.example.chatservice.api1;

import org.springframework.boot.autoconfigure.domain.EntityScan;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private double price;
}
